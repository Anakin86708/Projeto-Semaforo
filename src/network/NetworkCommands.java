/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 * Define os comandos que podem ser enviados pela rede
 *
 * @author silva
 */
public enum NetworkCommands implements Serializable {
    NEXTSTAGE, STOP;
}
