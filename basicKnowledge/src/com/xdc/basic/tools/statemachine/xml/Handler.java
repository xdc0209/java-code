//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.08.09 at 09:28:45 PM CST
//

package com.xdc.basic.tools.statemachine.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}name"/>
 *         &lt;element ref="{}nxtstate"/>
 *         &lt;element ref="{}function"/>
 *         &lt;element ref="{}rettype"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "nxtstate", "function", "rettype" })
@XmlRootElement(name = "handler")
public class Handler
{
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String  name;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String  nxtstate;
    @XmlElement(required = true)
    protected String  function;
    @XmlElement(required = true)
    protected Rettype rettype;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *         possible object is
     *         {@link String }
     * 
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     * 
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the nxtstate property.
     * 
     * @return
     *         possible object is
     *         {@link String }
     * 
     */
    public String getNxtstate()
    {
        return nxtstate;
    }

    /**
     * Sets the value of the nxtstate property.
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     * 
     */
    public void setNxtstate(String value)
    {
        this.nxtstate = value;
    }

    /**
     * Gets the value of the function property.
     * 
     * @return
     *         possible object is
     *         {@link String }
     * 
     */
    public String getFunction()
    {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *            allowed object is
     *            {@link String }
     * 
     */
    public void setFunction(String value)
    {
        this.function = value;
    }

    /**
     * Gets the value of the rettype property.
     * 
     * @return
     *         possible object is
     *         {@link Rettype }
     * 
     */
    public Rettype getRettype()
    {
        return rettype;
    }

    /**
     * Sets the value of the rettype property.
     * 
     * @param value
     *            allowed object is
     *            {@link Rettype }
     * 
     */
    public void setRettype(Rettype value)
    {
        this.rettype = value;
    }
}