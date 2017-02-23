package com.xdc.basic.api.json.bson4jackson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

/**
 * 摘自：https://www.michel-kraemer.com/binary-json-with-bson4jackson
 */
public class ObjectMapperSample
{
    public static void main(String[] args) throws Exception
    {
        // create dummy POJO
        Person bob = new Person();
        bob.setName("Bob");

        // serialize data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper(new BsonFactory());
        mapper.writeValue(baos, bob);

        // deserialize data
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Person clone_of_bob = mapper.readValue(bais, Person.class);

        assert bob.getName().equals(clone_of_bob.getName());
    }
}