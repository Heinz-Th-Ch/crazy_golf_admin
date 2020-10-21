package runnables;

import enumerations.ApplicationState;
import enumerations.SessionState;
import org.jetbrains.annotations.VisibleForTesting;
import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static dataStructures.CommonValues.PROPERTY_INTERNAL_SERVER_PORT;
import static enumerations.ApplicationAction.STOP;
import static enumerations.ApplicationAction.STOP_DONE;
import static enumerations.ApplicationState.STOPPED;
import static enumerations.SessionState.INACTIVE;

/**
 * This class represents the application controller.
 * It has several missions. That are:<br>
 * <ol>
 *     <li>remove of STOPPED sessions including corresponding threads</li>
 * </ol>
 */
public class ApplicationControlRunnable extends Thread {

    private final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(ApplicationControlRunnable.class);

    private final String runnerName;
    private final Properties properties;
    private final ApplicationStates applicationStates;

    public ApplicationControlRunnable(String runnerName,
                                      Properties properties,
                                      ApplicationStates applicationStates) {
        this.runnerName = runnerName;
        this.properties = properties;
        this.applicationStates = applicationStates;
    }

    public void run() {
        try {
            logger.info("application control runner {} started. Runner name: {}",
                    getClass().getName(),
                    runnerName);

            while (applicationStates.getApplicationAction() != STOP_DONE) {
                try {
                    int PAUSE_FOR_ONE_SECOND = 1000;
                    Thread.sleep(PAUSE_FOR_ONE_SECOND);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                processStoppingSessions();
                processApplicationStopping();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    protected void processApplicationStopping() throws IOException {
        if (applicationStates.getApplicationAction() == STOP
                && applicationStates.getApplicationState() == ApplicationState.STOPPING) {
            for (SessionStates entry : applicationStates.getServerSessionStates()) {
                if (entry.getSessionState() != INACTIVE
                        && entry.getSessionState() != SessionState.STOPPING) {
                    entry.setSessionState(SessionState.STOPPING);
                    logger.info("session stopping initiated at application stopping. Server port: {}, client port: {}, host: {}",
                            properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                            entry.getCommunicationEndPoint().getSocket().getPort(),
                            entry.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
                }
            }
            logger.info("application stopped");
            applicationStates.setApplicationAction(STOP_DONE);
            applicationStates.setApplicationState(STOPPED);
            applicationStates.getServerSocket().close();
        }
    }

    @VisibleForTesting
    protected void processStoppingSessions() throws IOException {
        List<SessionStates> removableSessionStates = new ArrayList<>(List.of());
        for (SessionStates entry : applicationStates.getServerSessionStates()) {
            if (entry.getSessionState() == SessionState.STOPPING) {
                removableSessionStates.add(entry);
                if (!entry.getServiceSessionRunner().isInterrupted()) {
                    entry.getServiceSessionRunner().interrupt();
                    logger.debug("service session runner interrupted. Server port: {}, client port: {}, host: {}",
                            properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                            entry.getCommunicationEndPoint().getSocket().getPort(),
                            entry.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
                }
                logger.debug("session states removed from application list. Server port: {}, client port: {}, host: {}",
                        properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                        entry.getCommunicationEndPoint().getSocket().getPort(),
                        entry.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
                logger.info("session stopped. Server port: {}, client port: {}, host: {}",
                        properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                        entry.getCommunicationEndPoint().getSocket().getPort(),
                        entry.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
            }
        }
        for (SessionStates entry : removableSessionStates) {
            applicationStates.removeServerSessionStates(entry);
        }
    }

}
