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
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            return;
        }
        while (keepRunning) {
            try {
                Socket socket = serverSocket.accept();  // Aguarda até que uma conexão seja estabelecida

                Object result = deserialization(socket);
                try {
                    ClientRepresentation client = (ClientRepresentation) result;
                    avaliableClients.add(client);
                    return;
                } catch (Exception e) {
                }
                try {
                    NetworkCommands command = (NetworkCommands) result;
                    switch (command) {
                        case STOP:
                        // Remover o cliente da lista
                        InetAddress clientRequested = socket.getInetAddress();
                        this.avaliableClients.forEach((x) -> {
                            if (x.getAddress().equals(clientRequested)) {
                                this.avaliableClients.remove(x);
                            }
                        });

                    }
                } catch (Exception e) {
                }

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
    private Object deserialization(Socket socket) throws ClassNotFoundException {
        ObjectInputStream objectInputStream = null;
        try {
            InputStream inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            return objectInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException ex) {
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
            NetworkCommands.NEXTSTAGE.sendCommandChangeTo();
        }
    }

    @Override
    public void run() {
        listenner();
    }
}
