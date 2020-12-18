/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import GUI.GUIClient;
import resources.StageSemaphore;

/**
 *
 * @author silva
 */
public class ClientSemaphore {

    private final GUIClient guiClient;
    private final NetworkClient networkClient;
    private final Thread clientThread;
    private StageSemaphore stage;

    /**
     *
     * @param guiServer
     */
    public ClientSemaphore(GUIClient guiServer) {
        this.guiClient = guiServer;
        this.networkClient = new NetworkClient(this);
        this.clientThread = this.networkClient.startThread();
        this.stage = StageSemaphore.RED;
        guiClient.writeText(this.stage);
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    public void changeStage() {
        this.stage = this.stage.changeStage();
        guiClient.writeText(this.stage);
    }

    public StageSemaphore getStage() {
        return stage;
    }
    
}
