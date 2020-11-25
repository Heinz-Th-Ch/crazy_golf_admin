package runnables;

import states.ApplicationStates;
import states.SessionStates;
import utilities.ApplicationLoggerUtil;

import java.util.Properties;

/**
 * This is the interface between the  main application and the web browser of crazy golf administration.<br>
 * It is used to manage the whole application.
 */
public class MainApplicationWebServer extends Thread {

    private final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(MainApplicationWebServer.class);

    private final String runnerName;
    private final Properties properties;
    private final ApplicationStates applicationStates;
    private final SessionStates sessionStates;

    public MainApplicationWebServer(String runnerName,
                                    Properties properties,
                                    ApplicationStates applicationStates,
                                    SessionStates sessionStates) {
        this.runnerName = runnerName;
        this.properties = properties;
        this.applicationStates = applicationStates;
        this.sessionStates = sessionStates;
    }

    public void run() {

    }

}
