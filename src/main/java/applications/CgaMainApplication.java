package applications;

import communications.datastructures.SessionRequest;
import communications.datastructures.SessionResponse;
import communications.enumerations.SessionFunction;
import communications.enumerations.SessionReturnCode;
import communications.enumerations.SessionType;
import dataStructures.CommonValues;
import enumerations.ConsoleCommand;
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
import java.util.Properties;

import static dataStructures.CommonValues.*;
import static enumerations.ApplicationState.*;

public class CgaMainApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaMainApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final String PROPERTY_FILE_NAME = "CgaMainApplication.properties";
    private static final String RESOURCES = "resources";
    private static final Properties properties = new Properties();
    private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException, ClassNotFoundException {
        ApplicationStates applicationStates = new ApplicationStates();

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
                        Integer.parseInt(properties.getProperty(PROPERTY_REMOTEAPPLICATION_INTERNAL_SERVER_PORT))));
        SessionStates serviceWebApplication = applicationStates.addClientSessionStates(
                new SessionStates(properties.getProperty(PROPERTY_WEBAPPLICATION_INTERNAL_SERVER_HOSTNAME),
                        Integer.parseInt(properties.getProperty(PROPERTY_WEBAPPLICATION_INTERNAL_SERVER_PORT))));

        while (applicationStates.getApplicationState() != STOPPED) {
            SessionReturnCode returnCodeFromRemoteApplication = SessionReturnCode.NOT_OKAY;
            SessionReturnCode returnCodeFromWebApplication = SessionReturnCode.NOT_OKAY;

            String command = readConsoleCommand();
            try {
                switch (ConsoleCommand.valueOf(command)) {
                    case SHOW_STATUS_ALL:
                        returnCodeFromRemoteApplication = connectToServer(serviceRemoteApplication);
                        returnCodeFromWebApplication = connectToServer(serviceWebApplication);
                        break;
                    case SHOW_STATUS_REMOTE:
                        returnCodeFromRemoteApplication = connectToServer(serviceRemoteApplication);
                        break;
                    case SHOW_STATUS_WEB:
                        returnCodeFromWebApplication = connectToServer(serviceWebApplication);
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.warn("invalid command {} received", command);
                continue;
            }

            if (returnCodeFromRemoteApplication == SessionReturnCode.OKAY) {
                processServiceRequest(serviceRemoteApplication);
            }

            if (returnCodeFromWebApplication == SessionReturnCode.OKAY) {
                processServiceRequest(serviceWebApplication);
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
        if (sessionStates.getSocket() != null) {
            return SessionReturnCode.OKAY;
        }
        try {
            sessionStates.setSocket(new Socket(sessionStates.getHostName(), sessionStates.getPortNumber()));
        } catch (ConnectException e) {
            logger.warn("{} against port {}", e.getMessage(), sessionStates.getPortNumber());
            return SessionReturnCode.NOT_OKAY;
        }
        logger.info("new session on port {} established, connected with port {} on host {}",
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

    private static void processServiceRequest(SessionStates serviceRemoteApplication) {
        System.out.println("process is active for " + serviceRemoteApplication.toString());
    }

    private static String readConsoleCommand() throws IOException {
        if (workingLevel == WorkingLevel.DEV) {
            System.out.println("Please enter one of the following commands:");
            for (ConsoleCommand entry :
                    ConsoleCommand.values()) {
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

}
