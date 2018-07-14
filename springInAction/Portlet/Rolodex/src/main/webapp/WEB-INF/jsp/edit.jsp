<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<h2>Rolodex settings</h2>

<form action="<portlet:actionURL/>" method="POST">
<b>Contacts per page:</b>
<select name="pageSize">

<c:forEach begin="1" end="20" varStatus="status">
  <c:if test="${status.index == command.pageSize}">
    <option value="${status.index}" selected>${status.index}
  </c:if>
  <c:if test="${status.index != command.pageSize}">
    <option value="${status.index}">${status.index}
  </c:if>
</c:forEach>
</select>
<center><input type="submit" value="Save"></center>
</form>
