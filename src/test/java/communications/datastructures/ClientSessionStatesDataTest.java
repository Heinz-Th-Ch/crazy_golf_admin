package communications.datastructures;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link ClientSessionStatesData}.
 */
public class ClientSessionStatesDataTest extends AbstractPlainJava {

    private final String EXPECTED = "<clientSessionStatesData>\n" +
            "<listSize>47</listSize>\n" +
            "</clientSessionStatesData>\n";

    private ClientSessionStatesData testableData;

    @Test
    public void toXmlString() {
        testableData = new ClientSessionStatesData(47);
        assertEquals("wrong result received", EXPECTED, testableData.toXmlString());
    }

}