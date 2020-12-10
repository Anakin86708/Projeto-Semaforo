/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import GUI.GUIClient;

/**
 *
 * @author silva
 */
public class ClientSemaphore {

    private final GUIClient guiServer;
    private final NetworkClient networkClient;
    private final Thread clientThread;

    /**
     *
     * @param guiServer
     */
    public ClientSemaphore(GUIClient guiServer) {
        this.guiServer = guiServer;
        this.networkClient = new NetworkClient();
        this.clientThread = this.networkClient.startThread();
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

}
