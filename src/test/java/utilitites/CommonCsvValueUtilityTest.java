package utilitites;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link CommonCsvValueUtility}.
 */
public class CommonCsvValueUtilityTest extends AbstractPlainJava {

    private final int NUMBER_OF_FIELDS = 3;

    @Test
    public void testNumberOfFields() {
        // act and assert
        assertEquals("invalid number of fields",
                NUMBER_OF_FIELDS,
                CommonCsvValueUtility.class.getDeclaredFields().length);
    }

}