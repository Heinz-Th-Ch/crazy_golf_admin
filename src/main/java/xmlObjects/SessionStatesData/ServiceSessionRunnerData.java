//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1-b171012.0423 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.10.08 um 03:16:40 PM CEST 
//


package xmlObjects.SessionStatesData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für serviceSessionRunnerData complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="serviceSessionRunnerData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="runnerId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="runnerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="runnerState" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceSessionRunnerData", propOrder = {
    "runnerId",
    "runnerName",
    "runnerState"
})
public class ServiceSessionRunnerData {

    protected long runnerId;
    @XmlElement(required = true)
    protected String runnerName;
    @XmlElement(required = true)
    protected String runnerState;

    /**
     * Ruft den Wert der runnerId-Eigenschaft ab.
     * 
     */
    public long getRunnerId() {
        return runnerId;
    }

    /**
     * Legt den Wert der runnerId-Eigenschaft fest.
     * 
     */
    public void setRunnerId(long value) {
        this.runnerId = value;
    }

    /**
     * Ruft den Wert der runnerName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunnerName() {
        return runnerName;
    }

    /**
     * Legt den Wert der runnerName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunnerName(String value) {
        this.runnerName = value;
    }

    /**
     * Ruft den Wert der runnerState-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunnerState() {
        return runnerState;
    }

    /**
     * Legt den Wert der runnerState-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunnerState(String value) {
        this.runnerState = value;
    }

}
