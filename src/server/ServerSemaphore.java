package server;

import GUI.GUIServer;
import java.util.TimerTask;

/**
 *
 * @author Guilherme
 */
public class ServerSemaphore extends TimerTask{
    
    private final GUIServer guiServer;
    
    public ServerSemaphore(GUIServer guiServer) {
        this.guiServer = guiServer;
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
        generateLog();
    }
}
