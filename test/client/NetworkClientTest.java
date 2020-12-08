/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import org.junit.Test;
import static org.junit.Assert.*;
import server.NetworkServer;

/**
 *
 * @author silva
 */
public class NetworkClientTest {

    @Test
    public void testClientListenner() throws InterruptedException {
        NetworkServer server = new NetworkServer();
        server.startThread();
        
        NetworkClient client = new NetworkClient();
        Thread thread = client.startThread();
        
        Thread.currentThread().sleep(500);
        server.changeSemaphoreStatus();
        
    }

}
