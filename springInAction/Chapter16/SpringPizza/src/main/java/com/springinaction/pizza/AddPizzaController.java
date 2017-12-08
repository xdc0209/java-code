package com.springinaction.pizza;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.springinaction.pizza.domain.Pizza;

public class AddPizzaController extends SimpleFormController {
   public AddPizzaController() {}

   @Override
   protected ModelAndView onSubmit(Object command, BindException bindException)
                     throws Exception {

      Pizza pizza = (Pizza) command;

      addPizzaToOrder(pizza);

      return new ModelAndView("redirect:/showOrder.htm");
   }

   private void addPizzaToOrder(Pizza pizza) {
   // to be implemented (use your imagination)
   }
}
