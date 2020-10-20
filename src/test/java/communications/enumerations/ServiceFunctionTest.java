package communications.enumerations;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link ServiceFunction}.
 */
public class ServiceFunctionTest extends AbstractPlainJava {

    private final int NUMBER_OF_ENTRIES = 6;

    @Test
    public void testNumberOfValues(){
        assertEquals("invalid number of values",
                NUMBER_OF_ENTRIES,
                ServiceFunction.values().length);
    }

}