package communications.datastructures;

import abstracts.AbstractPlainJava;
import enumerations.SessionType;
import org.junit.Test;
import states.ApplicationStates;
import states.SessionStates;

public class SessionStatesDataTest extends AbstractPlainJava {

    private final String EXPECTED_RESULT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<SessionStatesData>\n" +
            "<applicationName>SessionStatesDataTest</applicationName>\n" +
            "<hostName>localhost</hostName>\n" +
            "<portNumber>0</portNumber>\n" +
            "<sessionType>CLIENT_SESSION</sessionType>\n" +
            "<sessionState>INACTIVE</sessionState>\n" +
            "<serviceSessionRunnerData>\n" +
            "<runnerId>0</runnerId>\n" +
            "<runnerName>n/a</runnerName>\n" +
            "<runnerState>n/a</runnerState>\n" +
            "</serviceSessionRunnerData>\n" +
            "<communicationEndPointData>\n" +
            "<ownPortNumber>0</ownPortNumber>\n" +
            "<foreignPortNumber>0</foreignPortNumber>\n" +
            "<foreignHost>n/a</foreignHost>\n" +
            "<numberReceived>0</numberReceived>\n" +
            "<numberSend>0</numberSend>\n" +
            "</communicationEndPointData>\n" +
            "</SessionStatesData>\n";

    private final ApplicationStates applicationStates = new ApplicationStates(getClass().getSimpleName());
    private final SessionStates sessionStates = new SessionStates("localhost",
            0,
            SessionType.CLIENT_SESSION);

    private SessionStatesData testableData;

    @Test
    public void toXmlString() {
        testableData = new SessionStatesData(applicationStates.getApplicationName(),
                sessionStates);
        assertEquals("wrong result received", EXPECTED_RESULT, testableData.toXmlString());
    }
}