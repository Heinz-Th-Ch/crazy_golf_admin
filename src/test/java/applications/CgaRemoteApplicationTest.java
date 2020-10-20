package applications;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link CgaRemoteApplication}.
 */
public class CgaRemoteApplicationTest extends AbstractPlainJava {

    private final int NUMBER_OF_ARGUMENTS = 1;

    @Test
    public void checkArgumentsWithCorrectSize() {
        CgaRemoteApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        CgaRemoteApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        CgaRemoteApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

}