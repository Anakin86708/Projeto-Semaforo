package starters;

import GUI.GUIClient;

/**
 *
 * @author ariel
 */
public class StartClient {

    /**
     * It's the class that will initialize the "Client",
     * calling its GUI.
     * @param args Parameter "Run Client"
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUIClient guic = new GUIClient();
                guic.setLocationRelativeTo(null);
                guic.setVisible(true);
            }
        });
    }
}
