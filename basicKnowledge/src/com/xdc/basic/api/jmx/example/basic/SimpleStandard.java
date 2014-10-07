package com.xdc.basic.api.jmx.example.basic;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;

public class SimpleStandard extends NotificationBroadcasterSupport implements SimpleStandardMBean
{
    private String state     = "Initial State";
    private int    nbChanges = 0;
    private int    nbResets  = 0;

    /*
     * "SimpleStandard" does not provide any specific constructors. However, "SimpleStandard" is JMX compliant with
     * regards to contructors because the default contructor SimpleStandard() provided by the Java compiler is public.
     */

    /**
     * Getter: get the "State" attribute of the "SimpleStandard" standard MBean.
     * 
     * @return the current value of the "State" attribute.
     */
    @Override
    public String getState()
    {
        return state;
    }

    /**
     * Setter: set the "State" attribute of the "SimpleStandard" standard MBean.
     * 
     * @param <VAR>s</VAR> the new value of the "State" attribute.
     */
    @Override
    public void setState(String s)
    {
        state = s;
        nbChanges++;
    }

    /**
     * Getter: get the "NbChanges" attribute of the "SimpleStandard" standard MBean.
     * 
     * @return the current value of the "NbChanges" attribute.
     */
    @Override
    public int getNbChanges()
    {
        return nbChanges;
    }

    /**
     * Operation: reset to their initial values the "State" and "NbChanges" attributes of the "SimpleStandard" standard
     * MBean.
     */
    @Override
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
     * Return the "NbResets" property. This method is not a Getter in the JMX sense because it is not exposed in the
     * "SimpleStandardMBean" interface.
     * 
     * @return the current value of the "NbResets" property.
     */
    public int getNbResets()
    {
        return nbResets;
    }

    /**
     * Returns an array indicating, for each notification this MBean may send, the name of the Java class of the
     * notification and the notification type.</p>
     * 
     * @return the array of possible notifications.
     */
    @Override
    public MBeanNotificationInfo[] getNotificationInfo()
    {
        return new MBeanNotificationInfo[] { new MBeanNotificationInfo(
                new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
                AttributeChangeNotification.class.getName(),
                "This notification is emitted when the reset() method is called.") };
    }
}
