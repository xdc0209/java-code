package com.xdc.basic.api.jmx.example.basic;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

public class SimpleDynamic extends NotificationBroadcasterSupport implements DynamicMBean
{
    private String                        state          = "Initial State";
    private int                           nbChanges      = 0;
    private int                           nbResets       = 0;

    private final String                  dClassName     = this.getClass().getName();
    private final String                  dDescription   = "Simple implementation of a dynamic MBean.";

    private final MBeanAttributeInfo[]    dAttributes    = new MBeanAttributeInfo[2];
    private final MBeanConstructorInfo[]  dConstructors  = new MBeanConstructorInfo[1];
    private final MBeanNotificationInfo[] dNotifications = new MBeanNotificationInfo[1];
    private final MBeanOperationInfo[]    dOperations    = new MBeanOperationInfo[1];
    private MBeanInfo                     dMBeanInfo     = null;

    public SimpleDynamic()
    {
        // Build the management information to be exposed by the dynamic MBean
        buildDynamicMBeanInfo();
    }

    /**
     * Allows the value of the specified attribute of the Dynamic MBean to be obtained.
     */
    @Override
    public Object getAttribute(String attributeName)
            throws AttributeNotFoundException, MBeanException, ReflectionException
    {
        // Check attributeName is not null to avoid NullPointerException later on
        if (attributeName == null)
        {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null."),
                    "Cannot invoke a getter of " + dClassName + " with null attribute name.");
        }

        // Check for a recognized attribute_name and call the corresponding getter
        if (attributeName.equals("State"))
        {
            return getState();
        }

        if (attributeName.equals("NbChanges"))
        {
            return getNbChanges();
        }

