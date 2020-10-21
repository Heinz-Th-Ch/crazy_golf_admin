package states;

import communications.CommunicationEndPoint;
import enumerations.SessionState;
import enumerations.SessionType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import runnables.ServiceSessionRunner;

import java.io.Serializable;

/**
 * This class shows all relevant states inside a communication connection.
 */
public class SessionStates implements Serializable {

    private final String hostName;
    private final Integer portNumber;
    private final SessionType sessionType;

    private SessionState sessionState = SessionState.INACTIVE;
    private ServiceSessionRunner serviceSessionRunner;
    private CommunicationEndPoint communicationEndPoint;

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

    public CommunicationEndPoint getCommunicationEndPoint() {
        return communicationEndPoint;
    }

    public void setCommunicationEndPoint(CommunicationEndPoint communicationEndPoint) {
        this.communicationEndPoint = communicationEndPoint;
    }

    public boolean isSessionUsable() {
        return communicationEndPoint != null
                && !communicationEndPoint.getSocket().isClosed()
                && !communicationEndPoint.getSocket().isInputShutdown()
                && !communicationEndPoint.getSocket().isOutputShutdown();
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

}
