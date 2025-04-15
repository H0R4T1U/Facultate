package network.jsonprotocol;

import model.Player;
import model.Race;
import model.User;
import network.dto.DTOUtils;
import network.dto.PlayerDTO;
import network.dto.TeamDTO;
import network.dto.UserDTO;

public class JsonProtocolUtils {



    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response createGetRacesResponse(Race[] races){
        Response resp = new Response();
        resp.setType(ResponseType.GET_RACES);
        resp.setRaces(DTOUtils.getDTO(races));
        return resp;
    }

    public static Response createGetPlayerResponse(Player[] players){
        Response resp = new Response();
        resp.setType(ResponseType.GET_TEAM_PLAYERS);
        resp.setPlayers(DTOUtils.getDTO(players));
        return resp;
    }
    public static Response createGetTeamResponse(TeamDTO team){
        Response resp = new Response();
        resp.setType(ResponseType.GET_TEAM);
        resp.setTeam(team);
        return resp;
    }
    public static Request createLoginRequest(User user){
        Request req=new Request();
        req.setType(RequestType.LOGIN);
        req.setUser(DTOUtils.getDTO(user));
        return req;
    }

    public static Request createLogoutRequest(User user){
        Request req=new Request();
        req.setType(RequestType.LOGOUT);
        req.setUser(DTOUtils.getDTO(user));
        return req;
    }
    public static Request createAddPlayerRequest(Player player,String raceId){
        Request req=new Request();
        req.setType(RequestType.ADD_PLAYER);
        req.setPlayer(DTOUtils.getDTO(player));
        req.setRaceId(raceId);
        return req;
    }
    public static Request createGetRacesRequest(){
        Request req=new Request();
        req.setType(RequestType.GET_RACES);
        return req;
    }

    public static Request createGetTeamRequest(String teamName){
        Request req=new Request();
        req.setType(RequestType.GET_TEAM);
        req.setTeamName(teamName);
        return req;
    }
    public static Request createGetTeamPlayersRequest(String raceId,String teamId){
        Request req=new Request();
        req.setType(RequestType.GET_TEAM_PLAYERS);
        req.setTeamId(teamId);
        req.setRaceId(raceId);
        return req;
    }


    public static Response createPlayerAddedResponse(PlayerDTO dto) {
        Response resp = new Response();
        resp.setType(ResponseType.NEW_PLAYER);
        resp.setNewPlayer(dto);
        return resp;
    }
}
