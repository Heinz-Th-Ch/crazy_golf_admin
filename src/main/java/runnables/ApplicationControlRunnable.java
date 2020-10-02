package runnables;

import enumerations.ApplicationAction;
import enumerations.ApplicationState;
import enumerations.SessionState;
import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.Properties;

import static dataStructures.CommonValues.PROPERTY_INTERNAL_SERVER_PORT;
import static enumerations.ApplicationAction.STOP_DONE;
import static enumerations.ApplicationState.STOPPED;

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

    private void processApplicationStopping() throws IOException {
        if (applicationStates.getApplicationAction() == ApplicationAction.STOP
                && applicationStates.getApplicationState() == ApplicationState.STOPPING) {
            for (SessionStates entry : applicationStates.getServerSessionStates()) {
                if (entry.getSessionState() != SessionState.INACTIVE
                        && entry.getSessionState() != SessionState.STOPPING) {
                    logger.info("session stopping initiated at application stopping. Server port: {}, client port: {}, host: {}",
                            properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                            entry.getSocket().getPort(),
                            entry.getSocket().getInetAddress().getHostName());
                }
            }
            logger.info("application stopped");
            applicationStates.setApplicationAction(STOP_DONE);
            applicationStates.setApplicationState(STOPPED);
            applicationStates.getServerSocket().close();
        }
    }

    private void processStoppingSessions() throws IOException {
        for (SessionStates entry : applicationStates.getServerSessionStates()) {
            if (entry.getSessionState() == SessionState.STOPPING) {
                if (!entry.getServiceSessionRunner().isInterrupted()) {
                    entry.getServiceSessionRunner().interrupt();
                    logger.debug("service session runner interrupted. Server port: {}, client port: {}, host: {}",
                            properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                            entry.getSocket().getPort(),
                            entry.getSocket().getInetAddress().getHostName());
                }
                applicationStates.removeServerSessionStates(entry);
                logger.debug("session states removed from application list. Server port: {}, client port: {}, host: {}",
                        properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                        entry.getSocket().getPort(),
                        entry.getSocket().getInetAddress().getHostName());
                logger.info("session stopped. Server port: {}, client port: {}, host: {}",
                        properties.getProperty(PROPERTY_INTERNAL_SERVER_PORT),
                        entry.getSocket().getPort(),
                        entry.getSocket().getInetAddress().getHostName());
            }
        }
    }

}
