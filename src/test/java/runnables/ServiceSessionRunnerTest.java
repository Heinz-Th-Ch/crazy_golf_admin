package runnables;

import abstracts.AbstractPlainJava;
import communications.CommunicationEndPoint;
import communications.datastructures.ServiceRequest;
import communications.datastructures.ServiceResponse;
import communications.enumerations.ServiceFunction;
import communications.enumerations.ServiceReturnCode;
import dataStructures.DataListContainerImpl;
import enumerations.ApplicationAction;
import enumerations.ApplicationState;
import enumerations.SessionState;
import enumerations.SessionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import states.ApplicationStates;
import states.SessionStates;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

import static dataStructures.CommonValues.PROPERTY_INTERNAL_SERVER_PORT;
import static org.mockito.Mockito.*;

/**
 * JUnit test for {@link ServiceSessionRunner}.
 */
public class ServiceSessionRunnerTest extends AbstractPlainJava {

    private final static String RUNNER_NAME = "runnerName";
    private final static DataListContainerImpl DATA_LIST_CONTAINER = new DataListContainerImpl();

    private final CommunicationEndPoint communicationEndPointMock = mock(CommunicationEndPoint.class);
    private final  Socket socketMock = mock(Socket.class);
    private final InetAddress inetAddressMock = mock(InetAddress.class);

    private final  Properties properties = new Properties();
    private final ApplicationStates applicationStates = new ApplicationStates(ServiceSessionRunnerTest.class.getSimpleName());
    private final SessionStates sessionStates = new SessionStates("localhost", 55555, SessionType.CLIENT_SESSION);

    private ServiceSessionRunner runnable;


    @Before
    public void setUp() {
        when(communicationEndPointMock.getSocket()).thenReturn(socketMock);
        when(socketMock.getPort()).thenReturn(1234);
        when(socketMock.getInetAddress()).thenReturn(inetAddressMock);
        when(inetAddressMock.getHostName()).thenReturn("this.host");
        properties.setProperty(PROPERTY_INTERNAL_SERVER_PORT, String.valueOf(9876));
        applicationStates.setApplicationState(ApplicationState.ACTIVE);
        applicationStates.setApplicationAction(ApplicationAction.NONE);
        sessionStates.setCommunicationEndPoint(communicationEndPointMock);
        sessionStates.setSessionState(SessionState.DEFINED);
        runnable = new ServiceSessionRunner(RUNNER_NAME,
                properties,
                applicationStates,
                sessionStates,
                DATA_LIST_CONTAINER);
    }

    @After
    public void tearDown() {
        reset(communicationEndPointMock);
        reset(socketMock);
        reset(inetAddressMock);
    }

    @Test
    public void processShowStatusAll() throws IOException {
        runnable.processShowStatusAll(new ServiceRequest(ServiceFunction.SHOW_STATUS_ALL));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
    }

    @Test
    public void processShowStatusApplication() throws IOException {
        runnable.processShowStatusApplication(new ServiceRequest(ServiceFunction.SHOW_STATUS_APPLICATION));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
    }

    @Test
    public void processShowStatusData() throws IOException {
        runnable.processShowStatusData(new ServiceRequest(ServiceFunction.SHOW_STATUS_DATA));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
    }

    @Test
    public void processShowStatusSession() throws IOException {
        runnable.processShowStatusSession(new ServiceRequest(ServiceFunction.SHOW_STATUS_SESSION));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
    }

    @Test
    public void initiateApplicationStopping() throws IOException {
        runnable.initiateApplicationStopping(new ServiceRequest(ServiceFunction.STOP_APPLICATIONS));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
        verify(communicationEndPointMock).closeCommunication();
        assertEquals("unexpected session state received",
                SessionState.STOPPING,
                sessionStates.getSessionState());
        assertEquals("unexpected application action received",
                ApplicationAction.STOP,
                applicationStates.getApplicationAction());
        assertEquals("unexpected application state received",
                ApplicationState.STOPPING,
                applicationStates.getApplicationState());
    }

    @Test
    public void stopSession() throws IOException {
        runnable.stopSession(new ServiceRequest(ServiceFunction.RESTART_SERVICE_SESSIONS));
        verify(communicationEndPointMock).sendToPartner(any(ServiceResponse.class));
        verify(communicationEndPointMock).closeCommunication();
        assertEquals("unexpected session state received",
                SessionState.STOPPING,
                sessionStates.getSessionState());
    }
}