package communications.enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link SessionReturnCode}.
 */
public class SessionReturnCodeTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 2;

    @Test
    public void testNumberOfValues(){
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                SessionReturnCode.values().length);
    }

}