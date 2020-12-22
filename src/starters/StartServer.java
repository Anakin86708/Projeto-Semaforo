package starters;

import GUI.GUIServer;

public class StartServer implements MainStarter {
    
    public static void main(String[] args) {
        new StartServer().start();
    }

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
