package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link DataAccessoryComparisonValues}.
 */
public class DataAccessoryComparisonValuesTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 6;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                DataAccessoryComparisonValues.values().length);
    }

}