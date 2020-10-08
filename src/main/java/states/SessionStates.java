package states;

import enumerations.SessionState;
import enumerations.SessionType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jetbrains.annotations.VisibleForTesting;
import runnables.ServiceSessionRunner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * This class shows all relevant states inside a communication connection.
 */
public class SessionStates implements Serializable {

    private final String hostName;
    private final Integer portNumber;
    private final SessionType sessionType;

    private SessionState sessionState = SessionState.INACTIVE;
    private ServiceSessionRunner serviceSessionRunner;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Integer numberReceived = 0;
    private Integer numberSend = 0;

    public SessionStates(String hostName,
                         Integer portNumber,
                         SessionType sessionType) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.sessionType = sessionType;
    }

    public String getHostName() {
        return hostName;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public ServiceSessionRunner getServiceSessionRunner() {
        return serviceSessionRunner;
    }

    public void setServiceSessionRunner(ServiceSessionRunner serviceSessionRunner) {
        this.serviceSessionRunner = serviceSessionRunner;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        setSocketWithoutStreams(socket);
        setOutputStream(new ObjectOutputStream(this.socket.getOutputStream()));
        setInputStream(new ObjectInputStream(this.socket.getInputStream()));
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Integer getNumberReceived() {
        return numberReceived;
    }

    public void clearNumberReceived() {
        numberReceived = 0;
    }

    public void incrementNumberReceived() {
        numberReceived += 1;
    }

    public Integer getNumberSend() {
        return numberSend;
    }

    public void clearNumberSend() {
        numberSend = 0;
    }

    public void incrementNumberSend() {
        numberSend += 1;
    }

    public boolean isSessionUsable() {
        return (socket != null)
                && !socket.isClosed()
                && !socket.isInputShutdown()
                && !socket.isOutputShutdown();
    }

    /**
     * Returns a string representation of the ball. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @VisibleForTesting
    public void setSocketWithoutStreams(Socket socket) {
        this.socket = socket;
    }

}
