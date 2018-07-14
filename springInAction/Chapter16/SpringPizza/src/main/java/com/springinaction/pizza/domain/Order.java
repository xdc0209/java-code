package com.springinaction.pizza.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import com.springinaction.pizza.service.PricingEngine;

/**
 * Defines a pizza order for use in the Spring Pizza Spring WebFlow example.
 * 
 * Shown in listing 15.1
 * 
 * @author wallsc
 */
@SuppressWarnings("serial")
@Configurable("order")
public class Order implements Serializable
{
    private Customer    customer;
    private List<Pizza> pizzas;
    private Payment     payment;

    public Order()
    {
        pizzas = new ArrayList<Pizza>();
        customer = new Customer();
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public List<Pizza> getPizzas()
    {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas)
    {
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza)
    {
        pizzas.add(pizza);
    }

    public float getTotal()
    {
        return pricingEngine.calculateOrderTotal(this);
    }

    public Payment getPayment()
    {
        return payment;
    }

    public void setPayment(Payment payment)
    {
        this.payment = payment;
    }

    // injected
    private PricingEngine pricingEngine;

    public void setPricingEngine(PricingEngine pricingEngine)
    {
        this.pricingEngine = pricingEngine;
    }
}
