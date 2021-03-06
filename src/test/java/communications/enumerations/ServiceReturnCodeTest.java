package communications.enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ServiceReturnCode}.
 */
public class ServiceReturnCodeTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 2;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ServiceReturnCode.values().length);
    }

}