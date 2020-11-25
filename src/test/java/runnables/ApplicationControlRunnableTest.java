package runnables;

import abstracts.AbstractPlainJava;
import communications.CommunicationEndPoint;
import enumerations.ApplicationState;
import enumerations.SessionType;
import org.junit.Before;
import org.junit.Test;
import states.ApplicationStates;
import states.SessionStates;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import static enumerations.ApplicationAction.STOP;
import static enumerations.ApplicationAction.STOP_DONE;
import static enumerations.ApplicationState.STOPPED;
import static enumerations.SessionState.DEFINED;
import static enumerations.SessionState.STOPPING;
import static org.mockito.Mockito.*;

/**
 * JUnit test for {@link ApplicationControlRunnable}.
 */
public class ApplicationControlRunnableTest extends AbstractPlainJava {

    public static final String PROPERTY_INTERNAL_SERVER_PORT = "internal.server.port";

    private final ServerSocket serverSocketMock = mock(ServerSocket.class);
    private final Socket socketMock = mock(Socket.class);
    private final InetAddress inetAddressMock = mock(InetAddress.class);
    private final ServiceSessionRunner serviceSessionRunnerMock = mock(ServiceSessionRunner.class);
    private final CommunicationEndPoint communicationEndPointMock = mock(CommunicationEndPoint.class);

    private final Properties properties = new Properties();
    private final ApplicationStates applicationStates = new ApplicationStates(getClass().getSimpleName());
    private final SessionStates sessionStates = new SessionStates("localhost",
            47,
            SessionType.CLIENT_SESSION);

    private ApplicationControlRunnable runnable;

    @Before
    public void setup() {
        reset(serverSocketMock);
        reset(socketMock);
        reset(inetAddressMock);
        reset(serviceSessionRunnerMock);
        reset(communicationEndPointMock);
        properties.setProperty(PROPERTY_INTERNAL_SERVER_PORT, String.valueOf(1));
        applicationStates.setServerSocket(serverSocketMock);
        sessionStates.setCommunicationEndPoint(communicationEndPointMock);
        sessionStates.setServiceSessionRunner(serviceSessionRunnerMock);
        runnable = new ApplicationControlRunnable("runnerName",
                properties, applicationStates);
    }

    @Test
    public void processApplicationNonStoppingBecauseStateAndActionNotCorrect() throws IOException {
        // act
        runnable.processApplicationStopping();
        // assert
        assertNotEquals("invalid action found",
                STOP_DONE,
                applicationStates.getApplicationAction());
        assertNotEquals("invalid state found",
                STOPPED,
                applicationStates.getApplicationState());
        verify(serverSocketMock, never()).close();
    }

    @Test
    public void processApplicationStoppingWithActiveSessions() throws IOException {
        // arrange
        when(communicationEndPointMock.getSocket()).thenReturn(socketMock);
        when(socketMock.getPort()).thenReturn(47);
        when(socketMock.getInetAddress()).thenReturn(inetAddressMock);
        when(inetAddressMock.getHostName()).thenReturn("localhost");
        applicationStates.setApplicationAction(STOP);
        applicationStates.setApplicationState(ApplicationState.STOPPING);
        applicationStates.getServerSessionStates().add(sessionStates);
        sessionStates.setSessionState(DEFINED);
        // act
        runnable.processApplicationStopping();
        // assert
        assertEquals("invalid action found",
                STOP_DONE,
                applicationStates.getApplicationAction());
        assertEquals("invalid state found",
                STOPPED,
                applicationStates.getApplicationState());
        assertEquals("invalid state found",
                STOPPING,
                sessionStates.getSessionState());
        verify(serverSocketMock).close();
        verify(socketMock).getPort();
    }

    @Test
    public void processApplicationStoppingWithInactiveSessions() throws IOException {
        // arrange
        applicationStates.setApplicationAction(STOP);
        applicationStates.setApplicationState(ApplicationState.STOPPING);
        applicationStates.getServerSessionStates().add(sessionStates);
        // act
        runnable.processApplicationStopping();
        // assert
        assertEquals("invalid action found",
                STOP_DONE,
                applicationStates.getApplicationAction());
        assertEquals("invalid state found",
                STOPPED,
                applicationStates.getApplicationState());
        assertNotEquals("invalid state found",
                STOPPING,
                sessionStates.getSessionState());
        verify(serverSocketMock).close();
        verify(socketMock, never()).getPort();
    }

    @Test
    public void processApplicationStoppingWithoutSessions() throws IOException {
        // arrange
        applicationStates.setApplicationAction(STOP);
        applicationStates.setApplicationState(ApplicationState.STOPPING);
        // act
        runnable.processApplicationStopping();
        // assert
        assertEquals("invalid action found",
                STOP_DONE,
                applicationStates.getApplicationAction());
        assertEquals("invalid state found",
                STOPPED,
                applicationStates.getApplicationState());
        verify(serverSocketMock).close();
    }

    @Test
    public void processStoppingSessionsWithRunningSessions() throws IOException {
        // arrange
        applicationStates.getServerSessionStates().add(sessionStates);
        // act
        runnable.processStoppingSessions();
        // assert
        assertEquals("invalid size in list",
                1,
                applicationStates.getServerSessionStates().size());
        verify(serviceSessionRunnerMock, never()).isInterrupted();
        verify(serviceSessionRunnerMock, never()).interrupt();
    }

    @Test
    public void processStoppingSessionsWithStoppingSessionsAndInterruptedRunner() throws IOException {
        // arrange
        when(communicationEndPointMock.getSocket()).thenReturn(socketMock);
        when(socketMock.getInetAddress()).thenReturn(inetAddressMock);
        when(inetAddressMock.getHostName()).thenReturn("localhost");
        when(serviceSessionRunnerMock.isInterrupted()).thenReturn(true);
        applicationStates.getServerSessionStates().add(sessionStates);
        sessionStates.setSessionState(STOPPING);
        // act
        runnable.processStoppingSessions();
        // assert
        assertTrue("list is not empty",
                applicationStates.getServerSessionStates().isEmpty());
        verify(serviceSessionRunnerMock).isInterrupted();
        verify(serviceSessionRunnerMock, never()).interrupt();
    }

    @Test
    public void processStoppingSessionsWithStoppingSessionsAndRunningRunner() throws IOException {
        // arrange
        when(communicationEndPointMock.getSocket()).thenReturn(socketMock);
        when(socketMock.getInetAddress()).thenReturn(inetAddressMock);
        when(inetAddressMock.getHostName()).thenReturn("localhost");
        when(serviceSessionRunnerMock.isInterrupted()).thenReturn(false);
        applicationStates.getServerSessionStates().add(sessionStates);
        sessionStates.setSessionState(STOPPING);
        // act
        runnable.processStoppingSessions();
        // assert
        assertTrue("list is not empty",
                applicationStates.getServerSessionStates().isEmpty());
        verify(serviceSessionRunnerMock).isInterrupted();
        verify(serviceSessionRunnerMock).interrupt();
    }


}