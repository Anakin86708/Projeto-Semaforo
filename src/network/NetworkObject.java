package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.SocketException;
import static network.NetworkCommands.BYTEARRAYSIZE;

/**
 * Object sent over the network, containing the command and necessary
 * information
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
