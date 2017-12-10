package com.tickettodrive.ws;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.springframework.remoting.jaxrpc.JaxRpcServicePostProcessor;

import com.tickettodrive.Citation;

/**
 * Maps an array of Citation objects to/from
 * {http://tickettodrive.com/ArrayOfCitation.
 * 
 * From Listing 8.2
 * 
 * @author wallsc
 */
public class AxisArrayOfCitationMappingServicePostProcessor implements
                  JaxRpcServicePostProcessor {
   public void postProcessJaxRpcService(Service service) {
      TypeMappingRegistry registry = service.getTypeMappingRegistry();
      TypeMapping mapping = registry.getDefaultTypeMapping();
      QName xmlType = new QName("http://tickettodrive.com", "ArrayOfCitation");
      mapping.register(Citation[].class, xmlType, new ArraySerializerFactory(
                        Citation[].class, xmlType),
                        new ArrayDeserializerFactory());
   }
}