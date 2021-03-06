package states;

import enumerations.ApplicationAction;
import enumerations.ApplicationState;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class shows all relevant states inside an application.
 */
public class ApplicationStates {

    private final String applicationName;

    private final List<SessionStates> clientSessionStates = new ArrayList<>(List.of());
    private final List<SessionStates> serverSessionStates = new ArrayList<>(List.of());

    private ApplicationState applicationState = ApplicationState.DOWN;
    private ApplicationAction applicationAction = ApplicationAction.NONE;
    private ServerSocket serverSocket;

    public ApplicationStates(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public List<SessionStates> getClientSessionStates() {
        return clientSessionStates;
    }

    public SessionStates addClientSessionStates(SessionStates clientSessionStates) {
        if (!this.clientSessionStates.contains(clientSessionStates)) {
            this.clientSessionStates.add(clientSessionStates);
        }
        return clientSessionStates;
    }

    public void removeClientSessionStates(SessionStates clientSessionStates) {
        this.clientSessionStates.remove(clientSessionStates);
    }

    public List<SessionStates> getServerSessionStates() {
        return serverSessionStates;
    }

    public SessionStates addServerSessionStates(SessionStates serverSessionStates) {
        if (!this.serverSessionStates.contains(serverSessionStates)) {
            this.serverSessionStates.add(serverSessionStates);
        }
        return serverSessionStates;
    }

    public void removeServerSessionStates(SessionStates serverSessionStates) {
        this.serverSessionStates.remove(serverSessionStates);

    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public ApplicationAction getApplicationAction() {
        return applicationAction;
    }

    public void setApplicationAction(ApplicationAction applicationAction) {
        this.applicationAction = applicationAction;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
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
