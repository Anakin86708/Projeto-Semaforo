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
import javax.swing.JOptionPane;
import server.NetworkServer;

/**
 * Define os comandos que podem ser enviados pela rede
 *
 * @author silva
 */
public enum NetworkCommands implements Serializable {
    NEW, NEXTSTAGE, STOP;
    
    public static final int BYTEARRAYSIZE = 1024;
    private ClientRepresentation srcRepresentation;

    public ClientRepresentation getSrcRepresentation() {
        return srcRepresentation;
    }

    /**
     * Responsável por enviar um comando para determinado cliente
     *
     * @param srcRepresentation
     * @param dstRepresentation
     */
    public void sendCommandChangeTo(ClientRepresentation srcRepresentation, ClientRepresentation dstRepresentation) {
        try {
            this.srcRepresentation = srcRepresentation;
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTEARRAYSIZE);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            
            // get the byte array of the object
            byte[] obj = baos.toByteArray();
            baos.close();
            DatagramPacket packet = new DatagramPacket(obj, obj.length, dstRepresentation.getAddress(), dstRepresentation.getPort());
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkCommands.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error while sending object.\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Envia mensagens para o servidor
     * @param srcRepresentation
     */
    public void sendCommandChangeTo(ClientRepresentation srcRepresentation) {
        sendCommandChangeTo(srcRepresentation, new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()));
    }

}
