package com.xdc.basic.api.restserver.jersey.domain.application.resource.shop;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Path("/")
public class DemoResource
{
    final String XMLNS_NAMESPACE = "http://yjmyzz.cnblogs.com/rest/service";
    final String ROOT_NODE       = "root";

    @GET
    @Path("/json/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public JAXBElement<String> getHelloWorldJSON()
    {
        JAXBElement<String> result = new JAXBElement<String>(new QName("", ROOT_NODE), String.class, sayHelloWorld());
        return result;
    }

    @GET
    @Path("/xml/hello")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<String> getHelloWorldXML()
    {
        JAXBElement<String> result = new JAXBElement<String>(new QName(XMLNS_NAMESPACE, ROOT_NODE), String.class,
                sayHelloWorld());
        return result;
    }

    @GET
    @Path("/json/hi/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public JAXBElement<String> getHelloToNameJSON(@PathParam("name") String name)
    {
        JAXBElement<String> result = new JAXBElement<String>(new QName("", ROOT_NODE), String.class,
                sayHelloToName(name));
        return result;
    }

    @GET
    @Path("/xml/hi/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<String> getHelloToNameXML(@PathParam("name") String name)
    {
        JAXBElement<String> result = new JAXBElement<String>(new QName(XMLNS_NAMESPACE, ROOT_NODE), String.class,
                sayHelloToName(name));
        return result;
    }

    @GET
    @Path("/xml/user")
    @Produces(MediaType.APPLICATION_XML)
    public User getUserInXML()
    {
        return getUser("unknown");
    }

    @GET
    @Path("/json/user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserInJSON()
    {
        return getUser("unknown");
    }

    @GET
    @Path("/xml/userByName/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public User getUserInXML(@PathParam("name") String username)
    {
        return getUser(username);
    }

    @GET
    @Path("/json/userByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserInJSON(@PathParam("name") String username)
    {
        return getUser(username);
    }

    @GET
    @Path("/xml/userByName2/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<User> getUserInXML2(@PathParam("name") String username)
    {
        JAXBElement<User> result = new JAXBElement<User>(new QName(XMLNS_NAMESPACE, ROOT_NODE), User.class,
                getUser(username));
        return result;
    }

    @GET
    @Path("/json/userByName2/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public JAXBElement<User> getUserInJSON2(@PathParam("name") String username)
    {
        JAXBElement<User> result = new JAXBElement<User>(new QName("", ROOT_NODE), User.class, getUser(username));
        return result;
    }

    @GET
    @Path("/xml/product/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public Product getProductXML(@PathParam("name") Product product)
    {
        return getProductFromServer(product);
    }

    @GET
    @Path("/xml/product2/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Product> getProductXML2(@PathParam("name") Product product)
    {
        JAXBElement<Product> result = new JAXBElement<Product>(new QName(XMLNS_NAMESPACE, ROOT_NODE), Product.class,
                getProductFromServer(product));
        return result;
    }

    @GET
    @Path("/xml/book/{ISBN}")
    @Produces(MediaType.APPLICATION_XML)
    public Book getBookXML(@PathParam("ISBN") Book book)
    {
        return getBookFromServer(book);
    }

    @GET
    @Path("/json/book/{ISBN}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookJSON(@PathParam("ISBN") Book book)
    {
        return getBookFromServer(book);
    }

    @POST
    @Path("/json/add-book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Book addBookJSON(Book book)
    {
        return getBookFromServer(book);
    }

    @POST
    @Path("/xml/add-book")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Book addBookXML(Book book)
    {
        return getBookFromServer(book);
    }

    private User getUser(String username)
    {
        User user = new User();
        user.setUsername(username);
        String pwd = new Long(Math.round(Math.random() * 100000)).toString();
        user.setPassword(pwd);
        int pin = (int) (Math.random() * 1000);
        user.setPin(pin);
        return user;
    }

    private Product getProductFromServer(Product p)
    {
        p.setProductName(p.getProductName() + " from server!");
        return p;
    }

    private Book getBookFromServer(Book book)
    {
        book.setProductName(book.getProductName() + " from server!");
        return book;
    }

    private String sayHelloWorld()
    {
        return "Hello JAX-RS!";
    }

    private String sayHelloToName(String name)
    {
        return "Hello " + name + ", welcome to the world of JAX-RS!";
    }

}
