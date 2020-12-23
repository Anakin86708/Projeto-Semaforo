package starters;

import GUI.GUIClient;

/**
 * Responsible for inheriting from MainStarter and creating the StarterClient, 
 * with its graphical interface
 */
public class StartClient implements MainStarter {
    
    /**
     * Responsable for calling the start function
     * @param args New Start the Client
     */
    public static void main(String[] args) {
        new StartClient().start();
    }

    /**
     * All logic is created from the Graphical Client Interface eventt to start
     */
    @Override
    public void start() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUIClient guic = new GUIClient();
                guic.setLocationRelativeTo(null);
                guic.setVisible(true);
            }
        });
    }

    /**
     * Responsable to show the String, and
     * @return the message Client
     */
    @Override
    public String toString() {
        return "Client";
    }

}
