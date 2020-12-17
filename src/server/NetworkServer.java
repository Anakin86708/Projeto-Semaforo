package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ClientRepresentation;
import network.NetworkCommands;

/**
 * Responsable to manage the network communication from the server
 * @author silva
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;
    private static InetAddress addressServer;
    private static int port = 25556; // Porta fixa do servidor a ser ouvida;
    private List<ClientRepresentation> avaliableClients;

    /**
     * Responsable to create the Array List with the available Clients
     */
    public NetworkServer() {
        this.keepRunning = true;
        this.avaliableClients = new ArrayList<>();
    }

    /**
     * Responsable to catch the Address's Number from server,
     * @return the Adrress's Number or Null
     */
    public static InetAddress getAddressServer() {
        try {
            return InetAddress.getByName("192.168.0.107");
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    /**
     * Responsable to catch the Port,
     * @return the Port
     */
    public static int getPort() {
        return port;
    }
    
    /**
     * Responsable to catch the Available Clients and 
     * @return the Number of them
     */
    public int getAvaliableClients() {
        return avaliableClients.size();
    }

    /**
     *  Responsable to initialize the Thread, and to create an object,
     * @return the working of Thread 
     */
    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    /**
     * Responsable for receiving information from new Clients who wish to connect
     */
    private void listenner() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            return;
        }
        while (keepRunning) {
            try {
                Socket socket = serverSocket.accept();  // Wait for a connection
                ClientRepresentation client = deserialization(socket);
                avaliableClients.add(client);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * To perform the deserialization of an object received by the network,
     * @param socket Socket with the object
     * @return Client's informations
     * @throws ClassNotFoundException
     * @throws IOException
     * @see ClientRepresentation
     */
    private ClientRepresentation deserialization(Socket socket) throws ClassNotFoundException, IOException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ClientRepresentation client = (ClientRepresentation) objectInputStream.readObject();
        return client;
    }

    /**
     * Responsable to send a request to all Clients to change the status
     */
    public void changeSemaphoreStatus() {
        for (ClientRepresentation clientRepresentation : avaliableClients) {
            try {
                // To send the alteration command to the all of  Clients listed 
                sendCommandChange(clientRepresentation, NetworkCommands.NEXTSTAGE);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Responsável por enviar um comando para determinado cliente
     * Responsable to send a command to determinated client 
     * @param clientRepresentation
     * @param command
     * @throws IOException
     */
    private void sendCommandChange(ClientRepresentation clientRepresentation, NetworkCommands command) throws IOException {
        DatagramSocket socket = new DatagramSocket(clientRepresentation.getPort(), clientRepresentation.getAddress());
        ByteArrayOutputStream baos = new ByteArrayOutputStream(16);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(command);
        oos.close();
        // get the byte array of the object
        byte[] obj = baos.toByteArray();
        baos.close();

        DatagramPacket packet = new DatagramPacket(obj, obj.length, clientRepresentation.getAddress(), clientRepresentation.getPort());
        socket.send(packet);
    }

    /**
     * Responsable to create the Override method, 
     * and its to initialize the listenner
     */
    @Override
    public void run() {
        listenner();
    }
}
