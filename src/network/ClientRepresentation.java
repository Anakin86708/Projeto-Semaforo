package network;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Data representing one client
 *
 */
public class ClientRepresentation implements Serializable {

    private final InetAddress address;
    private final int port;

    /**
     * Network data
     *
     * @param address address that can be accessed over the network
     * @param port listening door
     */
    public ClientRepresentation(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
