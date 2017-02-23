<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head><title>Rantz For Vehicle</title></head>

  <body>
    <h2>Rantz for: ${vehicle.state} ${vehicle.plateNumber}</h2>
    
    <ul>
    <c:forEach items="${rants}" var="rant">
    <li><c:out value="${rant.vehicle.state}"/>/<c:out value="${rant.vehicle.plateNumber}"/> -- <c:out value="${rant.rantText}"/>
    </c:forEach>
    </ul>
  </body>
</html>
