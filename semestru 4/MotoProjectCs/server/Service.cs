using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using moto.model;
using moto.persistence;
using moto.services;
using log4net;
using moto.networking.jsonprotocol;

namespace moto.server
{
    public class Service : IService
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Service));
        private readonly ConcurrentDictionary<int, IObserver> loggedClients;
        private readonly IPlayerRepository playerRepository;
        private readonly IUserRepository userRepository;
        private readonly IRaceRepository raceRepository;
        private readonly ITeamRepository teamRepository;
        private readonly int defaultThreadsNo = 3;

        public Service(IPlayerRepository playerRepository, IUserRepository userRepository, IRaceRepository raceRepository, ITeamRepository teamRepository)
        {
            this.playerRepository = playerRepository;
            this.userRepository = userRepository;
            this.raceRepository = raceRepository;
            this.teamRepository = teamRepository;
            loggedClients = new ConcurrentDictionary<int, IObserver>();
        }

        public User FindByUsername(string username)
        {
            var user = userRepository.FindByUsername(username,null);
            if (user == null)
            {
                log.Error("User not found");
                throw new Exception("User not found");
            }
            return user;
        }

        public void Login(User user, IObserver client)
        {
            var userR = userRepository.FindByUsername(user.Username,user.Password);
            if (userR == null)
                throw new MotoException("User not found.");

            if (loggedClients.ContainsKey(userR.Id))
                throw new MotoException("User already logged in.");

            loggedClients[userR.Id] = client;
        }

        public void Logout(User user, IObserver client)
        {
            var usr = userRepository.FindByUsername(user.Username, TextUtils.SimpleDecode(user.Password));
            if (usr != null)
            {
                loggedClients.TryRemove(usr.Id, out _);
                log.Info("User logged out: " + user.Username);
            }
        }

        public void Add(Player player, int raceId, IObserver client)
        {
            try
            {
                var race = raceRepository.FindOne(raceId);
                if (race == null)
                    throw new MotoException("Race not found.");

                player = playerRepository.Save(player);
                if (player == null)
                    throw new MotoException("Failed to save player.");

                if (race.Players == null)
                    race.Players = new List<Player>();

                race.Players.Add(player);
                race.NoPlayers = race.Players.Count;
                raceRepository.Update(race);

                log.Info("Service: Player added");
                Task.Run(() => NotifyPlayerAdded(player));
            }
            catch (Exception e)
            {
                throw new MotoException(e.Message);
            }
        }



        public Team GetTeamByName(string teamName)
        {
            return teamRepository.GetTeamByName(teamName);
        }

        public Race[] GetAllRaces()
        {
            var races = raceRepository.FindAll();
            var raceArray = new Race[races.Count];
            int index = 0;
            foreach (var race in races.Values)
            {
                raceArray[index++] = race;
            }
            return raceArray;
        }

        public Player[] GetRacePlayersOfTeam(int raceId, int teamId)
        {
            var race = raceRepository.FindOne(raceId);
            if (race != null)
            {
                log.Info("Service: Finding players by team");
                var players = new List<Player>();
                foreach (var player in race.Players)
                {
                    if (player.Team == teamId)
                    {
                        players.Add(player);
                    }
                }
                return players.ToArray();
            }
            log.Info("Service: Finding players by team no race found");
            return new Player[0];
        }

        private void NotifyPlayerAdded(Player player)
        {
            var executor = new List<Task>();
            foreach (var client in loggedClients.Values)
            {
                executor.Add(Task.Run(() =>
                {
                    try
                    {
                        client.PlayerAdded(player);
                    }
                    catch (MotoException e)
                    {
                        log.Error("Error notifying player added " + e);
                    }
                }));
            }
            Task.WaitAll(executor.ToArray());
        }
    }
}