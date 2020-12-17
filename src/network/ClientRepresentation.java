/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Realiza a representação de um cliente para o servidor
 *
 * @author silva
 */
public class ClientRepresentation{

    private final InetAddress address;
    private final int port;

    /**
     * Informações de cada cliente
     *
     * @param address endereço que pode ser acessado pela rede
     * @param port porta de escuta
     */
    public ClientRepresentation(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Obtêm o endereço para o cliente
     *
     * @return
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Obtêm a porta de escuta
     *
     * @return
     */
    public int getPort() {
        return port;
    }

}
