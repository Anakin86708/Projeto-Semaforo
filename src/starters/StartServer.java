package starters;

import GUI.GUIServer;

/**
 * Responsible for inheriting from MainStarter and creating the StarterServer, 
 * with its graphical interface
 */
public class StartServer implements MainStarter {
    
    /**
     * Responsable for calling the start function
     * @param args  New Start Server
     */
    public static void main(String[] args) {
        new StartServer().start();
    }

    /**
     * All logic is created from the Graphical Server Interface event to start
     */
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

    /**
     * To show the message "Server"
     * @return the String "Server"
     */
    @Override
    public String toString() {
        return "Server";
    }

}
