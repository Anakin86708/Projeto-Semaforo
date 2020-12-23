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
 * To manage network comunication for client
 */
public class NetworkClient implements Runnable {

    private ClientRepresentation clientRepresentation;
    private volatile boolean keepRunning;
    private DatagramSocket listennerDatagramSocket;
    private final ClientSemaphore semaphore;
    
    /**
     * Attempt to connect with the Client 
     * @param semaphore Client Representation 
     */

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
    
    /**
     * Getting port for Client 
     * @return the LocalPort or Message Error
     */
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
    
    /**
     * Initialize the Thread
     * @return the thread 
     */
    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        networkListener();
    }
    
    /**
     * Check communication with the datagram socket
     */
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
    /**
     * Receive the Packet 
     * @param buf Byte the Receive Packet
     * @throws HeadlessException Exception to Receive Packet
     */
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
    /**
     * Deserialization the Object
     * @param data byte to Deserialization 
     * @return the Object Deserialization 
     * @throws IOException Exception Deserialization
     * @throws ClassNotFoundException  Class Not Found Exception Deserialization
     */
    private NetworkObject deserialization(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(inputStream);
        return (NetworkObject) objectStream.readObject();
    }

    /**
     * Three cases to interpret Command  
     * @param command NEXSTAGE, SERVER_STOP, REQUEST_STAGE
     */
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

    /**
     * To stop the sending Command
     */
    public void sendEndCommand() {
        this.keepRunning = false;
        NetworkCommands.STOP.sendCommandToServer(clientRepresentation);
    }
    
    /**
     * To send the Stage to Server 
     * @param serverStagePort The Server stage 
     */
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
