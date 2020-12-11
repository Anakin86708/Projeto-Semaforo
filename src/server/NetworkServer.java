package server;

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

/**
 * Responsável por gerenciar a comunicação de rede do servidor
 *
 * @author silva
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;
    private static int port = 25556; // Porta fixa do servidor a ser ouvida;
    private List<ClientRepresentation> avaliableClients;

    public NetworkServer() {
        this.keepRunning = true;
        this.avaliableClients = new ArrayList<>();
    }

    public static InetAddress getAddressServer() {
        try {
            return InetAddress.getByName("192.168.15.22");
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    public static int getPort() {
        return port;
    }

    public int getAvaliableClients() {
        return avaliableClients.size();
    }

    public Thread startThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    /**
     * Deve receber informações de novos clientes que desejam se conectar
     */
    private void listenner() {
        try {
            byte[] buf = new byte[NetworkCommands.BYTEARRAYSIZE];
            DatagramSocket socket = new DatagramSocket(port);

            while (keepRunning) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    NetworkCommands command = deserialization(packet.getData());

                    switch (command) {
                        case NEW -> {
                            avaliableClients.add(command.getSrcRepresentation());  // Cliente está null
                        }
                        case STOP -> {
                            // Remover o cliente da lista
                            ClientRepresentation clientRequested = command.getSrcRepresentation();
                            this.avaliableClients.forEach((x) -> {
                                if (x.getAddress().equals(clientRequested.getAddress())) {
                                    this.avaliableClients.remove(x);
                                }
                            });
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error while listenning on server.\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Server socket error.\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    private NetworkCommands deserialization(byte[] data) {
        ObjectInputStream objectStream = null;

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            objectStream = new ObjectInputStream(inputStream);
            return (NetworkCommands) objectStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error desserialization on server");
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objectStream.close();
            } catch (IOException | NullPointerException ex) {
                System.err.println("Null pointer on server");
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Deve enviar uma solicitação a todos os clientes para alterar o status
     */
    public void changeSemaphoreStatus() {
        for (ClientRepresentation clientRepresentation : avaliableClients) {
            // Envia o comando de alteração para todos os clientes listados
            NetworkCommands.NEXTSTAGE.sendCommandChangeTo(new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()), clientRepresentation);
        }
    }

    @Override
    public void run() {
        listenner();
    }
}
