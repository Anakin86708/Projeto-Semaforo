package server;

import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import network.ClientRepresentation;
import network.NetworkCommands;
import network.NetworkObject;
import static resources.ExceptionHandler.errorDialog;

/**
 * Responsável por gerenciar a comunicação de rede do servidor
 *
 * @author silva
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;
    private static final int PORT = 25556; // Porta fixa do servidor a ser ouvida;
    private final List<ClientRepresentation> avaliableClients;
    private DatagramSocket listennerDatagramSocket;

    public NetworkServer() {
        this.keepRunning = true;
        this.avaliableClients = new ArrayList<>();
    }

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

    public int getAvaliableClients() {
        return avaliableClients.size();
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

    /**
     * Deve receber informações de novos clientes que desejam se conectar
     */
    private void listener() {
        try {
            byte[] buf = new byte[NetworkCommands.BYTEARRAYSIZE];
            listennerDatagramSocket = new DatagramSocket(PORT);

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
            case NEW -> {
                addNewClient(command.getSrcRepresentation());
            }
            case STOP -> {
                removeClient(command.getSrcRepresentation());
            }
        }
    }

    private void addNewClient(ClientRepresentation clientRepresentation) {
        avaliableClients.add(clientRepresentation);  // Cliente está null
    }

    private void removeClient(ClientRepresentation clientRequested) {
        // Remover o cliente da lista
        this.avaliableClients.forEach((client) -> {
            if (client.getAddress().equals(clientRequested.getAddress())) {
                this.avaliableClients.remove(client);
            }
        });
    }

    /**
     * Deve enviar uma solicitação a todos os clientes para alterar o status
     */
    public void changeSemaphoreStatus() {
        avaliableClients.forEach(clientRepresentation -> {
            NetworkCommands.NEXTSTAGE.sendCommandChangeTo(new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()), clientRepresentation);
        });
    }

    public void stop() {
        this.keepRunning = false;
        this.listennerDatagramSocket.close();
    }
}
