namespace MotoClientGTK;

public static class ProtoMapper
{
    public static moto.model.Race MapRace(Services.Race protoRace)
    {
        var race = new moto.model.Race(protoRace.EngineType)
        {
            Id = protoRace.Id,
            NoPlayers = protoRace.NoPlayers
        };
        foreach (var protoPlayer in protoRace.Players)
        {
            race.Players.Add(MapPlayer(protoPlayer));
        }
        return race;
    }

    public static moto.model.Player MapPlayer(Services.Player protoPlayer)
    {
        return new moto.model.Player(protoPlayer.Name, protoPlayer.Code, protoPlayer.Team);
    }

    public static moto.model.Team MapTeam(Services.Team protoTeam)
    {
        var team = new moto.model.Team(protoTeam.Name)
        {
            Id = protoTeam.Id
        };
        return team;
    }

    public static moto.model.User MapUser(Services.User protoUser)
    {
        var user = new moto.model.User(protoUser.Username, protoUser.Password)
        {
            Id = protoUser.Id
        };
        return user;
    }
}