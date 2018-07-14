<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>

<h2>${contact.firstName} ${contact.lastName}</h2>

<c:if test="${not empty contact.ownerName}">
<a href="<portlet:renderURL>
           <portlet:param name="action" value="editContact"/>
           <portlet:param name="contactId" value="${contact.id}"/>
           </portlet:renderURL>">Edit</a>&nbsp;|&nbsp;
</c:if>

<a href="<portlet:renderURL>
           <portlet:param name="action" value="contacts"/>
           </portlet:renderURL>">Contact List</a>

<br>
<br>

${contact.address1}<br>
${contact.address2}<br>
${contact.city}, ${contact.state} ${contact.zipCode}<br>

<b>Primary phone: </b>${contact.phone1}<br>
<b>Secondary phone: </b>${contact.phone2}<br>
<b>Fax: </b>${contact.fax}<br>
<b>E-mail: </b>${contact.email}<br>

<br>
