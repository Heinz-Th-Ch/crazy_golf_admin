package states;

import abstracts.AbstractPlainJava;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * JUnit test for {@link ApplicationStates}.
 */
public class ApplicationStatesTest extends AbstractPlainJava {

    private final ApplicationStates applicationStates = new ApplicationStates(getClass().getSimpleName());

    private final SessionStates sessionStates1 = Mockito.mock(SessionStates.class);
    private final SessionStates sessionStates2 = Mockito.mock(SessionStates.class);
    private final SessionStates sessionStates3 = Mockito.mock(SessionStates.class);
    private final SessionStates sessionStates4 = Mockito.mock(SessionStates.class);
    private final SessionStates sessionStates5 = Mockito.mock(SessionStates.class);

    @Before
    public void setup() {
        applicationStates.addClientSessionStates(sessionStates1);
        applicationStates.addClientSessionStates(sessionStates3);
        applicationStates.addClientSessionStates(sessionStates5);
        applicationStates.addServerSessionStates(sessionStates2);
        applicationStates.addServerSessionStates(sessionStates3);
        applicationStates.addServerSessionStates(sessionStates4);
    }

    @Test
    public void addClientSessionStates() {
        // act
        applicationStates.addClientSessionStates(sessionStates4);
        applicationStates.addClientSessionStates(sessionStates4);
        // assert
        assertEquals("invalid number of entries",
                4,
                applicationStates.getClientSessionStates().size());
    }

    @Test
    public void removeClientSessionStates() {
        // act
        applicationStates.removeClientSessionStates(sessionStates1);
        // assert
        assertEquals("invalid number of entries",
                2,
                applicationStates.getClientSessionStates().size());
        assertFalse("unexpected entry found",
                applicationStates.getClientSessionStates().contains(sessionStates1));
    }

    @Test
    public void addServerSessionStates() {
        // act
        applicationStates.addServerSessionStates(sessionStates1);
        applicationStates.addServerSessionStates(sessionStates5);
        applicationStates.addServerSessionStates(sessionStates5);
        // assert
        assertEquals("invalid number of entries",
                5,
                applicationStates.getServerSessionStates().size());
    }

    @Test
    public void removeServerSessionStates() {
        // act
        applicationStates.removeServerSessionStates(sessionStates1);
        applicationStates.removeServerSessionStates(sessionStates2);
        applicationStates.removeServerSessionStates(sessionStates3);
        applicationStates.removeServerSessionStates(sessionStates4);
        applicationStates.removeServerSessionStates(sessionStates5);
        // assert
        assertTrue("list is not empty",
                applicationStates.getServerSessionStates().isEmpty());
    }

}