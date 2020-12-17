package server;

import GUI.GUIServer;
import java.util.List;
import java.util.TimerTask;
import network.ClientRepresentation;

/**
 *
 * @author Guilherme
 */
public class ServerSemaphore extends TimerTask{
    
    private final GUIServer guiServer;
    private final NetworkServer networkServer;
    private final Thread serverThread;
    
    public ServerSemaphore(GUIServer guiServer) {
        this.guiServer = guiServer;
        this.networkServer = new NetworkServer();
        serverThread = networkServer.startThread();
    }
    
    public void initializeLog() {
        StringBuilder teste = new StringBuilder();
        teste.append("Welcome\n");
        teste.append("Versão1.0\n");
        guiServer.writeOnLog(teste);
    }
    
    @Override
    public void run() {
        networkServer.changeSemaphoreStatus();
        periodicLog();
    }
    
    private void periodicLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("Active clients - ").append(networkServer.getCountAvaliableClients()).append("\n");
        sb.append(showClients());
        sb.append("\n");
        guiServer.writeOnLog(sb);
        System.out.println(sb.toString());
    }
    
    private String showClients() {
        StringBuilder sb = new StringBuilder();
        List<ClientRepresentation> avaliableClients = networkServer.getAvaliableClients();
        avaliableClients.forEach(client -> {
            sb.append("Client ip: ").append(client.getAddress()).append(":").append(client.getPort()).append("\n");
        });
        return sb.toString();
    }
    
    public void stopThread() {
        this.networkServer.stop();
        serverThread.interrupt();
    }

}
