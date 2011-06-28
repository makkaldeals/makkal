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
    <meta name='layout' content='main'/>
    <title>Grepdeals customer</title>

    <ckeditor:resources/>
    <g:javascript src="ckeditor/ckcustomizations.js"/>
    <jqui:resources themeCss="${resource(dir:'jquery-ui/themes/ui-lightness' , file:'jquery-ui-1.8.13.custom.css')}"/>
    <jqvalui:renderValidationScript for="com.grepdeals.Post"
                                    form="createPostForm" errorClass="invalid"/>

    <script type="text/javascript">
        function facebookLogin() {
            FB.getLoginStatus(function(response) {
                if (response.session) {
                    // logged in and connected user, someone you know
                    window.location ="${createLink(controller:'posts', action:'createPost')}";
                }
            });
        }
    </script>

</head>


<body>

<div id="createPostContainer" class="formbody">

    <div class="formtitle span-24 last">

        <g:if test="${post.id}">
            <g:message code="post.edit.title" default="Edit Post"></g:message>
        </g:if>
        <g:else>
            <g:message code="post.create.title" default="Create Post"></g:message>
            <g:if test="${session.facebook.uid == null}">
                (<g:message code="facebook.login.defaultmessage"/>
                <fb:login-button perms="email,publish_stream" onlogin="facebookLogin();" size="large">
                    <g:message code="auth.login.facebook"/>
                </fb:login-button>
                )
            </g:if>
        </g:else>

    </div>
    <g:form name="createPostForm" controller="posts" action="publishPost" class="inline">
        <div class="column span-22 append-1 prepend-1 last ">

            <g:if test="${post.id}">
                <g:hiddenField name="id" value="${post.id}"/>
            </g:if>

            <gd:textFieldRow
                    name='title'
                    value="${post.title}"
                    labelCodeDefault="Title:"
                    labelSpan="3"
                    fieldSpan="19"
                    class="title" labelCode='post.title.label'/>

            <gd:textFieldRow
                    name='subject'
                    value="${post.subject}" l
                    abelCodeDefault="Subject:"
                    labelSpan="3"
                    fieldSpan="19"
                    class="text" labelCode='post.subject.label'/>

            <gd:ckeditorRow
                    name='content'
                    labelCodeDefault="Content::"
                    value="${post.content}"
                    labelSpan="3"
                    fieldSpan="19"
                    labelCode='post.content.label'
                    height="400px" width="90%"/>

            <gd:dateFieldRow name="expiresOn"
                             labelCode="post.expiresOn.label"
                             labelCodeDefault="Expires On:"
                             labelSpan="3"
                             fieldSpan="19"
                             value="${post?.expiresOn ? DateUtil.convertToJqueryFormat(post?.expiresOn) : DateUtil.getDefaultExpiresOn()}"
                             minDate="${DateUtil.convertDateToJqueryFormat(new Date())}"
                             maxDate="${ConfigurationHolder.config.grepdeals.posts.expiresOn.maxDuration}"/>

            <gd:textFieldRow name='tags'
                             value="${post.tags.join(',')}"
                             labelCodeDefault="Tags:"
                             labelSpan="3"
                             fieldSpan="19"
                             class="text"
                             labelCode='post.tags.label'/>

            <gd:submitButtonRow
                    name="publishPost"
                    labelSpan="3"
                    fieldSpan="3"
                    value="${message(code:'post.publish.button',default:'Publish')}"/>
        </div>

    </g:form>
</div>
<br/><br/>
</body>
</html>