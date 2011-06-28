<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: 6/22/11
  Time: 9:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.grepdeals.consts.CategoryTree" %>
<%@ page import="com.grepdeals.auth.Role" %>
<head>
    <meta name='layout' content='main'/>
    <g:javascript library="prototype"/>
    <title><g:message code='spring.security.ui.register.title'/></title>
    <jqvalui:renderValidationScript for="com.grepdeals.auth.RegisterCommand"
                                    form="registerForm" errorClass="invalid"/>

</head>

<body>
<!-- TODO: Complete validation -->
<g:if test='${confirmationMessage}'>
    <br/>

    <div class="span-16 prepend-4 append-4  last">
        <div class="info"><h4>${confirmationMessage}</h4></div>
    </div>
</g:if>

<g:else>
    <div id="registerContainer" class="formbody">

        <div class="formtitle span-24  last">

            <g:message code='spring.security.ui.register.title'/>

        </div>
        <g:if test="${flash.message}">
            <hr class="space" />
            <div class="error">
                <h4> ${flash.message} </h4>
            </div>
        </g:if>
        <g:form action='register' name='registerForm' class="inline">
            <g:set var="labelSpan" value="3"/>
            <g:set var="fieldSpan" value="19"/>
            <div class="column span-22 append-1 prepend-1 last ">
                <hr class="space"/>

                <gd:textFieldRow
                        name='email'
                        value="${command.email}"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        class="text"
                        labelCode='user.email.label'
                        labelCodeDefault='E-mail'/>

                <gd:passwordFieldRow
                        name='password'
                        labelCode='user.password.label'
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        class="text"
                        labelCodeDefault='Password'
                        value="${command.password}"/>

                <gd:textFieldRow
                        name='areaCode'
                        value="${command.areaCode}"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        class="text"
                        labelCode='user.areacode.label'
                        labelCodeDefault='Area Code'/>

                <gd:jcaptchaFieldRow
                        name="image"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"/>

                <gd:textFieldRow
                        name="response"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        class="text"
                        labelCode="jcaptcha.response.label"
                        value=""/>

                <gd:submitButtonRow
                        name="create_account"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        value="${message(code:'user.register..button',default:'Create Account:')}"/>
            </div>

            <g:hiddenField name="role" value="${Role.ROLE_CLIENT}"/>
            <g:hiddenField name="targetUrl" value="${params.targetUrl}"/>
        </g:form>

    </div>

</g:else>
<script>


    $(document).ready(function() {
        $('#email').focus();


        $("#create_account").button();
        $('#create_account').bind('click', function() {
            document.forms.registerForm.submit();
        });
    });
</script>

</body>
