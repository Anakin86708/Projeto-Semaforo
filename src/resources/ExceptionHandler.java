package resources;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 * Exception feedback to user
 */
public class ExceptionHandler {

    /**
     * The error in the Panel 
     * @param ex Exception in the ErrorDialog
     * @param displayMessage String the Error 
     * @throws HeadlessException
     */
    public static void errorDialog(Exception ex, String displayMessage) throws HeadlessException {
        JOptionPane.showMessageDialog(null, displayMessage + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
