using log4net;
using moto.model;
using moto.services;
using System;

namespace ChatClientGTK
{
    public class GTKClientCtrl : IObserver
    {
        private readonly IService server;
        public event EventHandler<MotoUserEventArgs>? updateEvent; 
        private User? currentUser;
        private static readonly ILog log = LogManager.GetLogger(typeof(GTKClientCtrl));

        public GTKClientCtrl(IService server)
        {
            this.server = server;
        }
        public void login(String userId, String pass)
        {
            User user=new User(userId,pass);
            server.Login(user,this);
            log.Debug("Login succeeded ....+");
            currentUser = user;
            log.DebugFormat("Current user {0}", user);
        }

        public Race[] GetAllRaces()
        {
            return server.GetAllRaces();
        }
        public void Logout()
        {
            log.Debug("Ctrl logout"+currentUser.Username);
            server.Logout(currentUser, this);
            currentUser = null;
        }

        public Player[] SearchTeam(string teamName, int raceId)
        {
            try
            {
                log.Debug("Search team ..."+teamName+" for user"+currentUser.Username);
                var team = server.GetTeamByName(teamName);
                if (team != null)
                {
                    var players = server.GetRacePlayersOfTeam(raceId, team.Id);
                    return players;
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
                    server.Add(player, raceId, this);
                    Thread.Sleep(1000);
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