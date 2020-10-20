package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ValidatorErrorFields}.
 */
public class ValidatorErrorFieldsTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 21;

    @Test
    public void testNumberOfValues() {
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ValidatorErrorFields.values().length);
    }

}