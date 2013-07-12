package com.roadrantz.marketing;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import com.roadrantz.domain.Motorist;

/**
 * RantzMarketingGatewayImpl as it appeared in listing 10.6.
 * 
 * @author wallsc
 */
public class RantzMarketingGatewayImpl10_6 extends JmsGatewaySupport implements
                  RantzMarketingGateway {
   public RantzMarketingGatewayImpl10_6() {}

   public void sendMotoristInfo(final Motorist motorist) {
      getJmsTemplate().send("rantz.marketing.queue", new MessageCreator() {
         public Message createMessage(Session session) throws JMSException {
            MapMessage message = session.createMapMessage();
            message.setString("lastName", motorist.getLastName());
            message.setString("firstName", motorist.getFirstName());
            message.setString("email", motorist.getEmail());
            return message;
         }
      });
   }
}
