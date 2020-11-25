package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ApplicationState}.
 */
public class ApplicationStateTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 6;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ApplicationState.values().length);
    }

}