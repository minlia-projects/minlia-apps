
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
 *         &lt;element name="GetActionResultResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActionResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getActionResultResult",
    "actionResult"
})
@XmlRootElement(name = "GetActionResultResponse")
public class GetActionResultResponse {

    @XmlElement(name = "GetActionResultResult")
    protected int getActionResultResult;
    @XmlElement(name = "ActionResult")
    protected String actionResult;

    /**
     * 获取getActionResultResult属性的值。
     * 
     */
    public int getGetActionResultResult() {
        return getActionResultResult;
    }

    /**
     * 设置getActionResultResult属性的值。
     * 
     */
    public void setGetActionResultResult(int value) {
        this.getActionResultResult = value;
    }

    /**
     * 获取actionResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionResult() {
        return actionResult;
    }

    /**
     * 设置actionResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionResult(String value) {
        this.actionResult = value;
    }

}
