/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 *
 * @author silva
 */
public class NetworkObject implements Serializable {

    private final NetworkCommands command;
    private final ClientRepresentation srcRepresentation;

    public NetworkObject(NetworkCommands command, ClientRepresentation srcRepresentation) {
        this.command = command;
        this.srcRepresentation = srcRepresentation;
    }

    public NetworkCommands getCommand() {
        return command;
    }

    public ClientRepresentation getSrcRepresentation() {
        return srcRepresentation;
    }

}
