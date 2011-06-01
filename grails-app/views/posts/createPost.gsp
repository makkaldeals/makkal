<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Mar 8, 2011
  Time: 2:01:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.grepdeals.util.DateUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name='layout' content='register'/>
  <title>Grepdeals customer</title>
  
  <ckeditor:resources/>
  <g:javascript src="ckeditor/ckcustomizations.js"/>
  <jqui:resources themeCss="${resource(dir:'jquery-ui/themes/ui-lightness' , file:'jquery-ui-1.8.13.custom.css')}"/>
  <jqvalui:renderValidationScript for="com.grepdeals.Post"
          form="createPostForm" errorClass="invalid"/>
</head>


<body>

<div id="createPostContainer" class="container formbody">

  <div class="formtitle span-24 prepand-1 last">

    <g:if test="${post.id}">
      <g:message code="post.edit.title" default="Edit Post"></g:message>
    </g:if>
    <g:else>
      <g:message code="post.create.title" default="Create Post"></g:message>
    </g:else>
  </div>
  <g:renderErrors bean="${post}"></g:renderErrors>
  <g:form name="createPostForm" controller="posts" action="publishPost" class="inline" >
    <div class="column span-22 append-1 prepend-1 last ">


    <g:if test="${post.id}">
      <g:hiddenField name="id" value="${post.id}"/>
    </g:if>
     <div class="column span-3">
      <label for="title"><g:message code="post.title.label" default="Title:"/></label>
      </div>
      <div class="column span-19 last">
      <g:textField size="150" name="title" class = "title" value="${post.title}"/>
      <g:if test="${hasErrors(bean: post, field: 'title', 'errors')}">
        <jqvalui:renderError for="title" style="margin-top: -5px">
          <g:eachError bean="${post}" field="title"><g:message error="${it}"/></g:eachError>
        </jqvalui:renderError>
      </g:if>
      </div>
  

      <div class="column span-3">
      <label for="subject"><g:message code="post.subject.label" default="Subject:"/></label>
      </div>
      <div class="column span-19 last">
      <g:textField size="150" class="text" name="subject" value="${post.subject}"/>
      <g:if test="${hasErrors(bean: post, field: 'subject', 'errors')}">
        <jqvalui:renderError for="subject" style="margin-top: -5px">
          <g:eachError bean="${post}" field="title"><g:message error="${it}"/></g:eachError>
        </jqvalui:renderError>
      </g:if>
      </div>
     <div class="column span-3">
      <label for="content"><g:message code="post.content.label" default="Content:"/></label>
      </div>
      <div class="column span-19 last">
      <ckeditor:editor toolbar="custom" name="content" height="400px" width="100%"
              filebrowserImageBrowseUrl="" filebrowserBrowseUrl="" filebrowserFlashBrowseUrl=""
              filebrowserImageUploadUrl="${createLink(controller:'media' , action:'uploadImage')}"
              filebrowserUploadUrl="${createLink(controller:'media' , action:'uploadImage')}">
        ${post.content}
      </ckeditor:editor>

      <g:if test="${hasErrors(bean: post, field: 'content', 'errors')}">
        <jqvalui:renderError for="content" style="margin-top: -5px">
          <g:eachError bean="${post}" field="title"><g:message error="${it}"/></g:eachError>
        </jqvalui:renderError>
      </g:if>
      </div>

    <div class="column span-3">

      <label for="expiresOn"><g:message code="post.expiresOn.label" default="Expires On:"/></label>
      </div>
      <div class="column span-19 last">
      <g:jqDatePicker name="expiresOn"
              value="${post?.expiresOn ? DateUtil.convertToJqueryFormat(post?.expiresOn) : DateUtil.getDefaultExpiresOn()}"
              minDate="${DateUtil.convertDateToJqueryFormat(new Date())}"
              maxDate="${ConfigurationHolder.config.grepdeals.posts.expiresOn.maxDuration}"/>
      </div>

   <div class="column span-3">
      <label for="tags"><g:message code="post.tags.label" default="Tags:"/></label>
     </div>
      <div class="column span-19 last">
      <g:textField size="50" name="tags" class="text" value="${post.tags.join(',')}"/>
    </div>

    <div class="column span-19 prepend-3 last">
    <g:submitButton name="publishPost" class="s2ui_hidden_button" form="createPostForm"
            value="${message(code:'post.publish.button',default:'Publish')}"/>
    </div>

  </div>
  </g:form>

</div>
<br/><br/>
</body>
</html>