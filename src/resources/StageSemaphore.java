package resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;
import static network.NetworkCommands.BYTEARRAYSIZE;

/**
 * All possible semaphore stages
 */
public enum StageSemaphore implements Serializable {
    RED(0), YELLOW(1), GREEN(2);

    private int actualStage;

    private StageSemaphore(int actualStage) {
        this.actualStage = actualStage;
    }

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

    public ByteArrayOutputStream serialize(DatagramSocket socket) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BYTEARRAYSIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return outputStream;
    }

}
