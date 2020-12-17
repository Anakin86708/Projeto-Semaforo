package resources;

import java.io.Serializable;

/**
 * Valores que podem ser atribuidos ao semáforo
 *
 * @author silva
 */
public enum StageSemaphore implements Serializable {
    RED(0), YELLOW(1), GREEN(2);

    private int actualStage;

    private StageSemaphore(int actualStage) {
        this.actualStage = actualStage;
    }

    /**
     * Obtêm o valor atual do semáforo
     *
     * @return constante com o estado
     */
    public StageSemaphore getActualState() {
        switch (actualStage) {
            case 0 -> {
                return StageSemaphore.RED;
            }
            case 1 -> {
                return StageSemaphore.YELLOW;
            }
            case 2 -> {
                return StageSemaphore.GREEN;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Possibilita fazer a troca para o próximo estágio do semáforo
     *
     * @return
     */
    public StageSemaphore changeStage() {
        switch (actualStage) {
            case 0 -> {
                this.actualStage = 2;
            }
            case 2 -> {
                this.actualStage = 1;
            }
            case 1 -> {
                this.actualStage = 0;
            }
            default -> {
                this.actualStage = 0;
            }
        }
        return getActualState();
    }

}
