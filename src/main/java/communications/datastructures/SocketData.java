package communications.datastructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.net.Socket;

/**
 * This class is used to transport several data of the socket.<br>
 * It is used inside the class {@link SessionStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class SocketData implements Serializable {

    private final String NEW_LINE = "\n";
    private final String NOT_APPLICABLE = "n/a";

    private final Integer ownPortNumber;
    private final Integer foreignPortNumber;
    private final String foreignHost;

    public SocketData(Socket socket) {
        if (socket != null) {
            this.ownPortNumber = socket.getLocalPort();
            this.foreignPortNumber = socket.getPort();
            if (socket.getInetAddress() != null) {
                this.foreignHost = socket.getInetAddress().getHostName();
            } else {
                this.foreignHost = NOT_APPLICABLE;
            }
        } else {
            this.ownPortNumber = 0;
            this.foreignPortNumber = 0;
            this.foreignHost = NOT_APPLICABLE;
        }
    }

    public Integer getOwnPortNumber() {
        return ownPortNumber;
    }

    public Integer getForeignPortNumber() {
        return foreignPortNumber;
    }

    public String getForeignHost() {
        return foreignHost;
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
        StringBuffer result = new StringBuffer(String.format("<socketData>%s", NEW_LINE));

        result.append(String.format("<ownPortNumber>%d</ownPortNumber>%s", getOwnPortNumber(), NEW_LINE));
        result.append(String.format("<foreignPortNumber>%d</foreignPortNumber>%s", getForeignPortNumber(), NEW_LINE));
        result.append(String.format("<foreignHost>%s</foreignHost>%s", getForeignHost(), NEW_LINE));

        result.append(String.format("</socketData>%s", NEW_LINE));

        return result.toString();
    }

}
