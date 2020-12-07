/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.io.Serializable;

/**
 *
 * @author silva
 */
public enum StatusSemaphore  implements Serializable{
    RED, YELLOW, GREEN;

    private StatusSemaphore actualState;

    StatusSemaphore() {
        changeStatus();
    }

    public StatusSemaphore getActualState() {
        return actualState;
    }

    public void changeStatus() {
        switch (actualState) {
            case RED:
                this.actualState = GREEN;
                break;

            case YELLOW:
                this.actualState = RED;
                break;

            case GREEN:
                this.actualState = YELLOW;
                break;

            default:
                this.actualState = RED;
        }
    }
    

}
