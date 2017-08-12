
package localhost.starwebservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetMeterInsufficientResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Insufficient" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getMeterInsufficientResult",
    "insufficient"
})
@XmlRootElement(name = "GetMeterInsufficientResponse")
public class GetMeterInsufficientResponse {

    @XmlElement(name = "GetMeterInsufficientResult")
    protected int getMeterInsufficientResult;
    @XmlElement(name = "Insufficient")
    protected String insufficient;

    /**
     * 获取getMeterInsufficientResult属性的值。
     * 
     */
    public int getGetMeterInsufficientResult() {
        return getMeterInsufficientResult;
    }

    /**
     * 设置getMeterInsufficientResult属性的值。
     * 
     */
    public void setGetMeterInsufficientResult(int value) {
        this.getMeterInsufficientResult = value;
    }

    /**
     * 获取insufficient属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsufficient() {
        return insufficient;
    }

    /**
     * 设置insufficient属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsufficient(String value) {
        this.insufficient = value;
    }

}
