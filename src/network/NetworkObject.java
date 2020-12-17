/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.SocketException;
import static network.NetworkCommands.BYTEARRAYSIZE;

/**
 *
 * @author silva
 */
public class NetworkObject implements Serializable {

    private final NetworkCommands command;
    private final ClientRepresentation srcRepresentation;

    public NetworkObject(NetworkCommands command, ClientRepresentation srcRepresentation) {
        this.command = command;
        this.srcRepresentation = srcRepresentation;
    }

    public NetworkCommands getCommand() {
        return command;
    }

    public ClientRepresentation getSrcRepresentation() {
        return srcRepresentation;
    }
    
    public ByteArrayOutputStream serialize(DatagramSocket socket) throws SocketException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BYTEARRAYSIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return outputStream;
    }

}
