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
import static resources.ExceptionHandler.errorDialog;
import server.NetworkServer;

/**
 * Define os comandos que podem ser enviados pela rede
 *
 * @author silva
 */
public enum NetworkCommands implements Serializable{
    NEW, NEXTSTAGE, STOP;

    public static final int BYTEARRAYSIZE = 2048;
    NetworkObject networkObject;

    /**
     * Responsável por enviar um comando para determinado cliente
     *
     * @param srcRepresentation
     * @param dstRepresentation
     */
    public void sendCommandChangeTo(ClientRepresentation srcRepresentation, ClientRepresentation dstRepresentation) {
        try {
            this.networkObject = new NetworkObject(this, srcRepresentation);
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream outputStream = this.networkObject.serialize(socket);
            byte[] obj = outputStream.toByteArray();
            outputStream.close();
            DatagramPacket packet = new DatagramPacket(obj, obj.length, dstRepresentation.getAddress(), dstRepresentation.getPort());
            socket.send(packet);
        } catch (IOException ex) {
            errorDialog(ex, "Error while sending object.\n");
        }
    }

    /**
     * Envia mensagens para o servidor
     *
     * @param srcRepresentation
     */
    public void sendCommandChangeToServer(ClientRepresentation srcRepresentation) {
        sendCommandChangeTo(srcRepresentation, new ClientRepresentation(NetworkServer.getAddressServer(), NetworkServer.getPort()));
    }

}
