package client;

import java.awt.HeadlessException;
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
import network.NetworkObject;
import static resources.ExceptionHandler.errorDialog;
import server.NetworkServer;

/**
 *
 * @author silva
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
        NetworkCommands.NEW.sendCommandChangeToServer(this.clientRepresentation);
    }

    /**
     * Responsável por acompanhar e receber comandos pela rede
     */
    private void listenner() {
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

    /**
     * Realiza a deserialização de um objeto recebido pela rede
     *
     * @param socket socket com objeto
     * @return informações do cliente
     * @throws ClassNotFoundException
     * @throws IOException
     * @see ClientRepresentation
     */
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
        }
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

    @Override
    public void run() {
        listenner();
    }
    
    public void sendEndCommand() {
        this.keepRunning = false;
        NetworkCommands.STOP.sendCommandChangeToServer(clientRepresentation);
    }
    
}
