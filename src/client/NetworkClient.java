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
import javax.swing.JOptionPane;
import network.ClientRepresentation;
import network.NetworkCommands;
import server.NetworkServer;

/**
 *
 * @author silva
 */
public class NetworkClient implements Runnable {

    private ClientRepresentation clientRepresentation;
    private volatile boolean keepRunning;

    public NetworkClient() {
        this.keepRunning = true;
        try {
            this.clientRepresentation = new ClientRepresentation(InetAddress.getLocalHost(), generatePort());
            beaconToServer();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Unable to bind to network interface. Aborting...\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        NetworkCommands.NEW.sendCommandChangeTo(this.clientRepresentation);
    }

    /**
     * Responsável por acompanhar e receber comandos pela rede
     */
    private void listenner() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(clientRepresentation.getPort());
        } catch (SocketException ex) {
            return;
        }
        while (this.keepRunning) {
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
                JOptionPane.showMessageDialog(null, "Error while listenning on client.\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        System.out.println("Ending client listenner thread");
    }

    /**
     * Retorna uma nova porta que ainda não foi utilizada
     *
     * @return
     */
    private int generatePort() {
        try ( ServerSocket ss = new ServerSocket(0)) {
            if (ss != null && ss.getLocalPort() > 0) {
                return ss.getLocalPort();
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error while getting port for client.\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return -1;
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
    
    public void sendEndCommand() {
        this.keepRunning = false;
        NetworkCommands.STOP.sendCommandChangeTo(clientRepresentation);
    }
    
}
