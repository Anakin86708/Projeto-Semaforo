/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Represent a client to the server
 *
 * @author silva
 */
public class ClientRepresentation implements Serializable{

    private final InetAddress address;
    private final int port;

    /**
     * Information of each client
     * @param address address address that can be accessed over the 
     * @param port door listening door
     */
    public ClientRepresentation(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Get the address for the user,
     * @return the address 
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Get the address for the listening door,
     * @return the port
     */
    public int getPort() {
        return port;
    }

}
