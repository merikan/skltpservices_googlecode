
package se.sll.engagemangsindex.integration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * 
 * 				Type which describes a service contract.
 * 			
 * 
 * <p>Java class for ServiceContractNamespaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceContractNamespaceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceContractNamespace" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceContractNamespaceType", namespace = "urn:riv:itintegration:registry:1", propOrder = {
    "serviceContractNamespace",
    "any"
})
public class ServiceContractNamespaceType {

    @XmlElement(name = "ServiceContractNamespace", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String serviceContractNamespace;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the serviceContractNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceContractNamespace() {
        return serviceContractNamespace;
    }

    /**
     * Sets the value of the serviceContractNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceContractNamespace(String value) {
        this.serviceContractNamespace = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
