<%@ taglib prefix="portlet" 
    uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="spring" 
    uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   
    uri="http://www.springframework.org/tags/form" %>
<h2>Contact Edit</h2>

<portlet:actionURL var="actionUrl">
  <portlet:param name="action" value="editContact"/>
</portlet:actionURL>
<portlet:renderURL var="contactsUrl">
  <portlet:param name="action" value="contacts"/>
</portlet:renderURL>

<form:form method="POST" 
    action="${actionUrl}" commandName="command">
            
  <form:hidden path="id" />

  <table width="100%" border="0">
    <tr>
      <td align="right">First name:</td>
      <td>
        <form:input path="firstName" size="20" />
      </td>
    </tr>
    <tr>
      <td align="right">Last name:</td>
      <td>
        <form:input path="lastName" size="20" />
      </td>
    </tr>
    <tr>
      <td align="right">Primary phone #:</td>
      <td>
        <form:input path="phone1" size="15" />
      </td>
    </tr>
    <tr>
      <td align="right">Alternate phone #:</td>
      <td>
        <form:input path="phone2" size="15" />
      </td>
    </tr>
    <tr><td align="center" colspan="2">
      <input type="submit" value="Save">&nbsp;
      <input type="button" value="Cancel" 
          onclick="window.location.href=
              '${contactsUrl}';">
    </td></tr>
  </table>
</form:form>
