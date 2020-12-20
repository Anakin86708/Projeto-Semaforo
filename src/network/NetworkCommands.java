package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import static resources.ExceptionHandler.errorDialog;
import server.NetworkServer;

/**
 * Defines commands that can be sent over the network
 *
 */
public enum NetworkCommands implements Serializable {
    NEW, NEXTSTAGE, STOP, SERVER_STOP, REQUEST_STAGE;

    public static final int BYTEARRAYSIZE = 2048;
    NetworkObject networkObject;

    /**
     * Responsible for sending a command to a specific customer
     *
     * @param srcRepresentation source
     * @param dstRepresentation destination
     */
    public void sendCommandFromTo(ClientRepresentation srcRepresentation, ClientRepresentation dstRepresentation) {
        try {
            this.networkObject = new NetworkObject(this, srcRepresentation);
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream outputStream = this.networkObject.serialize(socket);
            byte[] obj = outputStream.toByteArray();
            outputStream.close();
            DatagramPacket packet = new DatagramPacket(obj, obj.length, dstRepresentation.getAddress(), dstRepresentation.getPort());
            socket.send(packet);
        } catch (IOException ex) {
            errorDialog(ex, "Error while sending object.\n");
        }
    }

    /**
     * Send messages to server
     *
     * @param srcRepresentation source
     */
    public void sendCommandToServer(ClientRepresentation srcRepresentation) {
        sendCommandFromTo(srcRepresentation, new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()));
    }

    /**
     * Sends a request and returns the client stage
     *
     * @param dstRepresentation client
     * @return current stage from client
     */
    public byte[] getClientStageSemaphore(ClientRepresentation dstRepresentation) {
        try {
            DatagramSocket reciveSocket = new DatagramSocket();
            sendCommandFromTo(new ClientRepresentation(NetworkServer.getAddressServer(), reciveSocket.getLocalPort()), dstRepresentation);
            return reciveClientStageSemaphore(reciveSocket);
        } catch (SocketException ex) {
            errorDialog(ex, "Error while sending object.\n");
            return null;
        }
    }

    /**
     * Waiting for client response
     *
     * @param reciveSocket listening socket
     * @return serialized stage
     */
    private byte[] reciveClientStageSemaphore(DatagramSocket reciveSocket) {
        try {
            byte[] buf = new byte[BYTEARRAYSIZE];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            reciveSocket.receive(packet);
            InetAddress srcClient = packet.getAddress();
            return packet.getData();
        } catch (IOException ex) {
            errorDialog(ex, "Error while reciving object.\n");
            return null;
        }
    }

}
