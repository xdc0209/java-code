<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="rr" tagdir="/WEB-INF/tags" %>

<html>
  <head>
      <title>Add Rant</title>
      <style>
         .error {
             color: #ff0000;
             font-weight: bold;
         }
      </style>
  </head>
  
  <body>
    <h2>Enter a rant...</h2>
    <form:form method="POST" action="addRant.htm" commandName="rant">
      <b><spring:message code="field.state" /></b>
          <rr:stateSelection path="vehicle.state" />
          <form:errors path="vehicle.state"  cssClass="error"/><br>
      <b><spring:message code="field.plateNumber" /></b>
          <form:input path="vehicle.plateNumber" />
          <form:errors path="vehicle.plateNumber" cssClass="error"/><br>
      <b><spring:message code="field.rantText" /></b>
      <form:errors path="rantText" cssClass="error"/><br>
      <form:textarea path="rantText" rows="5" cols="50" />
      <input type="submit"/>
    </form:form>
  </body>
</html>
