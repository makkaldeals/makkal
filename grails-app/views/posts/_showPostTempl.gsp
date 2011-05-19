<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Mar 8, 2011
  Time: 7:50:47 PM
  To change this template use File | Settings | File Templates.
--%>

<div>
  <p><b>Title:
  <g:link controller="posts" action="showPost" params="[id:post.id]">${post.title}</g:link>
  </b>
  </p>
  <p>${post.subject}</p>
  <p>${post.content}</p>
  <p>
    Posted by
      <g:link mapping="postsByAuthor" params="[author:post.author.email]">${post.author.firstName} ${post.author.lastName}</g:link>

    at

      <g:link mapping="postsByBusiness" params="[business:post.author.business.name]">${post.author.business.name}</g:link>

    on
    <g:formatDate date="${post.dateCreated}" format="MMM dd, yyyy hh:mm a"/>

    <!-- Show edit link only if he is author of post -->
    <g:if test="${post.author.id == session.user.id}">

      <g:link controller="posts" action="editPost" params="[id:post.id]">
        <g:message code="post.edit.link" default="Edit"></g:message>
      </g:link>
    </g:if>
    
    <br>
    Tags :
    <g:each status="i" var="tag" in="${post.tags}">
      <g:link mapping="postsByTag" params="[tag:tag]">${tag}</g:link><g:if test="${i<post.tags.size()-1}">,</g:if>
    </g:each>
    <br>
    Expires On : ${post.expiresOn} 
  </p>
  <hr>

</div>