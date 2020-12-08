package server;

import GUI.GUIServer;
import java.util.TimerTask;

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
    
    public void generateLog() {
        StringBuilder testeGenerate = new StringBuilder();
        testeGenerate.append("Teste A\n");
        guiServer.writeOnLog(testeGenerate);
    }

    @Override
    public void run() {
        periodicLog();
    }
    
    private void periodicLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("Active clients - ").append(networkServer.getAvaliableClients());
        sb.append("\n\n");
        guiServer.writeOnLog(sb);
        System.out.println(sb.toString());
    }
}
