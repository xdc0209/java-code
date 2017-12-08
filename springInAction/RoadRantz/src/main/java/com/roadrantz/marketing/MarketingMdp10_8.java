package com.roadrantz.marketing;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * MarketingMdp, as it appeared in listing 10.8.
 * 
 * @author wallsc
 */
public class MarketingMdp10_8 {
   public MarketingMdp10_8() {}

   public void processMotoristInfo(MapMessage message) {
      SpammedMotorist motorist = new SpammedMotorist();
      try {
         motorist.setFirstName(message.getString("firstName"));
         motorist.setLastName(message.getString("lastName"));
         motorist.setEmail(message.getString("email"));
      }
      catch (JMSException e) {
         // handle this...somehow
      }
   }
}
