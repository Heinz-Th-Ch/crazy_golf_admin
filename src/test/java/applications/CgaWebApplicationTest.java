package applications;

import abstracts.AbstractPlainJava;
import org.junit.Test;

public class CgaWebApplicationTest extends AbstractPlainJava {

    private final int NUMBER_OF_ARGUMENTS = 1;

    @Test
    public void checkArgumentsWithCorrectSize() {
        CgaWebApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        CgaWebApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        CgaWebApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

}