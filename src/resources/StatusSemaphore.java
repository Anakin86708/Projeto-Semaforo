package resources;

import java.io.Serializable;

/**
 * Values this can be to atribuited for traffic lights 
 *
 * @author silva
 */
public enum StatusSemaphore implements Serializable {

    /**
     * "Red" Option
     */
    RED,

    /**
     *"Yellow" Option
     */
    YELLOW,

    /**
     * "Green" Option
     */
    GREEN;

    private StatusSemaphore actualState;

    StatusSemaphore() {
        changeStatus();
    }

    /**
     * Get the current semaphore value
     *
     * @return the constant with the state
     */
    public StatusSemaphore getActualState() {
        return actualState;
    }

    /**
     * It enables to make the change for the next semaphore's stage
     */
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
