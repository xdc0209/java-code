<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<portlet:defineObjects/>


<style>
  tr.odd {
    background-color:#eeeeee;
  }
  
  th.sorted {
    background-color:#666666;
    color:#ffffff;
  }

  th.sortable {
    background-color:#666666;
    color:#ffffff;
  }
  
  th.sortable a {
    color:#ffffff;
  }

  th.sorted a {
    color:#ffcc66;
  }
</style>


<c:if test='${actionName eq "searchContacts"}'>
<a href="<portlet:renderURL>
           <portlet:param name="action" value="contacts"/>
           </portlet:renderURL>">Contact List</a>
</c:if>

<c:if test='${actionName eq "contactList"}'>
<portlet:renderURL var="searchUrl">
  <portlet:param name="action" value="searchContacts"/>
  <portlet:param name="contactId" value="${contact.id}"/>
</portlet:renderURL>

<form action="${searchUrl}" method="POST">
<b>Search:</b><input type="text" name="searchText">&nbsp;<input type="submit" name="gobutton" value="Go">
</form>
</c:if>

<hr>

<%
  if(request.getUserPrincipal() != null) {
%>

<portlet:renderURL var="addUrl">
  <portlet:param name="action" 
      value="editContact"/>
</portlet:renderURL>

<table width="100%">
  <tr><td align="right">
    <a href='<%= addUrl %>'>Add Contact</a>
  </td></tr>
</table>

<%
  }
%>

<display:table name="contacts" 
    pagesize="${pageSize}" export="false" 
    id="contact" style="width:100%" sort="list" 
    defaultsort="1">
  <display:column sortable="true" title="Name">
    <c:out value="${contact.lastName}"/>, 
    <c:out value="${contact.firstName}"/>
  </display:column>
  <display:column sortable="true" title="Phone">
    <c:out value="${contact.phone1}"/>
  </display:column>
  <display:column sortable="false" title="">
    <portlet:renderURL var="detailUrl">
      <portlet:param name="action" 
          value="contactDetail"/>
      <portlet:param name="contactId" 
          value="${contact.id}"/>
    </portlet:renderURL>
    <portlet:renderURL var="editUrl">
      <portlet:param name="action" 
          value="editContact"/>
      <portlet:param name="contactId" 
          value="${contact.id}"/>
    </portlet:renderURL>
    <portlet:actionURL var="deleteUrl">
      <portlet:param name="action" 
          value="deleteContact"/>
      <portlet:param name="contactId" 
          value="${contact.id}"/>
    </portlet:actionURL>
    <a href="${detailUrl}"><img src=
        "/Rolodex/images/view.gif" border="0" 
        title="View contact details"></a>
    <c:if test="${not empty contact.ownerName}">
      <a href="${editUrl}"><img src=
          "/Rolodex/images/edit.gif" border="0" 
          title="Edit contact"></a>
      <a href="${deleteUrl}"><img src=
          "/Rolodex/images/trash.gif" border="0" 
          title="Delete contact"></a>
    </c:if>
  </display:column>
</display:table>