        // If attributeName has not been recognized throw an AttributeNotFoundException
        throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + dClassName + ".");
    }

    /**
     * Sets the value of the specified attribute of the Dynamic MBean.
     */
    @Override
    public void setAttribute(Attribute attribute)
            throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
    {
        // Check attribute is not null to avoid NullPointerException later on
        if (attribute == null)
        {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null."),
                    "Cannot invoke a setter of " + dClassName + " with null attribute.");
        }

        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null)
        {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null."),
                    "Cannot invoke the setter of " + dClassName + " with null attribute name.");
        }

        // Check for a recognized attribute name and call the corresponding setter
        if (name.equals("State"))
        {
            // if null value, try and see if the setter returns any exception
            if (value == null)
            {
                try
                {
                    setState(null);
                }
                catch (Exception e)
                {
                    throw new InvalidAttributeValueException("Cannot set attribute " + name + " to null.");
                }
            }
            // if non null value, make sure it is assignable to the attribute
            else
            {
                try
                {
                    if (Class.forName("java.lang.String").isAssignableFrom(value.getClass()))
                    {
                        setState((String) value);
                    }
                    else
                    {
                        throw new InvalidAttributeValueException("Cannot set attribute " + name + " to a "
                                + value.getClass().getName() + " object, String expected.");
                    }
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        // recognize an attempt to set "NbChanges" attribute (read-only)
        else if (name.equals("NbChanges"))
        {
            throw new AttributeNotFoundException("Cannot set attribute " + name + " because it is read-only.");
        }
        // unrecognized attribute name
        else
        {
            throw new AttributeNotFoundException("Attribute " + name + " not found in " + dClassName + ".");
        }
    }

    /**
     * Enables the to get the values of several attributes of the Dynamic MBean.
     */
    @Override
    public AttributeList getAttributes(String[] attributeNames)
    {
        // Check attributeNames is not null to avoid NullPointerException later on
        if (attributeNames == null)
        {
            throw new RuntimeOperationsException(new IllegalArgumentException("AttributeNames cannot be null."),
                    "Cannot invoke a getter of " + dClassName + ".");
        }

        AttributeList resultList = new AttributeList();

        // If attributeNames is empty, return an empty result list
        if (attributeNames.length == 0)
        {
            return resultList;
        }

        // Build the result attribute list
        for (int i = 0; i < attributeNames.length; i++)
        {
            try
            {
                Object value = getAttribute(attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * Sets the values of several attributes of the Dynamic MBean, and returns the list of attributes that have been
     * set.
     */
    @Override
    public AttributeList setAttributes(AttributeList attributes)
    {
        // Check attributes is not null to avoid NullPointerException later on
        if (attributes == null)
        {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("AttributeList attributes cannot be null."),
                    "Cannot invoke a setter of " + dClassName + ".");
        }

        AttributeList resultList = new AttributeList();

        // If attributeNames is empty, nothing more to do
        if (attributes.isEmpty())
        {
            return resultList;
        }

        // For each attribute, try to set it and add to the result list if successful
        for (Iterator<Object> i = attributes.iterator(); i.hasNext();)
        {
            Attribute attr = (Attribute) i.next();
            try
            {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * Allows an operation to be invoked on the Dynamic MBean.
     */
    @Override
    public Object invoke(String operationName, Object params[], String signature[])
            throws MBeanException, ReflectionException
    {
        // Check operationName is not null to avoid NullPointerException later on
        if (operationName == null)
        {
            throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null."),
                    "Cannot invoke a null operation in " + dClassName + ".");
        }

        // Check for a recognized operation name and call the corresponding operation
        if (operationName.equals("reset"))
        {
            reset();
            return null;
        }
        else
        {
            // Unrecognized operation name
            throw new ReflectionException(new NoSuchMethodException(operationName),
                    "Cannot find the operation " + operationName + " in " + dClassName + ".");
        }
    }

    /**
     * This method provides the exposed attributes and operations of the Dynamic MBean. It provides this information
     * using an MBeanInfo object.
     */
    @Override
    public MBeanInfo getMBeanInfo()
    {
        // Return the information we want to expose for management: the dMBeanInfo private field has been built at instantiation time
        return dMBeanInfo;
    }

    /**
     * Getter: get the "State" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Setter: set the "State" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public void setState(String s)
    {
        state = s;
        nbChanges++;
    }

    /**
     * Getter: get the "NbChanges" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public Integer getNbChanges()
    {
        return new Integer(nbChanges);
    }

    /**
     * Return the "NbResets" property. This method is not a Getter in the JMX sense because it is not returned by the
     * getMBeanInfo() method.
     */
    public Integer getNbResets()
    {
        return new Integer(nbResets);
    }

    /**
     * Operation: reset to their initial values the "State" and "NbChanges" attributes of the "SimpleDynamic" dynamic
     * MBean.
     */
    public void reset()
    {
        AttributeChangeNotification acn = new AttributeChangeNotification(this, 0, 0, "NbChanges reset.", "NbChanges",
                "Integer", new Integer(nbChanges), new Integer(0));
        state = "Initial State";
        nbChanges = 0;
        nbResets++;
        sendNotification(acn);
    }

    /**
     * Build the private dMBeanInfo field, which represents the management interface exposed by the MBean, that is, the
     * set of attributes, constructors, operations and notifications which are available for management.
     * 
     * A reference to the dMBeanInfo object is returned by the getMBeanInfo() method of the DynamicMBean interface. Note
     * that, once constructed, an MBeanInfo object is immutable.
     */
    private void buildDynamicMBeanInfo()
    {
        dAttributes[0] = new MBeanAttributeInfo("State", "java.lang.String", "State string.", true, true, false);
        dAttributes[1] = new MBeanAttributeInfo("NbChanges", "java.lang.Integer",
                "Number of times the State string has been changed.", true, false, false);

        Constructor<?>[] constructors = this.getClass().getConstructors();
        dConstructors[0] = new MBeanConstructorInfo("Constructs a " + "SimpleDynamic object", constructors[0]);

        MBeanParameterInfo[] params = null;
        dOperations[0] = new MBeanOperationInfo("reset",
                "reset State and NbChanges " + "attributes to their initial values", params, "void",
                MBeanOperationInfo.ACTION);

        dNotifications[0] = new MBeanNotificationInfo(new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
                AttributeChangeNotification.class.getName(),
                "This notification is emitted when the reset() method is called.");

        dMBeanInfo = new MBeanInfo(dClassName, dDescription, dAttributes, dConstructors, dOperations, dNotifications);
    }
}
