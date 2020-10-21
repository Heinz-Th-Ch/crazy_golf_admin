package states;

import abstracts.AbstractPlainJava;
import communications.CommunicationEndPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static enumerations.SessionType.CLIENT_SESSION;
import static org.mockito.Mockito.*;

/**
 * JUnit test for {@link SessionStates}.
 */
public class SessionStatesTest extends AbstractPlainJava {

    private final SessionStates sessionStates = new SessionStates("localhost",
            9999,
            CLIENT_SESSION);

    private final CommunicationEndPoint communicationEndPointMock = mock(CommunicationEndPoint.class);
    private final Socket socketMock = mock(Socket.class);

    @Before
    public void setUp() {
        when(communicationEndPointMock.getSocket()).thenReturn(socketMock);
        sessionStates.setCommunicationEndPoint(communicationEndPointMock);
    }

    @After
    public void tearDown() {
        reset(communicationEndPointMock);
        reset(socketMock);
    }

    @Test
    public void isSessionUsableAnswerIsYes() {
        when(socketMock.isClosed()).thenReturn(false);
        when(socketMock.isInputShutdown()).thenReturn(false);
        when(socketMock.isOutputShutdown()).thenReturn(false);
        assertTrue("session is unexpected not usable", sessionStates.isSessionUsable());
    }

    @Test
    public void isSessionUsableAnswerIsNoBecauseCommunicationEndPointIsNotCreated() {
        sessionStates.setCommunicationEndPoint(null);
        assertFalse("session is unexpected usable although communication end point not created", sessionStates.isSessionUsable());
    }

    @Test
    public void isSessionUsableAnswerIsNoBecauseSocketIsClosed() {
        when(socketMock.isClosed()).thenReturn(true);
        when(socketMock.isInputShutdown()).thenReturn(false);
        when(socketMock.isOutputShutdown()).thenReturn(false);
        assertFalse("session is unexpected not usable although socket is closed", sessionStates.isSessionUsable());
    }

    @Test
    public void isSessionUsableAnswerIsNoBecauseInputIsShutdown() {
        when(socketMock.isClosed()).thenReturn(false);
        when(socketMock.isInputShutdown()).thenReturn(true);
        when(socketMock.isOutputShutdown()).thenReturn(false);
        assertFalse("session is unexpected not usable although input is shutdown", sessionStates.isSessionUsable());
    }

    @Test
    public void isSessionUsableAnswerIsNoBecauseOutputIsShutdown() {
        when(socketMock.isClosed()).thenReturn(false);
        when(socketMock.isInputShutdown()).thenReturn(false);
        when(socketMock.isOutputShutdown()).thenReturn(true);
        assertFalse("session is unexpected not usable although output is shutdown", sessionStates.isSessionUsable());
    }

}