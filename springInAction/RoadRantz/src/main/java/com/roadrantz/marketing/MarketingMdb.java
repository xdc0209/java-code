package com.roadrantz.marketing;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MarketingMdb 
    implements MessageDrivenBean, MessageListener {

  private transient MessageDrivenContext ctx;
  
  public void setMessageDrivenContext(MessageDrivenContext ctx) 
      throws EJBException {
    this.ctx = ctx;
  }

  public void ejbRemove() throws EJBException {
  }

  public void onMessage(Message message) {
    MapMessage mapMessage = (MapMessage) message;
    
    try {
      SpammedMotorist driver = new SpammedMotorist();
      driver.setFirstName(mapMessage.getString("firstName"));
      driver.setLastName(mapMessage.getString("lastName"));
      driver.setEmail(mapMessage.getString("email"));
      
      processDriverInfo(driver);
    } catch (JMSException e) {
      // handle--somehow
    }
  }
  
  private void processDriverInfo(SpammedMotorist driver) {
    // ...
  }
}
