<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head><title>Login</title></head>
  <body>
  
    <table border="1"><tr><td>
    <c:if test="${not empty param.login_error}">
      <font color="red">Login failed...try again</font><br><br>
    </c:if>
  
    <form method="POST" action="<c:url value='j_acegi_security_check'/>">
      <b>Username:  </b><input type="text" name="j_username"><br>
      <b>Password:  </b><input type="password" name="j_password"><br>
      <input id="remember_me" type="checkbox" name="_acegi_security_remember_me"><b>Remember Me</b><br>
      <input type="submit" value="Login">
    </form>
    </td></tr></table>
  </body>
</html>
