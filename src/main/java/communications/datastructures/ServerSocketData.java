package communications.datastructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * This class is used to transport several data of the server socket.<br>
 * It is used inside the class {@link ApplicationStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class ServerSocketData implements Serializable {

    private final String NEW_LINE = "\n";
    private final String NOT_APPLICABLE = "n/a";

    private final Integer localPortNumber;
    private final String localHost;
    private final Integer receiveBufferSize;

    public ServerSocketData(ServerSocket serverSocket) throws SocketException {
        if (serverSocket != null) {
            this.localPortNumber = serverSocket.getLocalPort();
            if (serverSocket.getInetAddress() != null) {
                this.localHost = serverSocket.getInetAddress().getHostName();
            } else {
                this.localHost = NOT_APPLICABLE;
            }
            this.receiveBufferSize = serverSocket.getReceiveBufferSize();
        } else {
            this.localPortNumber = 0;
            this.localHost = NOT_APPLICABLE;
            this.receiveBufferSize = 0;
        }
    }

    public Integer getLocalPortNumber() {
        return localPortNumber;
    }

    public String getLocalHost() {
        return localHost;
    }

    public Integer getReceiveBufferSize() {
        return receiveBufferSize;
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

    /**
     * Returns a string representation of the socket data as a xml representation.
     *
     * @return
     */
    public String toXmlString() {
        StringBuffer result = new StringBuffer(String.format("<serverSocketData>%s", NEW_LINE));

        result.append(String.format("<localPortNumber>%d</localPortNumber>%s", getLocalPortNumber(), NEW_LINE));
        result.append(String.format("<localHost>%s</localHost>%s", getLocalHost(), NEW_LINE));
        result.append(String.format("<receiveBufferSize>%d</receiveBufferSize>%s", getReceiveBufferSize(), NEW_LINE));

        result.append(String.format("</serverSocketData>%s", NEW_LINE));

        return result.toString();
    }

}
