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
public class StartServer implements MainStarter {

    @Override
    public void start() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUIServer server = new GUIServer();
                server.setLocationRelativeTo(null);
                server.setVisible(true);
            }
        });
    }

    @Override
    public String toString() {
        return "Server";
    }

}
