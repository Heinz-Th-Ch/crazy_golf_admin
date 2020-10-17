//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.08 um 03:54:06 PM CEST 
//


package xmlObjects.ApplicationStatesData;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für serverSocketData complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="serverSocketData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="localPortNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="localHost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="receiveBufferSize" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverSocketData", propOrder = {
    "localPortNumber",
    "localHost",
    "receiveBufferSize"
})
public class ServerSocketData {

    @XmlElement(required = true)
    protected BigInteger localPortNumber;
    @XmlElement(required = true)
    protected String localHost;
    @XmlElement(required = true)
    protected BigInteger receiveBufferSize;

    /**
     * Ruft den Wert der localPortNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLocalPortNumber() {
        return localPortNumber;
    }

    /**
     * Legt den Wert der localPortNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLocalPortNumber(BigInteger value) {
        this.localPortNumber = value;
    }

    /**
     * Ruft den Wert der localHost-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalHost() {
        return localHost;
    }

    /**
     * Legt den Wert der localHost-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalHost(String value) {
        this.localHost = value;
    }

    /**
     * Ruft den Wert der receiveBufferSize-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReceiveBufferSize() {
        return receiveBufferSize;
    }

    /**
     * Legt den Wert der receiveBufferSize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReceiveBufferSize(BigInteger value) {
        this.receiveBufferSize = value;
    }

}
