package resources;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 * Exception feedback to user
 */
public class ExceptionHandler {

    public static void errorDialog(Exception ex, String displayMessage) throws HeadlessException {
        JOptionPane.showMessageDialog(null, displayMessage + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
