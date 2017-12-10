package com.springinaction.pizza.flow;

import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import com.springinaction.pizza.domain.Customer;
import com.springinaction.pizza.domain.Order;
import com.springinaction.pizza.service.CustomerService;

/**
 * Action to lookup customer based on a given phone number.
 * 
 * As shown in Listing 15.3
 * 
 * @author wallsc
 */
public class LookupCustomerAction implements Action {
   public Event execute(RequestContext context) throws Exception {

      String phoneNumber = context.getRequestParameters().get("phoneNumber");

      Customer customer = customerService.lookupCustomer(phoneNumber);

      Order order = (Order) context.getFlowScope().get("order");
      order.setCustomer(customer);

      return new Event(this, "success");
   }

   // injected
   private CustomerService customerService;

   public void setCustomerService(CustomerService customerService) {
      this.customerService = customerService;
   }
}
