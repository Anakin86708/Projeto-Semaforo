package resources;

import java.io.Serializable;

/**
 * Valores que podem ser atribuidos ao semáforo
 *
 * @author silva
 */
public enum StatusSemaphore implements Serializable {
    RED, YELLOW, GREEN;

    private StatusSemaphore actualState;

    StatusSemaphore() {
        changeStatus();
    }

    /**
     * Obtêm o valor atual do semáforo
     *
     * @return constante com o estado
     */
    public StatusSemaphore getActualState() {
        return actualState;
    }

    /**
     * Possibilita fazer a troca para o próximo estágio do semáforo
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
