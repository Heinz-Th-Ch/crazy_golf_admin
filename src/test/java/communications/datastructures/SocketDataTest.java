package communications.datastructures;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import java.net.Socket;

/**
 * JUnit test for {@link SocketData}.
 */
public class SocketDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_SOCKET = "<socketData>\n" +
            "<ownPortNumber>-1</ownPortNumber>\n" +
            "<foreignPortNumber>0</foreignPortNumber>\n" +
            "<foreignHost>n/a</foreignHost>\n" +
            "</socketData>\n";
    private final String EXPECTED_WITHOUT_SOCKET = "<socketData>\n" +
            "<ownPortNumber>0</ownPortNumber>\n" +
            "<foreignPortNumber>0</foreignPortNumber>\n" +
            "<foreignHost>n/a</foreignHost>\n" +
            "</socketData>\n";

    private SocketData testableData;

    @Test
    public void toXmlStringWithDefinedSocket() {
        testableData = new SocketData(new Socket());
        assertEquals("wrong result received", EXPECTED_WITH_SOCKET, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithoutDefinedSocket() {
        testableData = new SocketData(null);
        assertEquals("wrong result received", EXPECTED_WITHOUT_SOCKET, testableData.toXmlString());
    }

}