<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: 6/23/11
  Time: 9:37 PM
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
    <g:javascript>

        function updateSubcategories(e) {

            // The response comes back as a bunch-o-JSON
            var subcategories = eval("(" + e.responseText + ")") // evaluate JSON

            if (subcategories) {
                var subategorySelect = document.getElementById('subcategory');

                // Clear all previous options
                var l = subategorySelect.length;

                while (l > 0) {
                    l--;
                    subategorySelect.remove(l);
                }

                //  Rebuild the select
                for (var i = 0; i < subcategories.length; i++) {
                    var subcategory = subcategories[i];
                    var opt = document.createElement('option');
                    opt.text = subcategory;
                    opt.value = subcategory;
                    try {
                        subategorySelect.add(opt, null);// standards compliant; doesn't work in IE
                    } catch(ex) {
                        subategorySelect.add(opt); // IE only
                    }
                }
            }
        }

    </g:javascript>
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
        <hr class="space"/>

        <div class="error">
            <h4>${flash.message}</h4>
        </div>
    </g:if>
    <g:form action='register' name='registerForm' class="inline">
        <g:set var="labelSpan" value="3"/>
        <g:set var="fieldSpan" value="19"/>
        <div class="column span-22 append-1 prepend-1 last ">
            <hr class="space"/>


            <gd:textFieldRow
                    name='firstName'
                    value="${command.firstName}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.firstName.label'/>

            <gd:textFieldRow
                    name='lastName'
                    value="${command.lastName}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.lastName.label'/>

            <gd:textFieldRow
                    name='businessName'
                    value="${command.businessName}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.businessName.label'/>

            <gd:selectFieldRow
                    name='category'
                    from="${CategoryTree.Category.children()}"
                    valueMessagePrefix='user.category.label'
                    optionValue="${category}"
                    labelCode='user.category.label'
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    onchange="${remoteFunction(
                             controller:'register',
                             action:'ajaxGetSubcategories',
                             params:'\'name=\' + this.value',
                             onComplete:'updateSubcategories(e)')}"/>

            <gd:selectFieldRow
                    name='subcategory'
                    optionKey="${subcategory}"
                    optionValue="${subcategory}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    labelCode='user.subcategory.label'/>


            <gd:textFieldRow
                    name='address'
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    value="${command.address}"
                    labelCode='user.address.label'/>

            <gd:textFieldRow
                    name='city'
                    value="${command.city}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.city.label'/>

            <gd:textFieldRow
                    name='state'
                    value="${command.state}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.state.label'/>
            <gd:textFieldRow
                    name='areaCode'
                    value="${command.areaCode}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.areacode.label'
                    labelCodeDefault='Area Code'/>


            <gd:countrySelectRow
                    name="country"
                    from="${['US','IN'] }"
                    optionValue="${country}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    labelCode="user.country.label"
                    valueMessagePrefix="user.country.label"/>

            <gd:textFieldRow
                    name='phone'
                    value="${command.phone}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.phone.label'/>

            <gd:textFieldRow
                    name='website'
                    value="${command.website}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.website.label'/>


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
        <g:hiddenField name="email" value="${command.email}"/>
        <g:hiddenField name="password" value="${command.password}"/>
        <g:hiddenField name="role" value="${params.role}"/>
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
<g:javascript>
   // This is called when the page loads to initialize subcategories
   var categorySelect = document.getElementById('category');
   var selectedCategory = categorySelect.options[categorySelect.selectedIndex];
   ${remoteFunction(controller: "register", action: "ajaxGetSubcategories", params: "'name=' + selectedCategory.value", onComplete: "updateSubcategories(e)")}

</g:javascript>

</body>
