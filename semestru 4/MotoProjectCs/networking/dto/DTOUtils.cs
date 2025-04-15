using moto.model;

namespace moto.networking.dto;

public class DTOUtils
{
    public static User GetFromDTO(UserDTO usdto)
    {
        string username = usdto.Username;
        string pass = usdto.Passwd;

        return new User(username, pass);
    }

    public static User[] GetFromDTO(UserDTO[] users)
    {
        User[] friends = new User[users.Length];
        for (int i = 0; i < users.Length; i++)
        {
            friends[i] = GetFromDTO(users[i]);
        }
        return friends;
    }

    public static UserDTO GetDTO(User user)
    {
        string username = user.Username;
        string pass = user.Password;
        return new UserDTO(username, pass);
    }

    public static UserDTO[] GetDTO(User[] users)
    {
        UserDTO[] frDTO = new UserDTO[users.Length];
        for (int i = 0; i < users.Length; i++)
        {
            frDTO[i] = GetDTO(users[i]);
        }
        return frDTO;
    }

    public static PlayerDTO GetDTO(Player player)
    {
        string name = player.Name;
        string code = player.Code;
        string team = player.Team.ToString();
        return new PlayerDTO(name, code, team);
    }

    public static PlayerDTO[] GetDTO(Player[] players)
    {
        PlayerDTO[] pDTO = new PlayerDTO[players.Length];
        for (int i = 0; i < players.Length; i++)
        {
            pDTO[i] = GetDTO(players[i]);
        }
        return pDTO;
    }

    public static Player GetFromDTO(PlayerDTO playerDTO)
    {
        string name = playerDTO.Name;
        string code = playerDTO.Code;
        int team = int.Parse(playerDTO.Team);
        return new Player(name, code, team);
    }

    public static Player[] GetFromDTO(PlayerDTO[] players)
    {
        Player[] pDTO = new Player[players.Length];
        for (int i = 0; i < players.Length; i++)
        {
            pDTO[i] = GetFromDTO(players[i]);
        }
        return pDTO;
    }

    public static RaceDTO GetDTO(Race race)
    {
        string Id = race.Id.ToString();
        string EngineType = race.EngineType.ToString();
        string NoPlayers = race.NoPlayers.ToString();
        RaceDTO raceDTO = new RaceDTO(NoPlayers, EngineType, Id);
        return raceDTO;
    }

    public static RaceDTO[] GetDTO(Race[] races)
    {
        RaceDTO[] rDTO = new RaceDTO[races.Length];
        for (int i = 0; i < races.Length; i++)
        {
            rDTO[i] = GetDTO(races[i]);
        }
        return rDTO;
    }

    public static Race GetFromDTO(RaceDTO raceDTO)
    {
        int engineType = int.Parse(raceDTO.EngineType);
        int noPlayers = int.Parse(raceDTO.NoPlayers);
        int id = int.Parse(raceDTO.Id);
        Race race = new Race(engineType)
        {
            Id = id,
            NoPlayers = noPlayers
        };
        return race;
    }

    public static Race[] GetFromDTO(RaceDTO[] races)
    {
        Race[] rDTO = new Race[races.Length];
        for (int i = 0; i < races.Length; i++)
        {
            rDTO[i] = GetFromDTO(races[i]);
        }
        return rDTO;
    }

    public static Team GetFromDTO(TeamDTO team)
    {
        Team t = new Team(team.Name)
        {
            Id = int.Parse(team.Id)
        };
        return t;
    }

    public static TeamDTO GetDTO(Team team)
    {
        TeamDTO tDTO = new TeamDTO(team.Name)
        {
            Id = team.Id.ToString()
        };
        return tDTO;
    }
}