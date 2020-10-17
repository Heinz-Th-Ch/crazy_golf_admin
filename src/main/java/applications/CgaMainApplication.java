package applications;

import communications.datastructures.*;
import communications.enumerations.*;
import dataStructures.CommonValues;
import enumerations.SessionState;
import enumerations.WorkingLevel;
import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

import static communications.enumerations.ServiceFunction.STOP_APPLICATIONS;
import static communications.enumerations.SessionReturnCode.OKAY;
import static dataStructures.CommonValues.*;
import static enumerations.ApplicationAction.NONE;
import static enumerations.ApplicationAction.STOP;
import static enumerations.ApplicationState.*;
import static enumerations.SessionType.CLIENT_SESSION;

public class CgaMainApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaMainApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final String PROPERTY_FILE_NAME = "CgaMainApplication.properties";
    private static final String RESOURCES = "resources";
    private static final Properties properties = new Properties();
    private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private static final String NEW_LINE = "\n";
    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException, ClassNotFoundException {
        ApplicationStates applicationStates = new ApplicationStates(CgaMainApplication.class.getSimpleName());

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
        logger.setLogOutputStream(properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH),
                properties.getProperty(CommonValues.PROPERTY_LOG_FILE_NAME));
        applicationStates.setApplicationState(INITIALIZED);
        logger.info("application initialized");

        logger.info("application started");
        applicationStates.setApplicationState(ACTIVE);
        runApplication(applicationStates);
        logger.info("application ended");
    }

    private static void runApplication(ApplicationStates applicationStates) throws IOException, ClassNotFoundException {
        /*
        Server Socket für den Web-Zugang folgt später, in Anlehnung der Remote Applikation
         */

        SessionStates serviceRemoteApplication = applicationStates.addClientSessionStates(
                new SessionStates(properties.getProperty(PROPERTY_REMOTEAPPLICATION_INTERNAL_SERVER_HOSTNAME),
                        Integer.parseInt(properties.getProperty(PROPERTY_REMOTEAPPLICATION_INTERNAL_SERVER_PORT)),
                        CLIENT_SESSION));
        SessionStates serviceWebApplication = applicationStates.addClientSessionStates(
                new SessionStates(properties.getProperty(PROPERTY_WEBAPPLICATION_INTERNAL_SERVER_HOSTNAME),
                        Integer.parseInt(properties.getProperty(PROPERTY_WEBAPPLICATION_INTERNAL_SERVER_PORT)),
                        CLIENT_SESSION));

        while (applicationStates.getApplicationState() != STOPPED) {
            SessionReturnCode returnCodeFromRemoteApplication;
            SessionReturnCode returnCodeFromWebApplication;

            String command = readConsoleCommand();
            ServiceFunction serviceFunction;
            try {
                serviceFunction = ServiceFunction.valueOf(command);
                returnCodeFromRemoteApplication = connectToServer(serviceRemoteApplication);
                returnCodeFromWebApplication = connectToServer(serviceWebApplication);
            } catch (IllegalArgumentException e) {
                logger.warn("invalid command {} received", command);
                continue;
            }

            if (serviceFunction == STOP_APPLICATIONS) {
                logger.info("application stopping initiated");
                applicationStates.setApplicationAction(STOP);
                applicationStates.setApplicationState(STOPPING);
            }

            if (returnCodeFromRemoteApplication == OKAY) {
                processServiceRequest(serviceRemoteApplication,
                        serviceFunction);
            }

            if (returnCodeFromWebApplication == OKAY) {
                processServiceRequest(serviceWebApplication,
                        serviceFunction);
            }

            if (serviceFunction == STOP_APPLICATIONS) {
                logger.info("application stopped");
                applicationStates.setApplicationAction(NONE);
                applicationStates.setApplicationState(STOPPED);
            }

        }
    }

    private static void adjustProperties() {
        properties.setProperty(CommonValues.PROPERTY_LOG_FILE_PATH,
                properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH).replace(CommonValues.DIRECTORY_PLACE_HOLDER,
                        workingLevel.getDirectoryName()));
    }

    private static void checkArguments(String[] args) {
        if (args.length == NUMBER_OF_ARGUMENTS)
            return;
        throw new IllegalArgumentException(String.format("illegal number of arguments. Expected: %d, received: %d",
                NUMBER_OF_ARGUMENTS,
                args.length));
    }

    private static SessionReturnCode connectToServer(SessionStates sessionStates) throws IOException, ClassNotFoundException {
        if (sessionStates.isSessionUsable()) {
            return OKAY;
        }
        try {
            sessionStates.setSocket(new Socket(sessionStates.getHostName(), sessionStates.getPortNumber()));
        } catch (ConnectException e) {
            logger.warn("{} against port {}", e.getMessage(), sessionStates.getPortNumber());
            return SessionReturnCode.NOT_OKAY;
        }
        logger.info("session established. Client port: {}, server port: {}, host: {}",
                sessionStates.getSocket().getLocalPort(),
                sessionStates.getPortNumber(),
                sessionStates.getHostName());
        sessionStates.setSessionState(SessionState.ACTIVE);
        sessionStates.getOutputStream()
                .writeObject(new SessionRequest(SessionFunction.SET_SESSION_TYPE,
                        SessionType.SERVICE_SESSION));
        sessionStates.incrementNumberSend();
        SessionReturnCode returnCode = ((SessionResponse) sessionStates.getInputStream().readObject()).getReturnCode();
        sessionStates.incrementNumberReceived();
        return returnCode;
    }

    private static void processServiceRequest(SessionStates sessionStates,
                                              ServiceFunction serviceFunction) throws IOException, ClassNotFoundException {
        logger.debug("process is active for {} and action {}",
                sessionStates.toString(),
                serviceFunction.name());
        ServiceRequest request = new ServiceRequest(serviceFunction);
        sessionStates.getOutputStream().writeObject(request);
        sessionStates.incrementNumberSend();
        ServiceResponse response = (ServiceResponse) sessionStates.getInputStream().readObject();
        sessionStates.incrementNumberReceived();
        logger.debug("response received from server: {}",
                response.toString());
        logger.info("Results of {} request from {}:", response.getFunction(), response.getApplicationName());
        switch (serviceFunction) {
            case SHOW_STATUS_ALL:
                showStatusApplication(response.getApplicationStates(),
                        serviceFunction);
                showStatusData(response.getDataStates(),
                        serviceFunction);
                showStatusSession(response.getSessionStates(),
                        serviceFunction);
                break;
            case SHOW_STATUS_APPLICATION:
                showStatusApplication(response.getApplicationStates(),
                        serviceFunction);
                break;
            case SHOW_STATUS_DATA:
                showStatusData(response.getDataStates(),
                        serviceFunction);
                break;
            case SHOW_STATUS_SESSION:
                showStatusSession(response.getSessionStates(),
                        serviceFunction);
                break;
            case RESTART_SERVICE_SESSIONS:
            case STOP_APPLICATIONS:
                stopSession(response, sessionStates);
                break;
        }
    }

    private static String readConsoleCommand() throws IOException {
        if (workingLevel == WorkingLevel.DEV) {
            System.out.println("Please enter one of the following commands:");
            for (ServiceFunction entry :
                    ServiceFunction.values()) {
                System.out.println("\t\t" + entry);
            }
        } else {
            System.out.println("Please enter a valid command:");
        }
        return console.readLine();
    }

    private static void setWorkingLevelValues(String argument) {
        workingLevel = WorkingLevel.valueOf(argument);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

    private static void showStatusApplication(List<ApplicationStatesData> applicationStates,
                                              ServiceFunction serviceFunction) throws IOException {
        if (applicationStates.isEmpty()) {
            logger.warn("no application states received");
            return;
        }
        logger.info("data of entry 1 of {}: {}",
                applicationStates.get(0).getClass().getSimpleName(),
                applicationStates.get(0).toXmlString());
    }

    private static void showStatusData(List<DataStatesData> dataStates,
                                       ServiceFunction serviceFunction) throws IOException {
        if (dataStates.isEmpty()) {
            logger.warn("no data states received");
            return;
        }
        logger.info("data of entry 1 of {}: {}",
                dataStates.get(0).getClass().getSimpleName(),
                dataStates.get(0).toXmlString());
    }

    private static void showStatusSession(List<SessionStatesData> sessionStates,
                                          ServiceFunction serviceFunction) throws IOException {
        if (sessionStates.isEmpty()) {
            logger.warn("no session states received");
            return;
        }
        for (int i = 0; i < sessionStates.size(); i++) {
            logger.info("data of entry {} of {}: {}",
                    i + 1,
                    sessionStates.get(i).getClass().getSimpleName(),
                    sessionStates.get(i).toXmlString());
        }
    }

    private static void stopSession(ServiceResponse response, SessionStates sessionStates) throws IOException {
        if (response.getReturnCode() == ServiceReturnCode.OKAY) {
            sessionStates.setSessionState(SessionState.STOPPING);
            sessionStates.getInputStream().close();
            sessionStates.getOutputStream().close();
            sessionStates.getSocket().close();
            sessionStates.clearNumberReceived();
            sessionStates.clearNumberSend();
            sessionStates.setSessionState(SessionState.INACTIVE);
        }
    }

}
