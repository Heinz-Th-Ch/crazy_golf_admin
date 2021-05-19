package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link DataManipulationFunction}.
 */
public class DataManipulationFunctionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 8;

    @Test
    public void values() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                DataManipulationFunction.values().length);
    }

}