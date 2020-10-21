//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.20 um 06:28:33 PM CEST 
//


package xmlObjects.SessionStatesData;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xmlObjects.SessionStatesData package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ApplicationName_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "applicationName");
    private final static QName _HostName_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "hostName");
    private final static QName _PortNumber_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "portNumber");
    private final static QName _SessionType_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "sessionType");
    private final static QName _SessionState_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "sessionState");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xmlObjects.SessionStatesData
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceSessionRunnerData }
     * 
     */
    public ServiceSessionRunnerData createServiceSessionRunnerData() {
        return new ServiceSessionRunnerData();
    }

    /**
     * Create an instance of {@link CommunicationEndPoint }
     * 
     */
    public CommunicationEndPoint createCommunicationEndPoint() {
        return new CommunicationEndPoint();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "applicationName")
    public JAXBElement<String> createApplicationName(String value) {
        return new JAXBElement<String>(_ApplicationName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "hostName")
    public JAXBElement<String> createHostName(String value) {
        return new JAXBElement<String>(_HostName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "portNumber")
    public JAXBElement<BigInteger> createPortNumber(BigInteger value) {
        return new JAXBElement<BigInteger>(_PortNumber_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "sessionType")
    public JAXBElement<String> createSessionType(String value) {
        return new JAXBElement<String>(_SessionType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "sessionState")
    public JAXBElement<String> createSessionState(String value) {
        return new JAXBElement<String>(_SessionState_QNAME, String.class, null, value);
    }

}
