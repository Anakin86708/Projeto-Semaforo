/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.NetworkServer;

/**
 * Define os comandos que podem ser enviados pela rede
 *
 * @author silva
 */
public enum NetworkCommands implements Serializable {
    NEXTSTAGE, STOP;

    /**
     * Responsável por enviar um comando para determinado cliente
     *
     * @param clientRepresentation
     * @param command
     * @throws IOException
     */
    public void sendCommandChangeTo(ClientRepresentation clientRepresentation)  {
        try {
            DatagramSocket socket = new DatagramSocket(clientRepresentation.getPort(), clientRepresentation.getAddress());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(16);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            // get the byte array of the object
            byte[] obj = baos.toByteArray();
            baos.close();
            DatagramPacket packet = new DatagramPacket(obj, obj.length, clientRepresentation.getAddress(), clientRepresentation.getPort());
            socket.send(packet);
        } catch (SocketException ex) {
            Logger.getLogger(NetworkCommands.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Envia mensagens para o servidor
     */
    public void sendCommandChangeTo() {
        sendCommandChangeTo(new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()));
    }
    
}
