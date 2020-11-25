package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ApplicationAction}.
 */
public class ApplicationActionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 3;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ApplicationAction.values().length);
    }

}