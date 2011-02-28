<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Feb 28, 2011
  Time: 5:08:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Makkal deals client</title></head>
  <body>

  Welcome ${user.email} ! , Area code : ${user.areaCode}  , Role : ${user.getAuthorities()}


  </body>
</html>