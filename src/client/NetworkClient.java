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
 *
 * @author silva
 */
public class NetworkClient implements Runnable {

    ClientRepresentation representation;

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

    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    /**
     * Avisa ao servidor que um novo cliente está disponível
     */
    private void beaconToServer() throws IOException {
        Socket socket = new Socket(NetworkServer.getAddressServer(), NetworkServer.getPort());
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);
        objectStream.writeObject(representation);
    }

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
                    // Avançar semáforo
                    System.out.println("AVANÇANDO...");
                }
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Retorna uma nova porta que ainda não foi utilizada
     *
     * @return
     */
    private int generatePort() {
        return new Socket().getPort();
    }

    private NetworkCommands deserialization(DatagramPacket packet) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            NetworkCommands client = (NetworkCommands) objectInputStream.readObject();
            return client;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public void run() {
        listenner();
    }

}
