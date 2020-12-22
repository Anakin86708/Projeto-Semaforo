package starters;

import GUI.GUIClient;

public class StartClient implements MainStarter {
    
    public static void main(String[] args) {
        new StartClient().start();
    }

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

    @Override
    public String toString() {
        return "Client";
    }

}
