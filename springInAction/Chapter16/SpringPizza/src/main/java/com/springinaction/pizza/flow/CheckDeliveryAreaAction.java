package com.springinaction.pizza.flow;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import com.springinaction.pizza.domain.Customer;
import com.springinaction.pizza.domain.Order;

public class CheckDeliveryAreaAction implements Action {
  public Event execute(RequestContext context) 
      throws Exception {
    
    Order order = 
        (Order) context.getFlowScope().get("order");
    Customer customer = order.getCustomer();
    
    customer.setInDeliveryArea(
        customer.getZipCode().startsWith("76"));
    
    return new Event(this, "success");
  }
}
