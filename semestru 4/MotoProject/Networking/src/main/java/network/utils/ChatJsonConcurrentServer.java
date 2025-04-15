package network.utils;

import Services.IService;
import network.jsonprotocol.ClientJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class ChatJsonConcurrentServer extends AbsConcurrentServer{
    private IService service;
    private static Logger logger = LogManager.getLogger(ChatJsonConcurrentServer.class);
    public ChatJsonConcurrentServer(int port, IService service) {
        super(port);
        this.service = service;
        logger.info("Chat-ChatJsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientJsonWorker worker=new ClientJsonWorker(service, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
