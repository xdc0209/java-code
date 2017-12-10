package com.springinaction.ws.client;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.transform.JDOMResult;
import org.jdom.transform.JDOMSource;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.springinaction.poker.Card;
import com.springinaction.poker.PokerHandType;

public class TemplateBasedPokerClient 
    implements PokerClient {

  public PokerHandType evaluateHand(Card[] cards) 
      throws IOException {
    
    // Construct message
    Element requestElement =
        new Element("EvaluateHandRequest");
    Namespace ns = Namespace.getNamespace(
        "http://www.springinaction.com/poker/schemas");
    requestElement.setNamespace(ns);
    Document doc = new Document(requestElement);
    
    for(int i=0; i<cards.length; i++) {
      Element cardElement = new Element("card");
      Element suitElement = new Element("suit");
      suitElement.setText(cards[i].getSuit().toString());
      Element faceElement = new Element("face");
      faceElement.setText(cards[i].getFace().toString());
      cardElement.addContent(suitElement);
      cardElement.addContent(faceElement);
      doc.getRootElement().addContent(cardElement);
    }
    
    // Send message
    JDOMSource requestSource = new JDOMSource(doc);
    JDOMResult result = new JDOMResult();
    webServiceTemplate.sendAndReceive(requestSource, result);
    
    // Parse result
    Document resultDocument = result.getDocument();
    Element responseElement = resultDocument.getRootElement();
    Element handNameElement = 
        responseElement.getChild("handName", ns);
    return PokerHandType.valueOf(handNameElement.getText());
  }
  
  // INJECTED
  private WebServiceTemplate webServiceTemplate;
  public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
    this.webServiceTemplate = webServiceTemplate;
  }
}
