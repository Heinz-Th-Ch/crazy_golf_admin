package utilitites;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link CommonTableTitleValueUtility}.
 */
public class CommonTableTitleValueUtilityTest extends AbstractPlainJava {

    private final int NUMBER_OF_FIELDS = 26;

    @Test
    public void testNumberOfFields() {
        // act and assert
        assertEquals("invalid number of fields",
                NUMBER_OF_FIELDS,
                CommonTableTitleValueUtility.class.getDeclaredFields().length);
    }

}