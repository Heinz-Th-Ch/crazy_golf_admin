package runnables;

import communications.datastructures.*;
import communications.enumerations.ServiceReturnCode;
import dataStructures.DataListContainerImpl;
import enumerations.PropertyKeys;
import enumerations.SessionState;
import org.jetbrains.annotations.VisibleForTesting;
import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.Properties;

import static enumerations.ApplicationAction.STOP;
import static enumerations.ApplicationState.STOPPING;

/**
 * This class represents the runner for all session relevant actions.
 */
public class ServiceSessionRunner extends Thread {

    private final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(ServiceSessionRunner.class);

    private final String runnerName;
    private final Properties properties;
    private final ApplicationStates applicationStates;
    private final SessionStates sessionStates;
    private final DataListContainerImpl dataListContainer;

    public ServiceSessionRunner(String runnerName,
                                Properties properties,
                                ApplicationStates applicationStates,
                                SessionStates sessionStates,
                                DataListContainerImpl dataListContainer) {
        this.runnerName = runnerName;
        this.properties = properties;
        this.applicationStates = applicationStates;
        this.sessionStates = sessionStates;
        this.dataListContainer = dataListContainer;
    }

    public void run() {
        try {
            logger.info("service session runner {} started. Runner name: {}",
                    getClass().getName(),
                    runnerName);

            while (sessionStates.getSessionState() != SessionState.STOPPING
                    && sessionStates.getSessionState() != SessionState.INACTIVE) {
                ServiceRequest request = (ServiceRequest) sessionStates.getCommunicationEndPoint()
                        .receiveFromPartner();
                logger.debug("request received from client: {}",
                        request.toString());
                switch (request.getFunction()) {
                    case RESTART_SERVICE_SESSIONS:
                        stopSession(request);
                        break;
                    case STOP_APPLICATIONS:
                        initiateApplicationStopping(request);
                        break;
                    case SHOW_STATUS_ALL:
                        processShowStatusAll(request);
                        break;
                    case SHOW_STATUS_APPLICATION:
                        processShowStatusApplication(request);
                        break;
                    case SHOW_STATUS_DATA:
                        processShowStatusData(request);
                        break;
                    case SHOW_STATUS_SESSION:
                        processShowStatusSession(request);
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    protected void processShowStatusAll(ServiceRequest request) throws IOException {
        ServiceResponse response = new ServiceResponse(request.getFunction(),
                ServiceReturnCode.OKAY);
        response.setApplicationName(applicationStates.getApplicationName());
        response.getApplicationStates().add(new ApplicationStatesData(applicationStates));
        response.getDataStates().add(new DataStatesData(applicationStates.getApplicationName(),
                dataListContainer));
        for (SessionStates entry : applicationStates.getClientSessionStates()) {
            response.getSessionStates().add(new SessionStatesData(applicationStates.getApplicationName(), entry));
        }
        for (SessionStates entry : applicationStates.getServerSessionStates()) {
            response.getSessionStates().add(new SessionStatesData(applicationStates.getApplicationName(), entry));
        }
        sendStatusResponse(response);
    }

    @VisibleForTesting
    protected void processShowStatusApplication(ServiceRequest request) throws IOException {
        ServiceResponse response = new ServiceResponse(request.getFunction(),
                ServiceReturnCode.OKAY);
        response.setApplicationName(applicationStates.getApplicationName());
        response.getApplicationStates().add(new ApplicationStatesData(applicationStates));
        sendStatusResponse(response);
    }

    @VisibleForTesting
    protected void processShowStatusData(ServiceRequest request) throws IOException {
        ServiceResponse response = new ServiceResponse(request.getFunction(),
                ServiceReturnCode.OKAY);
        response.setApplicationName(applicationStates.getApplicationName());
        response.getDataStates().add(new DataStatesData(applicationStates.getApplicationName(),
                dataListContainer));
        sendStatusResponse(response);
    }

    @VisibleForTesting
    protected void processShowStatusSession(ServiceRequest request) throws IOException {
        ServiceResponse response = new ServiceResponse(request.getFunction(),
                ServiceReturnCode.OKAY);
        response.setApplicationName(applicationStates.getApplicationName());
        for (SessionStates entry : applicationStates.getClientSessionStates()) {
            response.getSessionStates().add(new SessionStatesData(applicationStates.getApplicationName(), entry));
        }
        for (SessionStates entry : applicationStates.getServerSessionStates()) {
            response.getSessionStates().add(new SessionStatesData(applicationStates.getApplicationName(), entry));
        }
        sendStatusResponse(response);
    }

    @VisibleForTesting
    protected void initiateApplicationStopping(ServiceRequest request) throws IOException {
        stopSession(request);
        logger.info("application stopping initiated by main application client");
        applicationStates.setApplicationAction(STOP);
        applicationStates.setApplicationState(STOPPING);
    }

    private void sendStatusResponse(ServiceResponse response) throws IOException {
        sessionStates.getCommunicationEndPoint().sendToPartner(response);
        logger.debug("response sent to client: {}",
                response.toString());
    }

    private void sendStopResponse(ServiceRequest request) throws IOException {
        ServiceResponse response = new ServiceResponse(request.getFunction(),
                ServiceReturnCode.OKAY);
        sessionStates.getCommunicationEndPoint().sendToPartner(response);
        logger.debug("response sent to client: {}",
                response.toString());
    }

    @VisibleForTesting
    protected void stopSession(ServiceRequest request) throws IOException {
        sendStopResponse(request);
        sessionStates.getCommunicationEndPoint().closeCommunication();
        logger.info("session stopping. Server port: {}, client port: {}, host: {}",
                properties.getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey()),
                sessionStates.getCommunicationEndPoint().getSocket().getPort(),
                sessionStates.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
        sessionStates.setSessionState(SessionState.STOPPING);
    }

}
