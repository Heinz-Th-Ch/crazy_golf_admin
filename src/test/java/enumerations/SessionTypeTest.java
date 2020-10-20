package enumerations;

import abstracts.AbstractPlainJava;
import communications.enumerations.ServiceFunction;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link SessionType}.
 */
public class SessionTypeTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 2;

    @Test
    public void testNumberOfValues(){
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                SessionType.values().length);
    }

}