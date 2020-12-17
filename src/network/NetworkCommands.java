/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 * Define commands that can be sent over through the network
 *
 * @author silva
 */
public enum NetworkCommands implements Serializable {

    /**
     * "NEXTSTAGE" constant
     */
    NEXTSTAGE,

    /**
     * "STOP" constant
     */
    STOP;
}
