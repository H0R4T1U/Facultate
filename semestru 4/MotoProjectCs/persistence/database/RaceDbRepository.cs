using System.Data;
using log4net;
using moto.persistence;
using moto.model;

namespace moto.persistence.database
{
    public class RaceDbRepository : IRaceRepository
    {
        private readonly IPlayerRepository _playerRepo;
        private static readonly ILog Log = LogManager.GetLogger(typeof(RaceDbRepository));
        private readonly string connectionString;

        public RaceDbRepository(string connectionString, IPlayerRepository playerRepo)
        {
            _playerRepo = playerRepo;
            this.connectionString = connectionString;
            Log.Info("Initializing PlayerDBRepository");
        }

        public Race? FindOne(int id)
        {
            Log.Info($"Finding race with id {id}");
    
            Race? race = null;
            IDbConnection _connection = ConnectionFactory.getInstance().createConnection(connectionString);
            try
            {
                _connection.Open();
                using var cmd = _connection.CreateCommand();
                cmd.CommandText = "SELECT * FROM Race WHERE Id = @id";
                var idParam = cmd.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                cmd.Parameters.Add(idParam);
                using var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    int engineType = reader.GetInt32(reader.GetOrdinal("EngineType"));
                    int noPlayers = reader.GetInt32(reader.GetOrdinal("NoPlayers"));
                    race = new Race(engineType) { Id = id, NoPlayers = noPlayers };
                }
            }
            catch (Exception e)
            {
                Log.Error(e);

                throw new Exception(e.Message);
            }
            finally
            {
                _connection.Close();
            }

            if (race != null)
            {
                try
                {
                    _connection.Open();
                    using var cmd = _connection.CreateCommand();
                    cmd.CommandText = "SELECT PlayerId FROM PlayerRaces WHERE RaceId = @id";
                    var idParam = cmd.CreateParameter();
                    idParam.ParameterName = "@id";
                    idParam.Value = id;
                    cmd.Parameters.Add(idParam);
                    using var reader = cmd.ExecuteReader();
                    while (reader.Read())
                    {
                        int playerId = reader.GetInt32(reader.GetOrdinal("PlayerId"));
                        Player? player = _playerRepo.FindOne(playerId);
                        if (player != null)
                        {
                            race.Players.Add(player);
                        }
                    }

                    race.NoPlayers = race.Players.Count;
                    return race;
                }
                catch (Exception e)
                {
                    Log.Error(e);
                }
                finally
                {
                    _connection.Close();
                }
            }
            return null;
        }

