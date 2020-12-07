/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author silva
 */
public class NetworkServer implements Runnable {

    private volatile boolean keepRunning;

    public NetworkServer() {
        this.keepRunning = true;
    }

    private void listenner() {
        while (keepRunning) {            
            
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
