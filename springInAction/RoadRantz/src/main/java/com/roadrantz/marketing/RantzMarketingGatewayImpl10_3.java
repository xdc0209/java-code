package com.roadrantz.marketing;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.roadrantz.domain.Motorist;

/**
 * The original implementation of RantzMarkingGatewayImpl from Listing 10.3.
 * This class evolves as chapter 10 progresses, so this class serves as a memory
 * of what it looked like in Listing 10.3.
 * 
 * @author wallsc
 */
public class RantzMarketingGatewayImpl10_3 implements RantzMarketingGateway
{
    public RantzMarketingGatewayImpl10_3()
    {
    }

    public void sendMotoristInfo(final Motorist motorist)
    {
        jmsTemplate.send(destination, new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException
            {
                MapMessage message = session.createMapMessage();
                message.setString("lastName", motorist.getLastName());
                message.setString("firstName", motorist.getFirstName());
                message.setString("email", motorist.getEmail());
                return message;
            }
        });
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    private Destination destination;

    public void setDestination(Destination destination)
    {
        this.destination = destination;
    }
}
