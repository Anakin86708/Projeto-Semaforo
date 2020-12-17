package server;

import GUI.GUIServer;
import java.util.TimerTask;

/**
 * Class ServerSemaphore extends by TimerTask
 * @author Guilherme
 */
public class ServerSemaphore extends TimerTask{
    
    private final GUIServer guiServer;
    private final NetworkServer networkServer;
    private final Thread serverThread;
    
    /**
     * Initialize Server, with a User
     * @param guiServer Parameter startThread
     */
    public ServerSemaphore(GUIServer guiServer) {
        this.guiServer = guiServer;
        this.networkServer = new NetworkServer();
        serverThread = networkServer.startThread();
    }
    
    /**
     * Responsable for to initialize the Log
     */
    public void initializeLog() {
        StringBuilder teste = new StringBuilder();
        teste.append("Welcome\n");
        teste.append("Versão1.0\n");
        guiServer.writeOnLog(teste);
    }
    
    /**
     * Responsable for the writing the message "Teste"
     */
    public void generateLog() {
        StringBuilder testeGenerate = new StringBuilder();
        testeGenerate.append("Teste A\n");
        guiServer.writeOnLog(testeGenerate);
    }

    /**
     * Responsable for Override the periodicLog method
     */
    @Override
    public void run() {
        periodicLog();
    }
    /**
     * Responsable to activate the Clients who use the Server
     * and add them
     */
    private void periodicLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("Active clients - ").append(networkServer.getAvaliableClients());
        sb.append("\n\n");
        guiServer.writeOnLog(sb);
        System.out.println(sb.toString());
    }
}
