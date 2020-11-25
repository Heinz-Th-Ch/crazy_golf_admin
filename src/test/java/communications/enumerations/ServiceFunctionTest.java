package communications.enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ServiceFunction}.
 */
public class ServiceFunctionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 6;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ServiceFunction.values().length);
    }

}