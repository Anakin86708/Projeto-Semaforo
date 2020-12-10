package starters;

import GUI.GUIClient;

/**
 *
 * @author ariel
 */
public class StartClient {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIClient().setVisible(true);
            }
        });
    }
}
