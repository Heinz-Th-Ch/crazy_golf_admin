package states;

import abstracts.AbstractPlainJava;
import org.junit.Ignore;
import org.junit.Test;

import static enumerations.SessionType.CLIENT_SESSION;

/**
 * JUnit test for {@link SessionStates}.
 */
public class SessionStatesTest extends AbstractPlainJava {

    private final SessionStates sessionStates = new SessionStates("localhost",
            9999,
            CLIENT_SESSION);

    @Test
    @Ignore("The corresponding function will hang in the third statement, therefore it will not be tested.")
    public void setSocket() {
    }

    @Test
    public void incrementNumberReceived() {
        int RECEIVE_LOOP = 57;
        for (int i = 0; i < RECEIVE_LOOP; i++) {
            sessionStates.incrementNumberReceived();
        }
        assertEquals("invalid result received",
                RECEIVE_LOOP,
                sessionStates.getNumberReceived().intValue());
    }

    @Test
    public void incrementNumberSend() {
        int SEND_LOOP = 213;
        for (int i = 0; i < SEND_LOOP; i++) {
            sessionStates.incrementNumberSend();
        }
        assertEquals("invalid result received",
                SEND_LOOP,
                sessionStates.getNumberSend().intValue());
    }
}