        public IDictionary<int, Race> FindAll()
        {
            Log.Info("Finding all Races");
            Dictionary<int, Race> data = new Dictionary<int, Race>();
            IDbConnection _connection = ConnectionFactory.getInstance().createConnection(connectionString);
            try
            {
                _connection.Open();
                using var cmd = _connection.CreateCommand();
                cmd.CommandText = "SELECT * FROM Race";
                using var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    int id = reader.GetInt32(reader.GetOrdinal("Id"));
                    int engineType = reader.GetInt32(reader.GetOrdinal("EngineType"));
                    int noPlayers = reader.GetInt32(reader.GetOrdinal("NoPlayers"));
                    var race = new Race(engineType) { Id = id, NoPlayers = noPlayers };

                    using var cmdPlayers = _connection.CreateCommand();
                    cmdPlayers.CommandText = "SELECT PlayerId FROM PlayerRaces WHERE RaceId = @id";
                    var idParam = cmdPlayers.CreateParameter();
                    idParam.ParameterName = "@id";
                    idParam.Value = id;
                    cmdPlayers.Parameters.Add(idParam);
                    using var readerPlayers = cmdPlayers.ExecuteReader();
                    while (readerPlayers.Read())
                    {
                        int playerId = readerPlayers.GetInt32(readerPlayers.GetOrdinal("PlayerId"));
                        Player? player = _playerRepo.FindOne(playerId);
                        if (player != null)
                        {
                            race.Players.Add(player);
                        }
                    }

                    race.NoPlayers = race.Players.Count;
                    data.Add(race.Id, race);
                }

                Log.Info("Found " + data.Count + " Races");
                return data;
            }
            catch (Exception e)
            {
                Log.Error(e);
                throw new Exception(e.Message);
            }
            finally
            {
                _connection.Close();
            }
        }


        public  Race Save(Race entity)
        {
            Log.Info("Saving Race " + entity);
            IDbConnection _connection = ConnectionFactory.getInstance().createConnection(connectionString);
            _connection.Open();
            using var transaction = _connection.BeginTransaction();
            try
            {
                using var cmd = _connection.CreateCommand();
                cmd.Transaction = transaction;
                cmd.CommandText = "INSERT INTO Race (EngineType, NoPlayers) VALUES (@engineType, @noPlayers)";
                var engineTypeParam = cmd.CreateParameter();
                var noPlayersParam = cmd.CreateParameter();

                engineTypeParam.ParameterName = "@engineType";
                noPlayersParam.ParameterName = "@noPlayers";

                engineTypeParam.Value = entity.EngineType;
                noPlayersParam.Value = entity.Players.Count;

                cmd.Parameters.Add(engineTypeParam);
                cmd.Parameters.Add(noPlayersParam);
                cmd.ExecuteNonQuery();

                using var cmdLastId = _connection.CreateCommand();
                cmdLastId.Transaction = transaction;
                cmdLastId.CommandText = "SELECT last_insert_rowid();";
                entity.Id = Convert.ToInt32(cmdLastId.ExecuteScalar());

                foreach (var player in entity.Players)
                {
                    using var cmdPlayers = _connection.CreateCommand();
                    cmdPlayers.Transaction = transaction;
                    cmdPlayers.CommandText =
                        "INSERT OR IGNORE INTO PlayerRaces (PlayerId, RaceId) VALUES (@playerId, @raceId)";
                    var raceIdParam = cmdPlayers.CreateParameter();
                    var playerIdParam = cmdPlayers.CreateParameter();
                    raceIdParam.ParameterName = "@raceId";
                    playerIdParam.ParameterName = "@playerId";
                    raceIdParam.Value = entity.Id;
                    playerIdParam.Value = player.Id;
                    cmdPlayers.Parameters.Add(raceIdParam);
                    cmdPlayers.Parameters.Add(playerIdParam);
                    cmdPlayers.ExecuteNonQuery();
                }

                entity.NoPlayers = entity.Players.Count;
                transaction.Commit();
                return entity;
            }
            catch (Exception e)
            {
                Log.Error(e);
                transaction.Rollback();
                throw new Exception(e.Message);
            }
            finally
            {
                _connection.Close();
            }
            
        }

        public Race? Delete(int id)
        {
            Log.Info($"Deleting race with id {id}");
            Race? race = FindOne(id);
            if (race == null) return null;
            IDbConnection _connection = ConnectionFactory.getInstance().createConnection(connectionString);

            _connection.Open();
            using var transaction = _connection.BeginTransaction();
            try
            {
                using var cmdPlayers = _connection.CreateCommand();
                cmdPlayers.Transaction = transaction;
                cmdPlayers.CommandText = "DELETE FROM PlayerRaces WHERE RaceId = @id";
                var idParam = cmdPlayers.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                cmdPlayers.Parameters.Add(idParam);
                cmdPlayers.ExecuteNonQuery();

                using var cmd = _connection.CreateCommand();
                cmd.Transaction = transaction;
                cmd.CommandText = "DELETE FROM Race WHERE Id = @id";
                cmd.Parameters.Add(idParam);
                cmd.ExecuteNonQuery();
                
                transaction.Commit();
                return race;
            }
            catch (Exception e)
            {
                transaction.Rollback();
                Log.Error(e);
                return null;
            }
            finally
            {
                _connection.Close();
            }
        }
        public Race Update(Race entity)
        {
            Log.Info($"Updating race {entity}");
            IDbConnection _connection = ConnectionFactory.getInstance().createConnection(connectionString);
            _connection.Open();
            using var transaction = _connection.BeginTransaction();
            try
            {
                using var cmd = _connection.CreateCommand();
                cmd.Transaction = transaction;
                cmd.CommandText = "UPDATE Race SET EngineType = @engineType, NoPlayers = @noPlayers WHERE Id = @id";
                var engineTypeParam = cmd.CreateParameter();
                var noPlayersParam = cmd.CreateParameter();
                var idParam = cmd.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = entity.Id;

                engineTypeParam.ParameterName = "@engineType";
                engineTypeParam.Value = entity.EngineType;
                noPlayersParam.Value = entity.Players.Count;

                noPlayersParam.ParameterName = "@noPlayers";
                cmd.Parameters.Add(engineTypeParam);
                cmd.Parameters.Add(noPlayersParam);
                cmd.Parameters.Add(idParam);
                cmd.ExecuteNonQuery();

                using var cmdDeletePlayers = _connection.CreateCommand();
                cmdDeletePlayers.Transaction = transaction;
                cmdDeletePlayers.CommandText = "DELETE FROM PlayerRaces WHERE RaceId = @id";
                cmdDeletePlayers.Parameters.Add(idParam);
                cmdDeletePlayers.ExecuteNonQuery();

                foreach (var player in entity.Players)
                {
                    using var cmdPlayers = _connection.CreateCommand();
                    cmdPlayers.Transaction = transaction;
                    cmdPlayers.CommandText = "INSERT INTO PlayerRaces (PlayerId, RaceId) VALUES (@playerId, @raceId)";
                    var raceIdParam = cmdPlayers.CreateParameter();
                    var playerIdParam = cmdPlayers.CreateParameter();
                    raceIdParam.ParameterName = "@raceId";
                    playerIdParam.ParameterName = "@playerId";
                    raceIdParam.Value = entity.Id;
                    playerIdParam.Value = player.Id;
                    cmdPlayers.Parameters.Add(raceIdParam);
                    cmdPlayers.Parameters.Add(playerIdParam);
                    cmdPlayers.ExecuteNonQuery();
                }

                transaction.Commit();
                return entity;
            }
            catch (Exception e)
            {
                transaction.Rollback();
                Log.Error(e);
                throw new Exception(e.Message);
            }
            finally
            {
                _connection.Close();
            }
        }
    }
}