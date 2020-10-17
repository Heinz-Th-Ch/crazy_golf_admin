package communications.datastructures;

import enumerations.SessionState;
import enumerations.SessionType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import states.SessionStates;

import java.io.Serializable;

/**
 * This class is used to transport several data over communication sessions.<br>
 * It is used to create all needed data which reside in {@link states.SessionStates}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class SessionStatesData implements Serializable {

    private final String NEW_LINE = "\n";

    private final String applicationName;
    private final String hostName;
    private final Integer portNumber;
    private final SessionType sessionType;
    private final SessionState sessionState;
    private final ServiceSessionRunnerData serviceSessionRunnerData;
    private final SocketData socketData;
    private final Integer numberReceived;
    private final Integer numberSend;

    public SessionStatesData(String applicationName, SessionStates sessionStates) {
        this.applicationName = applicationName;
        this.hostName = sessionStates.getHostName();
        this.portNumber = sessionStates.getPortNumber();
        this.sessionType = sessionStates.getSessionType();
        this.sessionState = sessionStates.getSessionState();
        this.serviceSessionRunnerData = new ServiceSessionRunnerData(sessionStates.getServiceSessionRunner());
        this.socketData = new SocketData(sessionStates.getSocket());
        this.numberReceived = sessionStates.getNumberReceived();
        this.numberSend = sessionStates.getNumberSend();
    }

    public String getApplicationName() {
        return applicationName;
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

    public ServiceSessionRunnerData getServiceSessionRunnerData() {
        return serviceSessionRunnerData;
    }

    public SocketData getSocketData() {
        return socketData;
    }

    public Integer getNumberReceived() {
        return numberReceived;
    }

    public Integer getNumberSend() {
        return numberSend;
    }

    /**
     * Returns a string representation of the session states. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    /**
     * Returns a string representation of the session states as a xml representation.
     *
     * @return
     */
    public String toXmlString() {
        StringBuffer result = new StringBuffer(String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>%s", NEW_LINE));
        result.append(String.format("<%s>%s", getClass().getSimpleName(), NEW_LINE));

        result.append(String.format("<applicationName>%s</applicationName>%s", getApplicationName(), NEW_LINE));
        result.append(String.format("<hostName>%s</hostName>%s", getHostName(), NEW_LINE));
        result.append(String.format("<portNumber>%d</portNumber>%s", getPortNumber(), NEW_LINE));
        result.append(String.format("<sessionType>%s</sessionType>%s", getSessionType().name(), NEW_LINE));
        result.append(String.format("<sessionState>%s</sessionState>%s", getSessionState().name(), NEW_LINE));

        result.append(getServiceSessionRunnerData().toXmlString());
        result.append(getSocketData().toXmlString());


        result.append(String.format("<numberReceived>%d</numberReceived>%s", getNumberReceived(), NEW_LINE));
        result.append(String.format("<numberSend>%d</numberSend>%s", getNumberSend(), NEW_LINE));

        result.append(String.format("</%s>%s", getClass().getSimpleName(), NEW_LINE));

        return result.toString();
    }
}
