package com.roadrantz.marketing;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.roadrantz.domain.Motorist;

/**
 * A message converter to convert MapMessages to/from Motorist objects.
 * 
 * As shown in listing 10.5.
 * 
 * @author wallsc
 */
public class MotoristMessageConverter implements MessageConverter {
   public MotoristMessageConverter() {}

   public Object fromMessage(Message message) throws JMSException,
                     MessageConversionException {

      if (!(message instanceof MapMessage)) {
         throw new MessageConversionException("Message isn't a MapMessage");
      }

      MapMessage mapMessage = (MapMessage) message;
      SpammedMotorist motorist = new SpammedMotorist();

      motorist.setFirstName(mapMessage.getString("firstName"));
      motorist.setLastName(mapMessage.getString("lastName"));
      motorist.setEmail(mapMessage.getString("email"));

      return motorist;
   }

   public Message toMessage(Object object, Session session)
                     throws JMSException, MessageConversionException {

      if (!(object instanceof Motorist)) {
         throw new MessageConversionException("Object isn't a Motorist");
      }

      Motorist motorist = (Motorist) object;
      MapMessage message = session.createMapMessage();
      message.setString("firstName", motorist.getFirstName());
      message.setString("lastName", motorist.getLastName());
      message.setString("email", motorist.getEmail());

      return message;
   }
}
