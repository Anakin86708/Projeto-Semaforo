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
    RED, YELLOW, GREEN;

    public StageSemaphore changeStage() {
        switch (this) {
            case RED -> {
                return GREEN;
            }
            case YELLOW -> {
                return RED;
            }
            case GREEN -> {
                return YELLOW;
            }
            default -> {
                return RED;
            }
        }
    }

    public ByteArrayOutputStream serialize(DatagramSocket socket) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BYTEARRAYSIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return outputStream;
    }

}
