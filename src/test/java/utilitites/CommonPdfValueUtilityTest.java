package utilitites;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link CommonPdfValueUtility}.
 */
public class CommonPdfValueUtilityTest extends AbstractPlainJava {

    private final int NUMBER_OF_FIELDS = 15;

    @Test
    public void testNumberOfFields() {
        // act and assert
        assertEquals("invalid number of fields",
                NUMBER_OF_FIELDS,
                CommonPdfValueUtility.class.getDeclaredFields().length);
    }

}