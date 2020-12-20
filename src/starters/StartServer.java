package starters;

import GUI.GUIServer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import server.NetworkServer;

public class StartServer implements MainStarter {

    @Override
    public void start() {
        setServerIP();
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

    private void setServerIP() {
        String ip;
        do {
            ip = JOptionPane.showInputDialog(null, "Server IP (000.000.000.000):", "Set server ip", JOptionPane.QUESTION_MESSAGE);
        } while (isInvalidIP(ip)); 
        NetworkServer.setServerIP(ip);
    }

    private boolean isInvalidIP(String ip) {
        String patternString = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher ipMatcher = pattern.matcher(ip);
        return !ipMatcher.matches();
    }

    @Override
    public String toString() {
        return "Server";
    }

}
