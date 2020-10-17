package communications.datastructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import states.ApplicationStates;

import java.io.Serializable;
import java.net.SocketException;

/**
 * This class is used to transport several data over the application.<br>
 * It is used to create all needed data which reside in {@link states.ApplicationStates}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class ApplicationStatesData implements Serializable {

    private final String NEW_LINE = "\n";

    private final String applicationName;
    private final ClientSessionStatesData clientSessionStatesData;
    private final ServerSessionStatesData serverSessionStatesData;
    private final String applicationState;
    private final String applicationAction;
    private final ServerSocketData serverSocketData;


    public ApplicationStatesData(ApplicationStates applicationStates) throws SocketException {
        applicationName= applicationStates.getApplicationName();
        clientSessionStatesData =new ClientSessionStatesData(applicationStates.getClientSessionStates().size());
        serverSessionStatesData =new ServerSessionStatesData(applicationStates.getServerSessionStates().size());
        applicationState=applicationStates.getApplicationState().name();
        applicationAction=applicationStates.getApplicationAction().name();
        serverSocketData=new ServerSocketData(applicationStates.getServerSocket());
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ClientSessionStatesData getClientSessionStatesData() {
        return clientSessionStatesData;
    }

    public ServerSessionStatesData getServerSessionStatesData() {
        return serverSessionStatesData;
    }

    public String getApplicationState() {
        return applicationState;
    }

    public String getApplicationAction() {
        return applicationAction;
    }

    public ServerSocketData getServerSocketData() {
        return serverSocketData;
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
        StringBuffer result = new StringBuffer(String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>%s", NEW_LINE));
        result.append(String.format("<%s>%s", getClass().getSimpleName(), NEW_LINE));

        result.append(String.format("<applicationName>%s</applicationName>%s", getApplicationName(), NEW_LINE));

        result.append(getClientSessionStatesData().toXmlString());
        result.append(getServerSessionStatesData().toXmlString());

        result.append(String.format("<applicationState>%s</applicationState>%s", getApplicationState(), NEW_LINE));
        result.append(String.format("<applicationAction>%s</applicationAction>%s", getApplicationAction(), NEW_LINE));

        result.append(getServerSocketData().toXmlString());

        result.append(String.format("</%s>%s", getClass().getSimpleName(), NEW_LINE));

        return result.toString();
    }

}
