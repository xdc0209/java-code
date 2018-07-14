<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="rr" tagdir="/WEB-INF/tags" %>


<html>
  <head>
    <title>Motorist Vehicles</title>
  </head>

  <body>
    <h2>Motorist Vehicles vehicles (Step 2 of 3)</h2>

    <form:form commandName="motorist" method="POST" action="register.htm">
      <input type="hidden" name="page" value="1" /><br/>
      <input type="hidden" value="<c:out value="${nextVehicle}" />" /><br/>
      Motorist Vehicles: <c:out value="${motorist.firstName}" />&nbsp;<c:out value="${motorist.lastName}" /><br/>
      E-mail: <c:out value="${motorist.email}" /><br/>
      Vehicles: <br/>
      <ul>
      <c:forEach items="${motorist.vehicles}" var="vehicle">
      <li><c:out value="${vehicle.state}" /> - <c:out value="${vehicle.plateNumber}" /></li>
      </c:forEach>
      </ul>

      <hr />
      State: <rr:stateSelection path="vehicles[${nextVehicle}].state" /><br/>
      Plate: <form:input path="vehicles[${nextVehicle}].plateNumber" /><br/>
      <input type="submit" name="_target0" value="Back">&nbsp;
      <input type="submit" name="_target1" value="Add Vehicle">&nbsp;
      <input type="submit" name="_target2" value="Next">
    </form:form>
  </body>
</html>
