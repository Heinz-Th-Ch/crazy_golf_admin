package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link DataManipulationType}.
 */
public class DataManipulationTypeTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 3;

    @Test
    public void values() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                DataManipulationType.values().length);
    }

}