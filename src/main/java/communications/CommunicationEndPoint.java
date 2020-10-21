package communications;

import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used as a communication end point based on a {@link java.net.Socket}.
 * Its function is either a server or a client.
 */
public class CommunicationEndPoint {

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private Integer numberReceived = 0;
    private Integer numberSend = 0;

    public CommunicationEndPoint(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    @VisibleForTesting
    public CommunicationEndPoint() {
        this.socket = new Socket();
        this.outputStream = null;
        this.inputStream = null;
    }

    public Socket getSocket() {
        return socket;
    }

    public Integer getNumberReceived() {
        return numberReceived;
    }

    public Integer getNumberSend() {
        return numberSend;
    }

    public Object receiveFromPartner() throws IOException, ClassNotFoundException {
        Object receivedObject = this.inputStream.readObject();
        this.numberReceived += 1;
        return receivedObject;
    }

    public void sendToPartner(Object object) throws IOException {
        this.outputStream.writeObject(object);
        this.numberSend += 1;
    }

    public void closeCommunication() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
        this.numberReceived = 0;
        this.numberSend = 0;
    }

}
