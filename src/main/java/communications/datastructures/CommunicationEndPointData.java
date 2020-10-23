package communications.datastructures;

import communications.CommunicationEndPoint;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to transport several data of the communication end point.<br>
 * It is used inside the class {@link SessionStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class CommunicationEndPointData implements Serializable {

    private final String NEW_LINE = "\n";
    private final String NOT_APPLICABLE = "n/a";

    private final Integer ownPortNumber;
    private final Integer foreignPortNumber;
    private final String foreignHost;
    private final Integer numberReceived;
    private final Integer numberSend;

    public CommunicationEndPointData(CommunicationEndPoint communicationEndPoint) {
        if (communicationEndPoint != null && communicationEndPoint.getSocket() != null) {
            this.ownPortNumber = communicationEndPoint.getSocket().getLocalPort();
            this.foreignPortNumber = communicationEndPoint.getSocket().getPort();
            if (communicationEndPoint.getSocket().getInetAddress() != null) {
                this.foreignHost = communicationEndPoint.getSocket().getInetAddress().getHostName();
            } else {
                this.foreignHost = NOT_APPLICABLE;
            }
            this.numberReceived=communicationEndPoint.getNumberReceived();
            this.numberSend=communicationEndPoint.getNumberSend();
        } else {
            this.ownPortNumber = 0;
            this.foreignPortNumber = 0;
            this.foreignHost = NOT_APPLICABLE;
            this.numberReceived = 0;
            this.numberSend = 0;
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

    public Integer getNumberReceived() {
        return numberReceived;
    }

    public Integer getNumberSend() {
        return numberSend;
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
        StringBuffer result = new StringBuffer(String.format("<communicationEndPointData>%s", NEW_LINE));

        result.append(String.format("<ownPortNumber>%d</ownPortNumber>%s", getOwnPortNumber(), NEW_LINE));
        result.append(String.format("<foreignPortNumber>%d</foreignPortNumber>%s", getForeignPortNumber(), NEW_LINE));
        result.append(String.format("<foreignHost>%s</foreignHost>%s", getForeignHost(), NEW_LINE));
        result.append(String.format("<numberReceived>%s</numberReceived>%s", getNumberReceived(), NEW_LINE));
        result.append(String.format("<numberSend>%s</numberSend>%s", getNumberSend(), NEW_LINE));

        result.append(String.format("</communicationEndPointData>%s", NEW_LINE));

        return result.toString();
    }

}
