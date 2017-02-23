<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
  <head>
    <title>Motorist Confirmation</title>
  </head>
  
  <body>
    <h2>Motorist Confirmation Confirmation (Step 3 of 3)</h2>
    <form:form commandName="motorist" method="POST" action="register.htm">
      <input type="hidden" name="page" value="2" />
      Motorist: <c:out value="${motorist.firstName}" />&nbsp;<c:out value="${motorist.lastName}" /><br/>
      E-mail: <c:out value="${motorist.email}" /><br/>
      Vehicles:<br/>
      <ul>
        <c:forEach items="${motorist.vehicles}" var="vehicle">
          <li><c:out value="${vehicle.state}" /> - <c:out value="${vehicle.plateNumber}" /></li>
        </c:forEach>
      </ul>

      <p>Click "Finish" to add motorist.</p>
      <input type="submit" name="_target1" value="Back">&nbsp;<input type="submit" name="_finish" value="Finish">
    </form:form>
  </body>
</html>
