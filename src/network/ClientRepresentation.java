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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientRepresentation other = (ClientRepresentation) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!this.address.getHostAddress().equals(other.address.getHostAddress())) {
            return false;
        }
        return true;
    }
    
    

}
