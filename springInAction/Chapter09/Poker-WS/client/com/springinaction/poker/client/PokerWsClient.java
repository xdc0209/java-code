package com.springinaction.poker.client;

import java.io.IOException;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;

import org.springframework.core.io.Resource;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;

public class PokerWsClient {

  
  public void evaluatePokerHand()  throws IOException {

    Source requestSource = new ResourceSource(request);
    StringResult result = new StringResult();
    
    
    webServiceTemplate.sendAndReceive(requestSource,  
        new WebServiceMessageCallback() {
            public void doInMessage(WebServiceMessage message) 
                throws IOException {
              SaajSoapMessage saajSoapMessage =  
                  (SaajSoapMessage) message;  
              SOAPMessage saajMessage = 
                  saajSoapMessage.getSaajMessage();  
              saajSoapMessage.setSaajMessage(saajMessage);  
            }  
          }, result); 
    
    System.out.println("RESULT: " + result);
  }
  
  // INJECTED
  private Resource request;
  public void setRequest(Resource request) {
    this.request = request;
  }
  
  private WebServiceTemplate webServiceTemplate;
  public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
    this.webServiceTemplate = webServiceTemplate;
  }
  
  public static void main(String[] args) throws Exception {
    PokerWsClient client = new PokerWsClient();
    
    client.evaluatePokerHand();
  }
}
