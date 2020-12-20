package starters;

import javax.swing.JOptionPane;

public class Starter {

    public static void main(String[] args) {
        Object[] options = {new StartServer(), new StartClient()};
        MainStarter response = (MainStarter) JOptionPane.showInputDialog(null, "Select with one you want to start:", "Initializer", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response != null) {
            response.start();
        }
    }

}
