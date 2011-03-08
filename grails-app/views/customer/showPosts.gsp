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
    <g:link controller="customer" action="createPost"><g:message code="post.create.link" default="Create Post"></g:message></g:link>
  </div>

  <g:each var="post" in="${posts}">

     <p><b> Title: ${post.title}</b></p>
     <p>${post.content}</p>
     <p>
       Posted by ${post.author.firstName} on
       <g:formatDate date="${post.dateCreated}" format="MMM dd, yyyy" /> <br>
       Tags :
       <g:each status="i" var="tag" in="${post.tags}">
		<g:link controller="customer" action="tagged" id="${tag}">${tag}</g:link><g:if test="${i<post.tags.size()-1}">, </g:if>
	   </g:each>
     </p>
     <hr>
  </g:each>


  </body>
</html>