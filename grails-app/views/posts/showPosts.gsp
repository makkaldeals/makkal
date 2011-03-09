<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Mar 8, 2011
  Time: 1:21:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name='layout' content='register'/>
    <title>Makkal deals customer</title>
  </head>
  <body>
  <div >
    <sec:access expression="hasRole('ROLE_CUSTOMER')">
        <g:link controller="posts" action="createPost"><g:message code="post.create.link" default="Create Post"></g:message></g:link>
    </sec:access>
  </div>

  <g:each var="post" in="${posts}">
    <g:render template="/posts/showPostTempl" model="[post:post]"></g:render>
  </g:each>


  </body>
</html>