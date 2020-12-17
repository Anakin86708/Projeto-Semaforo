/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.NetworkServer;

/**
 *
 * @author silva
 */
public class ExceptionHandler {

    public static void errorDialog(Exception ex, String displayMessage) throws HeadlessException {
        Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, displayMessage + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
