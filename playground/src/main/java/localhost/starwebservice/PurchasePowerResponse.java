
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
 *         &lt;element name="PurchasePowerResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STOrderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "purchasePowerResult",
    "token",
    "stOrderNo"
})
@XmlRootElement(name = "PurchasePowerResponse")
public class PurchasePowerResponse {

    @XmlElement(name = "PurchasePowerResult")
    protected int purchasePowerResult;
    @XmlElement(name = "Token")
    protected String token;
    @XmlElement(name = "STOrderNo")
    protected String stOrderNo;

    /**
     * 获取purchasePowerResult属性的值。
     * 
     */
    public int getPurchasePowerResult() {
        return purchasePowerResult;
    }

    /**
     * 设置purchasePowerResult属性的值。
     * 
     */
    public void setPurchasePowerResult(int value) {
        this.purchasePowerResult = value;
    }

    /**
     * 获取token属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * 获取stOrderNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTOrderNo() {
        return stOrderNo;
    }

    /**
     * 设置stOrderNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTOrderNo(String value) {
        this.stOrderNo = value;
    }

}
