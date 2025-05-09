using log4net;
using moto.model;
using moto.services;
using System;

namespace MotoClientGTK
{
    public class GTKClientCtrl 
    {
        private readonly GrpcServiceClient server;
        public event EventHandler<MotoUserEventArgs>? updateEvent; 
        private User? currentUser;
        private static readonly ILog log = LogManager.GetLogger(typeof(GTKClientCtrl));
        private CancellationTokenSource updateTokenSource;
        public GTKClientCtrl(GrpcServiceClient server)
        {
            this.server = server;
        }
        public async Task login(string userId, string pass)
        {
            User user = new User(userId, pass);

            // Perform synchronous login

            var loginResponse = server.Login(userId, pass);



            log.Debug("Login succeeded ....+");
            currentUser = user;
            log.DebugFormat("Current user {0}", user);

            updateTokenSource = new CancellationTokenSource();

            // Await the asynchronous operation only after successful login
            Subscribe(userId,pass);
        }
        public async void Subscribe(string userId,string pass){
            await server.SubscribeToPlayerUpdates(PlayerAdded, updateTokenSource.Token, userId, pass);
        }
        public async Task<Race[]> GetAllRaces()
        {
            return await server.GetAllRacesAsync();
        }
        public void Logout()
        {
            log.Debug("Ctrl logout"+currentUser.Username);
            server.Logout(currentUser.Username, currentUser.Password);
            currentUser = null;
            updateTokenSource?.Cancel();
        }

        public async Task<Player[]> SearchTeam(string teamName, int raceId)
        {
            try
            {
                log.Debug("Search team ..."+teamName+" for user"+currentUser.Username);
                var team = server.GetTeamByName(teamName);
                if (team != null)
                {
                    return await server.GetRacePlayersOfTeam(raceId,team.Id);
                }
            }
            catch (Exception ex)
            {
                log.Error("Error searching team: " + ex.Message);
            }
        
            return null;
        }
        
        public void AddPlayer(string playerName, string playerCode, string teamName, int raceId)
        {
            try
            {
                log.Debug("Add player ..."+playerName+" for user"+currentUser.Username);
                var team = server.GetTeamByName(teamName);
                if (team != null)
                {
                    var player = new Player(playerName, playerCode, team.Id);
                    server.AddPlayerAsync(player, raceId);
                }
            }
            catch (Exception ex)
            {
                log.Error("Error adding player: " + ex.Message);
            }
        }
        
        
        
        public void PlayerAdded(Player player)
        {
            log.Debug("player added observed "+player);
            MotoUserEventArgs userArgs = new MotoUserEventArgs(MotoUserEvent.Player_ADDED, player);
            OnUserEvent(userArgs);
        }
        protected virtual void OnUserEvent(MotoUserEventArgs e)
        {
           Task.Run(() =>
           {
               updateEvent?.Invoke(this, e);
        
               log.Debug("Update Event called");
           }).ContinueWith(t =>
           {
               if (t.IsFaulted)
               {
                   log.Error("Error in OnUserEvent: " + t.Exception?.Message);
               }
           });
        
        }
    }
}