package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link SessionState}.
 */
public class SessionStateTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 5;

    @Test
    public void testNumberOfValues() {
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                SessionState.values().length);
    }

}