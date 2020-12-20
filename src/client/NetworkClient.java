package client;

import GUI.GUIClient;
import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import network.ClientRepresentation;
import network.NetworkCommands;
import network.NetworkObject;
import resources.ExceptionHandler;
import server.NetworkServer;
import static resources.ExceptionHandler.errorDialog;

/**
 * Manage network comunication for client
 */
public class NetworkClient implements Runnable {

    private ClientRepresentation clientRepresentation;
    private volatile boolean keepRunning;
    private DatagramSocket listennerDatagramSocket;
    private final ClientSemaphore semaphore;

    public NetworkClient(ClientSemaphore semaphore) {
        this.keepRunning = true;
        this.semaphore = semaphore;
        try {
            this.clientRepresentation = new ClientRepresentation(InetAddress.getLocalHost(), generatePort());
            beaconToServer();
        } catch (IOException ex) {
            ExceptionHandler.errorDialog(ex, "Unable to bind to network interface. Aborting...\n");
        }
    }

    private int generatePort() {
        try ( ServerSocket ss = new ServerSocket(0)) {
            if (ss != null && ss.getLocalPort() > 0) {
                return ss.getLocalPort();
            }
        } catch (IOException ex) {
            ExceptionHandler.errorDialog(ex, "Error while getting port for client.\n");
            System.exit(1);
        }
        return -1;
    }

    /**
     * Sends a signal to the server indicating a new client
     *
     * @throws IOException
     */
    private void beaconToServer() throws IOException {
        NetworkCommands.NEW.sendCommandToServer(this.clientRepresentation);
    }

    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        networkListener();
    }

    private void networkListener() {
        try {
            byte[] buf = new byte[NetworkCommands.BYTEARRAYSIZE];
            listennerDatagramSocket = new DatagramSocket(clientRepresentation.getPort());

            while (keepRunning) {
                recivePacket(buf);
            }
        } catch (SocketException ex) {
            errorDialog(ex, "Server socket error.\n");
        }
    }

    private void recivePacket(byte[] buf) throws HeadlessException {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            listennerDatagramSocket.receive(packet);
            NetworkObject command = deserialization(packet.getData());
            interpretCommand(command);
        } catch (IOException ex) {
            errorDialog(ex, "Error while listenning on server.\n");
        } catch (ClassNotFoundException ex) {
            errorDialog(ex, "Class not found!\n");
        }
    }

    private NetworkObject deserialization(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(inputStream);
        return (NetworkObject) objectStream.readObject();
    }

    private void interpretCommand(NetworkObject command) {
        switch (command.getCommand()) {
            case NEXTSTAGE -> {
                this.semaphore.changeStage();
            }
            case SERVER_STOP -> {
                sendEndCommand();
                GUIClient.close();
            }
            case REQUEST_STAGE -> {
                sendStageToServer(command.getSrcRepresentation().getPort());
            }
        }
    }

    public void sendEndCommand() {
        this.keepRunning = false;
        NetworkCommands.STOP.sendCommandToServer(clientRepresentation);
    }

    private void sendStageToServer(int serverStagePort) {
        try {
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream outputStream = this.semaphore.getStage().serialize(socket);
            byte[] obj = outputStream.toByteArray();
            outputStream.close();
            DatagramPacket packet = new DatagramPacket(obj, obj.length, NetworkServer.getAddressServer(), serverStagePort);
            socket.send(packet);
        } catch (IOException ex) {
            ExceptionHandler.errorDialog(ex, "Error sending response to server\n");
        }
    }

}
