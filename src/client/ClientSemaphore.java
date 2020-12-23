package client;

import GUI.GUIClient;
import resources.StageSemaphore;

/**
 * Manage comunication between client GUI and client network, as well stage
 * change
 */
public class ClientSemaphore {

    private final GUIClient guiClient;
    private final NetworkClient networkClient;
    private final Thread clientThread;
    private StageSemaphore stage;

    /**
     * Binds all instances required for control
     *
     * @param guiServer
     */
    public ClientSemaphore(GUIClient guiServer) {
        this.guiClient = guiServer;
        this.networkClient = new NetworkClient(this);
        this.clientThread = this.networkClient.startThread();
        this.stage = StageSemaphore.RED;
        guiClient.changeColorState(this.stage);
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    public void changeStage() {
        this.stage = this.stage.changeStage();
        guiClient.changeColorState(this.stage);
    }

    public StageSemaphore getStage() {
        return stage;
    }

}
