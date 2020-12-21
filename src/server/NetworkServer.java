package server;

import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import network.ClientRepresentation;
import network.NetworkCommands;
import network.NetworkObject;
import resources.ExceptionHandler;
import static resources.ExceptionHandler.errorDialog;
import resources.StageSemaphore;

/**
 * Manage server network
 *
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;
    private static final int PORT = 25556;
    private final List<ClientRepresentation> avaliableClients;
    private DatagramSocket listennerDatagramSocket;
    private final ServerSemaphore serverSemaphore;

    public NetworkServer(ServerSemaphore serverSemaphore) {
        this.keepRunning = true;
        this.avaliableClients = new ArrayList<>();
        this.serverSemaphore = serverSemaphore;
    }

    /**
     * Returns the IP address for the server, which has been kept fixed as specified
     * @return 
     */
    public static InetAddress getAddressServer() {
        String serverIP = "192.168.15.22";
        try {
            return InetAddress.getByName(serverIP);
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    public static int getPort() {
        return PORT;
    }

    public int getCountAvaliableClients() {
        return avaliableClients.size();
    }

    public List<ClientRepresentation> getAvaliableClients() {
        return avaliableClients;
    }

    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        listener();
    }

    private void listener() {
        try {
            byte[] buf = new byte[NetworkCommands.BYTEARRAYSIZE];
            listennerDatagramSocket = new DatagramSocket(PORT);

            while (keepRunning) {
                recivePacket(buf);
            }
        } catch (SocketException ex) {
            errorDialog(ex, "Server socket error.\n");
            System.exit(1);
        }
    }

    private void recivePacket(byte[] buf) throws HeadlessException {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            listennerDatagramSocket.receive(packet);
            InetAddress srcClient = packet.getAddress();
            NetworkObject command = (NetworkObject) deserialization(packet.getData());
            interpretCommand(command, srcClient);
        } catch (IOException ex) {
            errorDialog(ex, "Error while listenning on server.\n");
        } catch (ClassNotFoundException ex) {
            errorDialog(ex, "Class not found!\n");
        }
    }

    private Object deserialization(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(inputStream);
        return objectStream.readObject();
    }

    private void interpretCommand(NetworkObject command, InetAddress srcClient) {
        ClientRepresentation srcRepresentation = new ClientRepresentation(srcClient, command.getSrcRepresentation().getPort());
        switch (command.getCommand()) {
            case NEW -> {
                addNewClient(srcRepresentation);
            }
            case STOP -> {
                removeClient(srcRepresentation);
                serverSemaphore.removeClient(srcRepresentation);
            }
        }
    }

    private void addNewClient(ClientRepresentation clientRepresentation) {
        avaliableClients.add(clientRepresentation);
        serverSemaphore.newClientAdded(clientRepresentation);
    }

    private void removeClient(ClientRepresentation clientRequested) {
        try {
            Iterator<ClientRepresentation> clientIterable = this.avaliableClients.iterator();
            while (clientIterable.hasNext()) {
                ClientRepresentation next = clientIterable.next();
                if (next.getAddress().equals(clientRequested.getAddress())) {
                    this.avaliableClients.remove(next);
                }
            }
        } catch (Exception e) {
        }
    }

    public void changeAllSemaphoreStatus() {
        this.avaliableClients.forEach(clientRepresentation -> {
            NetworkCommands.NEXTSTAGE.sendCommandFromTo(new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()), clientRepresentation);
        });
    }

    public void stop() {
        this.keepRunning = false;
        this.listennerDatagramSocket.close();
    }

    StageSemaphore requestClientStage(ClientRepresentation client) {
        try {
            byte[] data = NetworkCommands.REQUEST_STAGE.getClientStageSemaphore(client);
            return (StageSemaphore) deserialization(data);
        } catch (IOException | ClassNotFoundException ex) {
            ExceptionHandler.errorDialog(ex, "Response error\n");
            return null;
        }
    }
}
