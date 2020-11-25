package communications.datastructures;

import abstracts.AbstractPlainJava;
import communications.CommunicationEndPoint;
import org.junit.Test;

/**
 * JUnit test for {@link CommunicationEndPointData}.
 */
public class CommunicationEndPointDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_SOCKET = "<communicationEndPointData>\n" +
            "<ownPortNumber>-1</ownPortNumber>\n" +
            "<foreignPortNumber>0</foreignPortNumber>\n" +
            "<foreignHost>n/a</foreignHost>\n" +
            "<numberReceived>0</numberReceived>\n" +
            "<numberSend>0</numberSend>\n" +
            "</communicationEndPointData>\n";

    private CommunicationEndPointData testableData;

    @Test
    public void toXmlString() {
        // arrange
        testableData = new CommunicationEndPointData(new CommunicationEndPoint());
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_SOCKET, testableData.toXmlString());
    }

}