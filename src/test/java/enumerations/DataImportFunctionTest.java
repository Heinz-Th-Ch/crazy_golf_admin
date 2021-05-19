package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link DataImportFunction}.
 */
public class DataImportFunctionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 2;

    @Test
    public void values() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                DataImportFunction.values().length);
    }

}