package server;

import GUI.GUIServer;
import java.util.List;
import java.util.TimerTask;
import network.ClientRepresentation;
import network.NetworkCommands;
import resources.StageSemaphore;

/**
 * Responsable for creating the semaphore class of the server 
 * and all the Semaphore relationship logic, showing the Clients
 */
public class ServerSemaphore extends TimerTask {

    private final GUIServer guiServer;
    private final NetworkServer networkServer;
    private final Thread serverThread;

    /**
     * GUI Server Semaphore 
     * @param guiServer GUI Server
     */
    public ServerSemaphore(GUIServer guiServer) {
        this.guiServer = guiServer;
        this.networkServer = new NetworkServer(this);
        serverThread = networkServer.startThread();
    }

    /**
     * Show the Program Version 
     */
    public void initializeLog() {
        StringBuilder teste = new StringBuilder();
        teste.append("Welcome\n");
        teste.append("Versão1.0\n");
        guiServer.writeOnLog(teste);
    }

    /**
     * Initialize other methods in the program
     */
    @Override
    public void run() {
        networkServer.changeAllSemaphoreStatus();
        guiServer.changeAllClientView();
        periodicLog();
    }
    
    /**
     * Show the Active Clients in the LOG
     */
    private void periodicLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("Active clients - ").append(networkServer.getCountAvaliableClients()).append("\n");
        sb.append(showClients());
        sb.append("\n");
        guiServer.writeOnLog(sb);
        System.out.println(sb.toString());
    }
    
    /**
     * Responsable to show Clients in the LOG
     * @return the String with Clients
     */
    private String showClients() {
        StringBuilder sb = new StringBuilder();
        List<ClientRepresentation> avaliableClients = networkServer.getAvaliableClients();
        avaliableClients.forEach(client -> {
            sb.append("Client ip: ").append(client.getAddress()).append(":").append(client.getPort());
            sb.append(">>").append(showStage(client)).append("\n");
        });
        return sb.toString();
    }

    private String showStage(ClientRepresentation client) {
        return getClientStage(client).toString();
    }

    /**
     * Responsable to Stage Semaphore
     * @param client Cliente Stage 
     * @return the Cliente Stage
     */
    public StageSemaphore getClientStage(ClientRepresentation client) {
        return this.networkServer.requestClientStage(client);
    }

    /**
     * Add a New Client
     * @param clientRepresentation To add a Client
     */
    public void newClientAdded(ClientRepresentation clientRepresentation) {
        guiServer.displayNewClientView(clientRepresentation);
    }

    /**
     * Remove a Client
     * @param srcRepresentation To delete the Client View
     */
    public void removeClient(ClientRepresentation srcRepresentation) {
        guiServer.removeClientView(srcRepresentation);
    }

    /**
     * Close All Client's Representations
     */
    public void closeAllClients() {
        ClientRepresentation srcRepresentation = new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort());
        this.networkServer.getAvaliableClients().forEach(client -> {
            NetworkCommands.SERVER_STOP.sendCommandFromTo(srcRepresentation, client);
        });
    }

    /**
     * Stop the thread created 
     */
    public void stopThread() {
        this.networkServer.stop();
        serverThread.interrupt();
    }

}
