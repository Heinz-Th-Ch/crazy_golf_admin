//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.20 um 06:28:33 PM CEST 
//


package xmlObjects.SessionStatesData;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für communicationEndPoint complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="communicationEndPoint"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ownPortNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="foreignPortNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="foreignHost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numberReceived" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="numberSend" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "communicationEndPoint", propOrder = {
    "ownPortNumber",
    "foreignPortNumber",
    "foreignHost",
    "numberReceived",
    "numberSend"
})
public class CommunicationEndPoint {

    @XmlElement(required = true)
    protected BigInteger ownPortNumber;
    @XmlElement(required = true)
    protected BigInteger foreignPortNumber;
    @XmlElement(required = true)
    protected String foreignHost;
    @XmlElement(required = true)
    protected BigInteger numberReceived;
    @XmlElement(required = true)
    protected BigInteger numberSend;

    /**
     * Ruft den Wert der ownPortNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnPortNumber() {
        return ownPortNumber;
    }

    /**
     * Legt den Wert der ownPortNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnPortNumber(BigInteger value) {
        this.ownPortNumber = value;
    }

    /**
     * Ruft den Wert der foreignPortNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getForeignPortNumber() {
        return foreignPortNumber;
    }

    /**
     * Legt den Wert der foreignPortNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setForeignPortNumber(BigInteger value) {
        this.foreignPortNumber = value;
    }

    /**
     * Ruft den Wert der foreignHost-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignHost() {
        return foreignHost;
    }

    /**
     * Legt den Wert der foreignHost-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignHost(String value) {
        this.foreignHost = value;
    }

    /**
     * Ruft den Wert der numberReceived-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberReceived() {
        return numberReceived;
    }

    /**
     * Legt den Wert der numberReceived-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberReceived(BigInteger value) {
        this.numberReceived = value;
    }

    /**
     * Ruft den Wert der numberSend-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberSend() {
        return numberSend;
    }

    /**
     * Legt den Wert der numberSend-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberSend(BigInteger value) {
        this.numberSend = value;
    }

}
