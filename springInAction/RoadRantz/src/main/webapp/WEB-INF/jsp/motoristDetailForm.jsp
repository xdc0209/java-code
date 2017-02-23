<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <title>Motorist Detail</title>
  </head>
  
  <body>
    <h2>Motorist info (Step 1 of 3)</h2>
    
    <form:form commandName="motorist" method="POST" action="register.htm">
      <input type="hidden" name="page" value="0" /><br>      
      <spring:message code="field.email" /> <form:input path="email" /><br>
      <spring:message code="field.password" />: <form:input path="password" /><br>
      <spring:message code="field.firstName" /><form:input path="firstName" /><br>
      <spring:message code="field.lastName" /><form:input path="lastName" /><br>
      <input type="submit" name="_target1" value="Next">
    </form:form>
  </body>
</html>
