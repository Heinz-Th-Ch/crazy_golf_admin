package communications.datastructures;

import abstracts.AbstractPlainJava;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * JUnit test for {@link ServerSocketData}.
 */
public class ServerSocketDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_SOCKET = "<serverSocketData>\n" +
            "<localPortNumber>-1</localPortNumber>\n" +
            "<localHost>n/a</localHost>\n" +
            "<receiveBufferSize>65536</receiveBufferSize>\n" +
            "</serverSocketData>\n";
    private final String EXPECTED_WITHOUT_SOCKET = "<serverSocketData>\n" +
            "<localPortNumber>0</localPortNumber>\n" +
            "<localHost>n/a</localHost>\n" +
            "<receiveBufferSize>0</receiveBufferSize>\n" +
            "</serverSocketData>\n";

    private ServerSocketData testableData;

    @Test
    public void toXmlStringWithDefinedServerSocket() throws IOException {
        // arrange
        testableData = new ServerSocketData(new ServerSocket());
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_SOCKET, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithoutDefinedServerSocket() throws SocketException {
        // arrange
        testableData = new ServerSocketData(null);
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITHOUT_SOCKET, testableData.toXmlString());
    }

}