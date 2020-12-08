package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private static int port;
    private List<ClientRepresentation> avaliableClients;

    public NetworkServer() throws UnknownHostException {
        this.keepRunning = true;

        NetworkServer.addressServer = InetAddress.getLocalHost();
        NetworkServer.port = 25556; // Porta fixa do servidor a ser ouvida
    }

    public static InetAddress getAddressServer() {
        return addressServer;
    }

    public static int getPort() {
        return port;
    }

    /**
     * Deve receber informações de novos clientes que desejam se conectar
     */
    private void listenner() {
        while (keepRunning) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
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
        Socket socket = new Socket(clientRepresentation.getAddress(), clientRepresentation.getPort());
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(stream);
        objectStream.writeObject(command);
    }

    @Override
    public void run() {
        listenner();
    }
}
