package com.roadrantz.marketing;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;

/**
 * The original version of MarketingReceiverGatewayImpl, as shown in Listing
 * 10.4. This example evolves as chapter 10 progresses, so this class serves as
 * a memory of what MarketingReceiverGatewayImpl looked like in listing 10.4.
 * 
 * @author wallsc
 */
public class MarketingReceiverGatewayImpl10_4 {
   public MarketingReceiverGatewayImpl10_4() {}

   public SpammedMotorist receiveSpammedMotorist() {
      MapMessage message = (MapMessage) jmsTemplate.receive();
      SpammedMotorist motorist = new SpammedMotorist();
      try {
         motorist.setFirstName(message.getString("firstName"));
         motorist.setLastName(message.getString("lastName"));
         motorist.setEmail(message.getString("email"));
      }
      catch (JMSException e) {
         throw JmsUtils.convertJmsAccessException(e);
      }
      return motorist;
   }

   // injected
   private JmsTemplate jmsTemplate;

   public void setJmsTemplate(JmsTemplate jmsTemplate) {
      this.jmsTemplate = jmsTemplate;
   }
}