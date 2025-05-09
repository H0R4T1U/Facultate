using moto.model;
using moto.networking.dto;

namespace moto.networking.jsonprotocol;

public class JsonProtocolUtils
{
    public static Response CreateOkResponse()
    {
        return new Response { Type = ResponseType.OK };
    }

    public static Response CreateErrorResponse(string errorMessage)
    {
        return new Response { Type = ResponseType.ERROR, ErrorMessage = errorMessage };
    }

    public static Response CreateGetRacesResponse(Race[] races)
    {
        return new Response { Type = ResponseType.GET_RACES, Races = DTOUtils.GetDTO(races) };
    }

    public static Response CreateGetPlayerResponse(Player[] players)
    {
        return new Response { Type = ResponseType.GET_TEAM_PLAYERS, Players = DTOUtils.GetDTO(players) };
    }

    public static Response CreateGetTeamResponse(TeamDTO team)
    {
        return new Response { Type = ResponseType.GET_TEAM, Team = team };
    }

    public static Request CreateLoginRequest(User user)
    {
        return new Request { Type = RequestType.LOGIN, User = DTOUtils.GetDTO(user) };
    }

    public static Request CreateLogoutRequest(User user)
    {
        return new Request { Type = RequestType.LOGOUT, User = DTOUtils.GetDTO(user) };
    }

    public static Request CreateAddPlayerRequest(Player player, string raceId)
    {
        return new Request { Type = RequestType.ADD_PLAYER, Player = DTOUtils.GetDTO(player), RaceId = raceId };
    }

    public static Request CreateGetRacesRequest()
    {
        return new Request { Type = RequestType.GET_RACES };
    }

    public static Request CreateGetTeamRequest(string teamName)
    {
        return new Request { Type = RequestType.GET_TEAM, TeamName = teamName };
    }

    public static Request CreateGetTeamPlayersRequest(string raceId, string teamId)
    {
        return new Request { Type = RequestType.GET_TEAM_PLAYERS, RaceId = raceId, TeamId = teamId };
    }

    public static Response CreatePlayerAddedResponse(PlayerDTO dto)
    {
        return new Response { Type = ResponseType.NEW_PLAYER, NewPlayer = dto };
    }
}