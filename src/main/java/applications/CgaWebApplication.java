package applications;

import communications.CommunicationEndPoint;
import communications.datastructures.SessionRequest;
import communications.datastructures.SessionResponse;
import communications.enumerations.SessionFunction;
import communications.enumerations.SessionReturnCode;
import communications.enumerations.SessionType;
import dataStructures.CommonValues;
import enumerations.ApplicationAction;
import enumerations.PropertyKeys;
import enumerations.SessionState;
import enumerations.WorkingLevel;
import org.apache.log4j.lf5.LogLevel;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import runnables.ApplicationControlRunnable;
import runnables.ServiceSessionRunner;
import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Properties;

import static enumerations.ApplicationState.*;
import static enumerations.SessionType.SERVER_SESSION;

/**
 * This is the web application of crazy golf administration.<br>
 * It is used to manage the data of the application.
 */
public class CgaWebApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaWebApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final String PROPERTY_FILE_NAME = "CgaWebApplication.properties";
    private static final String RESOURCES = "resources";

    private static final Properties properties = new Properties();

    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException, ClassNotFoundException {
        ApplicationStates applicationStates = new ApplicationStates(CgaWebApplication.class.getSimpleName());

        logger.info("application starting");
        applicationStates.setApplicationState(STARTING);
        checkArguments(args);
        setWorkingLevelValues(args[0]);
        logger.debug("application runs in {} mode", workingLevel.getWorkingMode());

        try {
            PropertiesUtil.loadProperties(properties, property_file_path_and_name);
            adjustProperties();
        } catch (IOException e) {
            logger.error("load of properties from {} failed. {}", property_file_path_and_name, e.getStackTrace());
            throw e;
        }

        logger.setLogOutputStream(properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()),
                properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_NAME.getPropertyKey()));
        applicationStates.setApplicationState(INITIALIZED);
        logger.info("application initialized");

        logger.info("application started");
        applicationStates.setApplicationState(ACTIVE);
        runApplication(applicationStates);
        logger.info("application ended");
    }

    private static void runApplication(ApplicationStates applicationStates) throws IOException, ClassNotFoundException {
        ApplicationControlRunnable applicationControl = new ApplicationControlRunnable("applicationControl",
                properties,
                applicationStates);
        applicationControl.start();

        /*
        Server Socket für den Web-Zugang folgt später
         */

        applicationStates.setServerSocket(new ServerSocket(Integer.parseInt(properties
                .getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey())),
                Integer.parseInt(properties
                        .getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_NUMBER_OF_PARALLEL_CONNECTS
                                .getPropertyKey()))));
        logger.info("server socket on port {} established", properties
                .getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey()));

        while (applicationStates.getApplicationState() != STOPPED) {
            SessionStates actualSessionStates = applicationStates.addServerSessionStates(
                    new SessionStates("notUsed",
                            Integer.parseInt(properties
                                    .getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey())),
                            SERVER_SESSION));
            try {
                actualSessionStates.setCommunicationEndPoint(
                        new CommunicationEndPoint(applicationStates.getServerSocket().accept()));
                actualSessionStates.setSessionState(SessionState.ACCEPTED);
            } catch (SocketException e) {
                if (applicationStates.getApplicationAction() != ApplicationAction.STOP_DONE
                        || applicationStates.getApplicationState() != STOPPED) {
                    throw e;
                }
                logger.info("server socket on port {} close during application stopping",
                        properties.getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey()));
                continue;
            }

            if (!actualSessionStates.getCommunicationEndPoint().getSocket().getInetAddress().getHostName()
                    .equals(properties
                            .getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_ACCEPTED_HOSTS.getPropertyKey()))) {
                logger.warn("new session not from {} but from {}. Session is rejected",
                        properties.getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_ACCEPTED_HOSTS.getPropertyKey()),
                        actualSessionStates.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
                rejectSession(applicationStates, actualSessionStates);
                continue;
            }

            SessionRequest request = (SessionRequest) actualSessionStates.getCommunicationEndPoint()
                    .receiveFromPartner();
            if (request.getFunction() != SessionFunction.SET_SESSION_TYPE) {
                logger.error("received request is not {} but {}. Session is rejected",
                        SessionFunction.SET_SESSION_TYPE,
                        request.getFunction());
                rejectSession(applicationStates, actualSessionStates);
                continue;
            }

            SessionType sessionType = request.getSessionType();
            if (sessionType == SessionType.SERVICE_SESSION) {
                logConnectionAndRespondRequest(LogLevel.INFO,
                        SessionReturnCode.OKAY,
                        actualSessionStates,
                        null);
                actualSessionStates.setServiceSessionRunner(new ServiceSessionRunner(sessionType.getSessionName(),
                        properties,
                        applicationStates,
                        actualSessionStates,
                        null));
                actualSessionStates.getServiceSessionRunner().start();
            } else {
                logConnectionAndRespondRequest(LogLevel.ERROR,
                        SessionReturnCode.NOT_OKAY,
                        actualSessionStates,
                        request.getSessionType());
                rejectSession(applicationStates, actualSessionStates);
            }

        }
        if (!applicationControl.isInterrupted()) {
            applicationControl.interrupt();
        }
    }

    private static void adjustProperties() {
        properties.setProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey(),
                properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey())
                        .replace(CommonValues.DIRECTORY_PLACE_HOLDER,
                                workingLevel.getDirectoryName()));
    }

    @VisibleForTesting
    protected static void checkArguments(String[] args) {
        if (args.length == NUMBER_OF_ARGUMENTS)
            return;
        throw new IllegalArgumentException(String.format("illegal number of arguments. Expected: %d, received: %d",
                NUMBER_OF_ARGUMENTS,
                args.length));
    }

    private static void logConnectionAndRespondRequest(LogLevel logLevel,
                                                       SessionReturnCode returnCode,
                                                       SessionStates sessionStates,
                                                       @Nullable SessionType sessionType) throws IOException {
        if (returnCode == SessionReturnCode.OKAY) {
            sessionStates.setSessionState(SessionState.DEFINED);
        }
        if (logLevel == LogLevel.INFO) {
            logger.info("session established. Server port: {}, client port: {}, host: {}",
                    properties.getProperty(PropertyKeys.PROPERTY_INTERNAL_SERVER_PORT.getPropertyKey()),
                    sessionStates.getCommunicationEndPoint().getSocket().getPort(),
                    sessionStates.getCommunicationEndPoint().getSocket().getInetAddress().getHostName());
        } else {
            logger.error("received session type {} is not valid at this place. Session is rejected",
                    sessionType);
        }
        sessionStates.getCommunicationEndPoint().sendToPartner(new SessionResponse(returnCode));
    }

    private static void rejectSession(ApplicationStates applicationStates,
                                      SessionStates sessionStates) throws IOException {
        sessionStates.getCommunicationEndPoint().closeCommunication();
        applicationStates.removeServerSessionStates(sessionStates);
    }

    private static void setWorkingLevelValues(String argument) {
        workingLevel = WorkingLevel.valueOf(argument);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }


}
