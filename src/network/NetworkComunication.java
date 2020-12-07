/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Port;
import resources.StatusSemaphore;

/**
 *
 * @author silva
 */
public class NetworkComunication {

    private static InetAddress addressServer;
    private static int port;

    public NetworkComunication(String host, int port) throws UnknownHostException {
        NetworkComunication.addressServer = InetAddress.getByName(host);
        NetworkComunication.port = port;
    }
    
    public NetworkComunication(int port) throws UnknownHostException {
        NetworkComunication.addressServer = InetAddress.getLocalHost();
        NetworkComunication.port = port;
    }
    

    public static InetAddress getAddressServer() {
        return addressServer;
    }

    public static int getPort() {
        return port;
    }

    public void listenner(int socketPort) {
        try {            
            int bufferSize = 2;
            byte[] buffer = new byte[bufferSize];            
            DatagramPacket packet = new DatagramPacket(buffer, bufferSize);
            DatagramSocket socket = new DatagramSocket(port);
            socket.receive(packet);  // Wait until recive packet  
            
            System.out.println("Value: " + new String(packet.getData()));

        } catch (IOException ex) {
            Logger.getLogger(NetworkComunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sender(byte[] message, InetAddress destinationAddress, int destinationPort) {
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length, addressServer, destinationPort);
            DatagramSocket socket = new DatagramSocket(port);
            socket.send(packet);
        } catch (SocketException ex) {
            Logger.getLogger(NetworkComunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkComunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
