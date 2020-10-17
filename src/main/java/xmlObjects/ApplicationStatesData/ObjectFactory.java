//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.08 um 03:54:06 PM CEST 
//


package xmlObjects.ApplicationStatesData;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xmlObjects.ApplicationStatesData package. 
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
    private final static QName _ApplicationState_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "applicationState");
    private final static QName _ApplicationAction_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "applicationAction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xmlObjects.ApplicationStatesData
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClientSessionStates }
     * 
     */
    public ClientSessionStates createClientSessionStates() {
        return new ClientSessionStates();
    }

    /**
     * Create an instance of {@link ServerSessionStates }
     * 
     */
    public ServerSessionStates createServerSessionStates() {
        return new ServerSessionStates();
    }

    /**
     * Create an instance of {@link ServerSocketData }
     * 
     */
    public ServerSocketData createServerSocketData() {
        return new ServerSocketData();
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
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "applicationState")
    public JAXBElement<String> createApplicationState(String value) {
        return new JAXBElement<String>(_ApplicationState_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "applicationAction")
    public JAXBElement<String> createApplicationAction(String value) {
        return new JAXBElement<String>(_ApplicationAction_QNAME, String.class, null, value);
    }

}
