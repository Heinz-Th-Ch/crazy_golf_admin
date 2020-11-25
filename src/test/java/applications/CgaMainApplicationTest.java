package applications;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link CgaMainApplication}.
 */
public class CgaMainApplicationTest extends AbstractPlainJava {

    private final int NUMBER_OF_ARGUMENTS = 1;

    @Test
    public void checkArgumentsWithCorrectSize() {
        // act and assert
        CgaMainApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        // act and assert
        CgaMainApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        // act and assert
        CgaMainApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }
}