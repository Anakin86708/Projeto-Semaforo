package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ClientRepresentation;
import network.NetworkCommands;
import server.NetworkServer;

/**
 * Responsible for creating the NetworkClient Class from Runnable
 * @author silva
 */
public class NetworkClient implements Runnable {

    ClientRepresentation representation;

    /**
     * 
     */
    public NetworkClient() {
        try {
            this.representation = new ClientRepresentation(InetAddress.getLocalHost(), generatePort());
            beaconToServer();
        } catch (UnknownHostException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Responsable to initialize the Thread, and to create an object,
     * @return the working of Thread 
     */
    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    /**
     * Responsable for notifying the server that a new client is available
     */
    private void beaconToServer() throws IOException {
        Socket socket = new Socket(NetworkServer.getAddressServer(), NetworkServer.getPort());
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);
        objectStream.writeObject(representation);
    }
    /**
     * Responsable for sending the information from new Clients who wish to connect
     */
    private void listenner() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(representation.getPort());
        } catch (SocketException ex) {
            return;
        }
        while (true) {
            try {
                byte[] buf = new byte[16];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                NetworkCommands client = deserialization(packet);
                if (client.equals(NetworkCommands.NEXTSTAGE)) {
                    // Semaphores 
                    System.out.println("AVANÇANDO...");
                }
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Responsable for  
     * @return a new door that has not yet been used
     */
    private int generatePort() {
        try ( ServerSocket ss = new ServerSocket(0)) {
            if (ss != null && ss.getLocalPort() > 0) {
                return ss.getLocalPort();
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    /**
     * Responsable to analize if port is available or doesn't 
     * @param port passed to ServerSocket
     * @return Available Port's True or False
     */
    private boolean isPortAvailable(int port) {
        try ( var ss = new ServerSocket(port);  var ds = new DatagramSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Responsable to perform the deserialization of an object received by the network 
     * @param packet the DatagramParcket's parameter 
     * @return the Client or Null
     * @throws IOException 
     */
    private NetworkCommands deserialization(DatagramPacket packet) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            NetworkCommands client = (NetworkCommands) objectInputStream.readObject();
            return client;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /**
     * Responsable to create the Override Method and to run the Listenner 
     */
    @Override
    public void run() {
        listenner();
    }

}
