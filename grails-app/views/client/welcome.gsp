<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Feb 28, 2011
  Time: 5:08:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name='layout' content='register'/>
    <title>Makkal deals client</title>
  </head>
  <body>

  Welcome ${session.user.email} ! , Area code : ${session.user.areaCode}  , Role : ${session.user.getAuthorities()}


  </body>
</html>