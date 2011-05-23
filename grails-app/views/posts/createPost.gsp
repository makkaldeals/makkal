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
</head>


<body>

<div id="createPost">

    <h1>
        <g:if test="${post.id}">
            <g:message code="post.edit.title" default="Edit Post"></g:message>
        </g:if>
        <g:else>
            <g:message code="post.create.title" default="Create Post"></g:message>
        </g:else>
    </h1>
<!-- <g:renderErrors bean="${post}"></g:renderErrors> -->

<!-- <g:hasErrors bean="${post}">
    <jqvalui:renderErrors render="true" style="margin-bottom:10px">
        <g:renderErrors bean="${post}" as="list"/>
    </jqvalui:renderErrors>
</g:hasErrors> -->

    <g:form name="createPostForm" controller="posts" action="publishPost">
        <g:if test="${post.id}">
            <g:hiddenField name="id" value="${post.id}"/>
        </g:if>
        <p>
            <label for="post.title"><g:message code="post.title.label" default="Title:"/></label>
            <g:textField size="150" name="post.title" value="${post.title}"/>
            <g:if test="${hasErrors(bean: post, field: 'title', 'errors')}">
                <jqvalui:renderError for="post.title" style="margin-top: -5px">
                    <g:eachError bean="${post}" field="title"><g:message error="${it}"/></g:eachError>
                </jqvalui:renderError>
            </g:if>
        </p>

        <p>
            <label for="post.subject"><g:message code="post.subject.label" default="Subject:"/></label>
            <g:textField size="150" name="post.subject" value="${post.subject}"/>

        </p>

        <p>

            <label for="post.content"><g:message code="post.content.label" default="Content:"/></label> <br>

            <ckeditor:editor toolbar="custom" name="post.content" height="400px" width="80%"
                             filebrowserImageBrowseUrl="" filebrowserBrowseUrl="" filebrowserFlashBrowseUrl=""
                             filebrowserImageUploadUrl="${createLink(controller:'media' , action:'uploadImage')}"
                             filebrowserUploadUrl="${createLink(controller:'media' , action:'uploadImage')}">
                ${post.content}
            </ckeditor:editor>

        </p>

        <p>
            <label for="expiresOn"><g:message code="post.expiresOn.label" default="Expires On:"/></label>
            <g:jqDatePicker name="expiresOn"
                            value="${post?.expiresOn ? DateUtil.convertToJqueryFormat(post?.expiresOn) : DateUtil.getDefaultExpiresOn()}"
                            minDate="${DateUtil.convertDateToJqueryFormat(new Date())}"
                            maxDate="${ConfigurationHolder.config.grepdeals.posts.expiresOn.maxDuration}"/>
        </p>

        <p>
            <label for="tags"><g:message code="post.tags.label" default="Tags:"/></label>
            <g:textField size="50" name="tags" value="${post.tags.join(',')}"/>
        </p>

        <g:submitButton name="publishPost" form="createPostForm"
                        value="${message(code:'post.publish.button',default:'Publish')}"/>
    </g:form>

<!-- <jqvalui:renderValidationScript for="com.grepdeals.Post"
                                     form="createPostForm" errorClass="invalid" validClass="success"/> -->

</div>
</body>
</html>