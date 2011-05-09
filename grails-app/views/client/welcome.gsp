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
    <title>Grepdeals client</title>
</head>
<body>

Welcome ${session.user.email} ! , Area code : ${session.user.areaCode}  , Role : ${session.user.getAuthorities()}

<g:each var="post" in="${posts}">
    <g:render template="/posts/showPostTempl" model="[post:post]"></g:render>
</g:each>

</body>
</html>