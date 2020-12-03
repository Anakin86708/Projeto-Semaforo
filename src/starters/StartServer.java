/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starters;

import GUI.GUIServer;

/**
 *
 * @author silva
 */
public class StartServer {
    public static void main(String[] args) {
        GUI.GUIServer server = new GUIServer();
        server.setLocationRelativeTo(null);
        server.setVisible(true);
    }
}
