package starters;

import javax.swing.JOptionPane;

/**
 * Responsable to initilizate the Graphical Interface with two options to the User
 * @author enzoj
 */
public class Starter {

    /**
     * Responsable to show the the two options (Server and Client) 
     * @param args Start Client or Start Server 
     */
    public static void main(String[] args) {
        Object[] options = {new StartClient(), new StartServer()};
        MainStarter response = (MainStarter) JOptionPane.showInputDialog(null, "Select with one you want to start:", "Initializer", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response != null) {
            response.start();
        }
    }

}
