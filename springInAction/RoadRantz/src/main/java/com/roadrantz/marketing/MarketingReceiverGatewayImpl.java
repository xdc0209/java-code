package com.roadrantz.marketing;

import org.springframework.jms.core.JmsTemplate;

public class MarketingReceiverGatewayImpl
{
    public MarketingReceiverGatewayImpl()
    {
    }

    public SpammedMotorist receivedSpammedDriver()
    {
        return (SpammedMotorist) jmsTemplate.receiveAndConvert();
    }

    // injected
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }
}
