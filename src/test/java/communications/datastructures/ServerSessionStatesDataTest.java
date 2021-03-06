package communications.datastructures;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ServerSessionStatesData}.
 */
public class ServerSessionStatesDataTest extends AbstractPlainJava {

    private final String EXPECTED = "<serverSessionStatesData>\n" +
            "<listSize>113</listSize>\n" +
            "</serverSessionStatesData>\n";

    private ServerSessionStatesData testableData;

    @Test
    public void toXmlString() {
        // arrange
        testableData = new ServerSessionStatesData(113);
        // act and assert
        assertEquals("wrong result received", EXPECTED, testableData.toXmlString());
    }

}