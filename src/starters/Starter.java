/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starters;

import javax.swing.JOptionPane;

/**
 *
 * @author silva
 */
public class Starter {

    public static void main(String[] args) {
        Object[] options = {new StartServer(), new StartClient()};
        MainStarter response = (MainStarter) JOptionPane.showInputDialog(null, "Select with one you want to start:", "Initializer", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response != null) {
            response.start();
        }
    }
    
}
