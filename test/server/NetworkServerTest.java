/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.NetworkClient;
import org.junit.Test;

/**
 *
 * @author silva
 */
public class NetworkServerTest {

    public NetworkServerTest() {
    }

    /**
     * Test of run method, of class NetworkServer.
     */
    @Test
    public void testServerListenner() {
        NetworkServer networkServer = new NetworkServer();
        networkServer.startThread();
        new NetworkClient();
    }

}
