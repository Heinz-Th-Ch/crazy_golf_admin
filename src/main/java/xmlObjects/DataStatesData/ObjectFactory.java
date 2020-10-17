//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.14 um 01:48:54 PM CEST 
//


package xmlObjects.DataStatesData;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xmlObjects.DataStatesData package. 
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
    private final static QName _DataAvailable_QNAME = new QName("http://tempuri.org/XMLSchema.xsd", "dataAvailable");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xmlObjects.DataStatesData
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BallCharacteristicsData }
     * 
     */
    public BallCharacteristicsData createBallCharacteristicsData() {
        return new BallCharacteristicsData();
    }

    /**
     * Create an instance of {@link SuitCaseCharacteristicsData }
     * 
     */
    public SuitCaseCharacteristicsData createSuitCaseCharacteristicsData() {
        return new SuitCaseCharacteristicsData();
    }

    /**
     * Create an instance of {@link CrazyGolfSiteCharacteristicsData }
     * 
     */
    public CrazyGolfSiteCharacteristicsData createCrazyGolfSiteCharacteristicsData() {
        return new CrazyGolfSiteCharacteristicsData();
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
    @XmlElementDecl(namespace = "http://tempuri.org/XMLSchema.xsd", name = "dataAvailable")
    public JAXBElement<String> createDataAvailable(String value) {
        return new JAXBElement<String>(_DataAvailable_QNAME, String.class, null, value);
    }

}
