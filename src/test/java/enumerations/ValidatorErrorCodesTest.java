package enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ValidatorErrorCodes}.
 */
public class ValidatorErrorCodesTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 12;

    @Test
    public void testNumberOfValues() {
        // act and assert
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ValidatorErrorCodes.values().length);
    }

}