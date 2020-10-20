package communications.enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link SessionFunction}.
 */
public class SessionFunctionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 1;

    @Test
    public void testNumberOfValues(){
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                SessionFunction.values().length);
    }

}