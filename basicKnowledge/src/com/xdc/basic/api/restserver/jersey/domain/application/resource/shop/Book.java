package com.xdc.basic.api.restserver.jersey.domain.application.resource.shop;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book extends Product
{
    private String isbn;

    private String author;

    private Date   publishDate;

    public Book()
    {
        System.out.println("construct: Book() is called!");
    }

    public static Book valueOf(String isbn)
    {
        System.out.println("Book: valueOf(String isbn) is called!");

        Book book = new Book();
        book.setISBN(isbn);
        book.setProductName("Java编程思想（第4版）");

        book.setAuthor("[美]埃克尔");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, 6, 1);
        book.setPublishDate(calendar.getTime());
        return book;
    }

    @XmlElement
    public String getISBN()
    {
        return isbn;
    }

    public void setISBN(String isbn)
    {
        this.isbn = isbn;
    }

    @XmlElement
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    @XmlElement
    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }

}
