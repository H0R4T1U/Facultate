package network.jsonprotocol;

import Services.IObserver;
import Services.IService;
import Services.MotoException;
import model.Player;
import model.Race;
import model.Team;
import model.User;
import network.dto.DTOUtils;

import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServicesJsonProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    private static Logger logger = LogManager.getLogger(ChatServicesJsonProxy.class);

    public ChatServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(User user, IObserver client) throws MotoException {
        initializeConnection();
        user.setPassword(TextUtils.simpleEncode(user.getPassword()));
        Request req= JsonProtocolUtils.createLoginRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new MotoException(err);
        }
    }



    public void logout(User user, IObserver client) throws MotoException {

        Request req=JsonProtocolUtils.createLogoutRequest(user);
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();//data().toString();
            throw new MotoException(err);
        }
    }



    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }

    }

    private void sendRequest(Request request) throws MotoException {
        String reqLine=gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new MotoException("Error sending object "+e);
        }

    }

    private Response readResponse() throws MotoException {
       Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }
    private void initializeConnection() throws MotoException {
        try {
            gsonFormatter=new Gson();
            connection=new Socket(host,port);
            output=new PrintWriter(connection.getOutputStream());
            output.flush();
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished=false;
            startReader();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
       if (response.getType()== ResponseType.NEW_PLAYER){
           Player player=DTOUtils.getFromDTO(response.getNewPlayer());
            logger.debug("Player added: "+player.getName());
           Executors.newSingleThreadExecutor().execute(() -> {
               try {
                   client.playerAdded(player);
               } catch (MotoException e) {
                   logger.error(e);
                   logger.error(e.getStackTrace());
               }
           });
       }
    }

    private boolean isUpdate(Response response){
        return response.getType() == ResponseType.NEW_PLAYER;
    }
    private class ReaderThread implements Runnable{
        private final ExecutorService executor = Executors.newSingleThreadExecutor(); // Single executor for all updates
        public void run() {
            while(!finished){
                try {
                    String responseLine=input.readLine();
                    logger.debug("response received {}",responseLine);
                    Response response=gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)){
                        ExecutorService executor = Executors.newFixedThreadPool(3);
                        executor.execute(()->handleUpdate(response));
                         executor.shutdown();
                    }else{

                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (IOException e) {
                    logger.error("Reading error "+e);
                }
            }
        }
    }

    @Override
    public void add(Player p, Integer raceId, IObserver client) throws MotoException {
        try {
            initializeConnection();
            Request req = JsonProtocolUtils.createAddPlayerRequest(p, String.valueOf(raceId));
            sendRequest(req);
            Response response = readResponse();
            if (response.getType() == ResponseType.ERROR) {
                String err = response.getErrorMessage();
                closeConnection();
                throw new MotoException(err);
            }
            if(response.getType()== ResponseType.OK){
                logger.debug("OK:Player added: " +p.getName());
            }
        } catch (MotoException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        try {
            initializeConnection();

            Request req = JsonProtocolUtils.createGetTeamRequest(name);
            sendRequest(req);
            Response response = readResponse();
            if (response.getType() == ResponseType.GET_TEAM) {
                Team team = DTOUtils.getFromDTO(response.getTeam());

                return Optional.of(team);
            }
            if (response.getType()== ResponseType.ERROR){
                String err=response.getErrorMessage();
                closeConnection();
                throw new MotoException(err);
            }
        }catch(MotoException e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Race[] getAllRaces() throws MotoException {
        initializeConnection();
        Request req= JsonProtocolUtils.createGetRacesRequest();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.GET_RACES){
            return DTOUtils.getFromDTO(response.getRaces());
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new MotoException(err);
        }
        return null;
    }


    @Override
    public Player[] getRacePlayersOfTeam(Integer raceId, Integer teamId) {
        try{
            initializeConnection();
            String tID = String.valueOf(teamId);
            String rID = String.valueOf(raceId);
            Request req = JsonProtocolUtils.createGetTeamPlayersRequest(rID,tID);
            sendRequest(req);
            Response response=readResponse();
            if (response.getType()== ResponseType.GET_TEAM_PLAYERS){
                return DTOUtils.getFromDTO(response.getPlayers());
            }
            if(response.getType()== ResponseType.ERROR){
                String err=response.getErrorMessage();
                closeConnection();
                throw new MotoException(err);
            }
        } catch (MotoException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return new Player[0];
    }
}
