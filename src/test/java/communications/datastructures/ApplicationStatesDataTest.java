package communications.datastructures;

import abstracts.AbstractPlainJava;
import enumerations.SessionType;
import org.junit.Test;
import states.ApplicationStates;
import states.SessionStates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * JUnit test for {@link ApplicationStatesData}.
 */
public class ApplicationStatesDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_SERVER_SOCKET = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ApplicationStatesData>\n" +
            "<applicationName>ApplicationStatesDataTest</applicationName>\n" +
            "<clientSessionStatesData>\n" +
            "<listSize>9</listSize>\n" +
            "</clientSessionStatesData>\n" +
            "<serverSessionStatesData>\n" +
            "<listSize>1</listSize>\n" +
            "</serverSessionStatesData>\n" +
            "<applicationState>DOWN</applicationState>\n" +
            "<applicationAction>NONE</applicationAction>\n" +
            "<serverSocketData>\n" +
            "<localPortNumber>-1</localPortNumber>\n" +
            "<localHost>n/a</localHost>\n" +
            "<receiveBufferSize>65536</receiveBufferSize>\n" +
            "</serverSocketData>\n" +
            "</ApplicationStatesData>\n";

    private final String EXPECTED_WITHOUT_SERVER_SOCKET = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ApplicationStatesData>\n" +
            "<applicationName>ApplicationStatesDataTest</applicationName>\n" +
            "<clientSessionStatesData>\n" +
            "<listSize>19</listSize>\n" +
            "</clientSessionStatesData>\n" +
            "<serverSessionStatesData>\n" +
            "<listSize>101</listSize>\n" +
            "</serverSessionStatesData>\n" +
            "<applicationState>DOWN</applicationState>\n" +
            "<applicationAction>NONE</applicationAction>\n" +
            "<serverSocketData>\n" +
            "<localPortNumber>0</localPortNumber>\n" +
            "<localHost>n/a</localHost>\n" +
            "<receiveBufferSize>0</receiveBufferSize>\n" +
            "</serverSocketData>\n" +
            "</ApplicationStatesData>\n";

    private final ApplicationStates applicationStates = new ApplicationStates(getClass().getSimpleName());

    private ApplicationStatesData testableData;

    @Test
    public void toXmlStringWithServerSocket() throws IOException {
        for (int i = 0; i < 9; i++) {
            applicationStates.addClientSessionStates(new SessionStates("X",
                    35,
                    SessionType.CLIENT_SESSION));
        }
        for (int i = 0; i < 1; i++) {
            applicationStates.addServerSessionStates(new SessionStates("X",
                    35,
                    SessionType.SERVER_SESSION));
        }
        applicationStates.setServerSocket(new ServerSocket());
        testableData = new ApplicationStatesData(applicationStates);
        assertEquals("wrong result received", EXPECTED_WITH_SERVER_SOCKET, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithoutServerSocket() throws SocketException {
        for (int i = 0; i < 19; i++) {
            applicationStates.addClientSessionStates(new SessionStates("X",
                    35,
                    SessionType.CLIENT_SESSION));
        }
        for (int i = 0; i < 101; i++) {
            applicationStates.addServerSessionStates(new SessionStates("X",
                    35,
                    SessionType.SERVER_SESSION));
        }
        testableData = new ApplicationStatesData(applicationStates);
        assertEquals("wrong result received", EXPECTED_WITHOUT_SERVER_SOCKET, testableData.toXmlString());
    }

}