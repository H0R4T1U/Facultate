package network.jsonprotocol;

import Services.IObserver;
import Services.IService;
import Services.MotoException;
import model.Player;
import model.Race;
import model.Team;
import model.User;

import com.google.gson.Gson;
import network.dto.DTOUtils;
import network.dto.PlayerDTO;
import network.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ClientJsonWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    private static Logger logger = LogManager.getLogger(ClientJsonWorker.class);

    public ClientJsonWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter=new Gson();
        try{
            output=new PrintWriter(connection.getOutputStream());
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected=true;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run() {
        while(connected){
            try {
                String requestLine=input.readLine();
                Request request=gsonFormatter.fromJson(requestLine, Request.class);
                Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error "+e);
        }
    }

    private static final Response okResponse=JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request){
       Response response=null;
        if (request.getType()== RequestType.LOGIN){
            logger.debug("Login request ...{}"+request.getUser());
            UserDTO udto=request.getUser();
            User user=DTOUtils.getFromDTO(udto);
            user.setPassword(TextUtils.simpleDecode(user.getPassword()));
            try {
                server.login(user,this);
                return okResponse;
            } catch (MotoException e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT){
            logger.debug("Logout request {}",request.getUser());
            UserDTO udto=request.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.logout(user,this);
                connected=false;
                return okResponse;

            } catch (MotoException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.ADD_PLAYER){
            logger.debug("Add player request {}",request.getUser());
            PlayerDTO pdto=request.getPlayer();
            Player player = DTOUtils.getFromDTO(pdto);
            Integer raceID = Integer.valueOf(request.getRaceId());
            try{
                server.add(player,raceID,this);
                return okResponse;
            } catch (MotoException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType()== RequestType.GET_RACES){
            logger.debug("Get races request {}",request.getUser());
            try{
                Race[] races = server.getAllRaces();
                return JsonProtocolUtils.createGetRacesResponse(races);

            } catch (MotoException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType()== RequestType.GET_TEAM){
            logger.debug("Get team request {}",request.getUser());
            try{
                Optional<Team> team = server.getTeamByName(request.getTeamName());
                return JsonProtocolUtils.createGetTeamResponse(DTOUtils.getDTO(team.get()));

            } catch (MotoException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType() == RequestType.GET_TEAM_PLAYERS){
            logger.debug("Get team players request {}",request.getUser());
            try{
                Integer raceId = Integer.valueOf(request.getRaceId());
                Integer teamId = Integer.valueOf(request.getTeamId());
                Player[] players = server.getRacePlayersOfTeam(raceId,teamId);
                return JsonProtocolUtils.createGetPlayerResponse(players);

            } catch (MotoException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        String responseLine=gsonFormatter.toJson(response);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
        logger.debug("sending response "+responseLine);

    }

    @Override
    public void playerAdded(Player player) throws MotoException {
        Response response = JsonProtocolUtils.createPlayerAddedResponse(DTOUtils.getDTO(player));

        try{
            Thread.sleep(1000);
            sendResponse(response);
        } catch (IOException e) {
            throw new MotoException("Error sending player added response: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
