package server;

import client.NetworkClient;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import starters.StartServer;

/**
 *
 * @author Guilhere
 */
public class ServerSemaphoreTest {
    
    public ServerSemaphoreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of initializeLog method, of class ServerSemaphore.
     */
    @Test
    public void testInitializeLog() {
        StartServer.main(null);
        NetworkClient networkClient = new NetworkClient();
    }

}
