package com.roadrantz.marketing;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * MarketingMdp message-driven POJO, as it appeared in listing 10.7.
 * 
 * @author wallsc
 */
public class MarketingMdp10_7 implements MessageListener
{
    public void onMessage(Message message)
    {
        MapMessage mapMessage = (MapMessage) message;

        try
        {
            SpammedMotorist motorist = new SpammedMotorist();
            motorist.setFirstName(mapMessage.getString("firstName"));
            motorist.setLastName(mapMessage.getString("lastName"));
            motorist.setEmail(mapMessage.getString("email"));

            processMotoristInfo(motorist);
        }
        catch (JMSException e)
        {
            // handle--somehow
        }
    }

    private void processMotoristInfo(SpammedMotorist motorist)
    {
        // ...
    }
}
