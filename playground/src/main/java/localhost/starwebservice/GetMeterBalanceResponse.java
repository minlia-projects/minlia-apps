
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
 *         &lt;element name="GetMeterBalanceResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MeterBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getMeterBalanceResult",
    "meterBalance"
})
@XmlRootElement(name = "GetMeterBalanceResponse")
public class GetMeterBalanceResponse {

    @XmlElement(name = "GetMeterBalanceResult")
    protected int getMeterBalanceResult;
    @XmlElement(name = "MeterBalance")
    protected String meterBalance;

    /**
     * 获取getMeterBalanceResult属性的值。
     * 
     */
    public int getGetMeterBalanceResult() {
        return getMeterBalanceResult;
    }

    /**
     * 设置getMeterBalanceResult属性的值。
     * 
     */
    public void setGetMeterBalanceResult(int value) {
        this.getMeterBalanceResult = value;
    }

    /**
     * 获取meterBalance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeterBalance() {
        return meterBalance;
    }

    /**
     * 设置meterBalance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeterBalance(String value) {
        this.meterBalance = value;
    }

}
