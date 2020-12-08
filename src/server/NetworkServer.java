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
 * Responsável por gerenciar a comunicação de rede do servidor
 *
 * @author silva
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;
    private static InetAddress addressServer;
    private static int port = 25556; // Porta fixa do servidor a ser ouvida;
    private List<ClientRepresentation> avaliableClients;

    public NetworkServer() {
        this.keepRunning = true;
        this.avaliableClients = new ArrayList<>();
    }

    public static InetAddress getAddressServer() {
        try {
            return InetAddress.getByName("192.168.0.107");
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
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            return;
        }
        while (keepRunning) {
            try {
                Socket socket = serverSocket.accept();  // Aguarda até que uma conexão seja estabelecida

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
     * Realiza a deserialização de um objeto recebido pela rede
     *
     * @param socket socket com objeto
     * @return informações do cliente
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
     * Deve enviar uma solicitação a todos os clientes para alterar o status
     */
    public void changeSemaphoreStatus() {
        for (ClientRepresentation clientRepresentation : avaliableClients) {
            try {
                // Envia o comando de alteração para todos os clientes listados
                sendCommandChange(clientRepresentation, NetworkCommands.NEXTSTAGE);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Responsável por enviar um comando para determinado cliente
     *
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

    @Override
    public void run() {
        listenner();
    }
}
