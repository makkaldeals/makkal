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
<div id="registerContainer" class="formbody">

    <g:if test="${flash.message}">
        <div class="message">
            ${flash.message}
        </div>
    </g:if>

    <g:if test='${confirmationMessage}'>
        <br/>
        ${confirmationMessage}
    </g:if>

    <g:else>

        <div class="formtitle span-24  last">

            <g:message code='spring.security.ui.register.title'/>    ${params.role}

        </div>
        <g:form action='register' name='registerForm' class="inline">
            <g:set var="labelSpan" value="3"/>
            <g:set var="fieldSpan" value="19"/>
            <div class="column span-22 append-1 prepend-1 last ">
                <hr class="space"/>
                <g:if test="${params.role == null || params.role == Role.ROLE_CLIENT}">

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
                </g:if>
                <g:elseif test="${params.role == Role.ROLE_CUSTOMER}">

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
                </g:elseif>
                <gd:textFieldRow
                        name='areaCode'
                        value="${command.areaCode}"
                        labelSpan="${labelSpan}"
                        fieldSpan="${fieldSpan}"
                        class="text"
                        labelCode='user.areacode.label'
                        labelCodeDefault='Area Code'/>

                <g:if test="${params.role == Role.ROLE_CUSTOMER}">

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

                </g:if>

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

            <g:hiddenField name="role" value="${params.role}"/>
            <g:hiddenField name="targetUrl" value="${params.targetUrl}"/>
            <g:hiddenField name="accountLocked" value="false"/>
        </g:form>
    </g:else>
</div>

<!--
<p/>

<div id="grepGroupbox" class="grep_group_form" >

<g:form action='register' name='registerForm'>

    <h1><g:message code='spring.security.ui.register.title'/></h1>
<div class="formbody">
    <g:if test="${flash.message}">
        <div class="message">
        ${flash.message}
        </div>
    </g:if>

    <g:if test='${confirmationMessage}'>
        <br/>
        ${confirmationMessage}
    </g:if>

    <g:else>

        <br/>

      <table>
      <tbody>


        <s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
                           size='25' labelCode='user.email.label' labelCodeDefault='E-mail'/>

        <s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                               size='25' labelCodeDefault='Password' value="${command.password}"/>


        <g:if test="${params.role == Role.ROLE_CUSTOMER}">
            <s2ui:textFieldRow name='firstName' bean="${command}" value="${command.firstName}"
                               size='25' labelCode='user.firstName.label'/>
            <s2ui:textFieldRow name='lastName' bean="${command}" value="${command.lastName}"
                               size='25' labelCode='user.lastName.label'/>
            <s2ui:textFieldRow name='businessName' bean="${command}" value="${command.businessName}"
                               size='25' labelCode='user.businessName.label'/>
            <tr class="prop">
                    <td valign="top" class="name">
                        <label for="category">${message(code: 'user.category.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
            <g:select name='category' from="${CategoryTree.Category.children()}"
                      valueMessagePrefix='user.category.label'
                      optionValue='${category}' labelCode='user.category.label'
                      onchange="${remoteFunction(
                                         controller:'register',
                                         action:'ajaxGetSubcategories',
                                         params:'\'name=\' + this.value',
                                         onComplete:'updateSubcategories(e)')}"/>
            </td>
                 </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="subcategory">${message(code: 'user.subcategory.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
            <g:select name='subcategory'
                      optionKey="${subcategory}" optionValue="${subcategory}" labelCode='user.subcategory.label'/>
            </td>
                </tr>

            <s2ui:textFieldRow name='address' bean="${command}" value="${command.address}"
                               size='25' labelCode='user.address.label'/>

            <s2ui:textFieldRow name='city' bean="${command}" value="${command.city}"
                               size='25' labelCode='user.city.label'/>
            <s2ui:textFieldRow name='state' bean="${command}" value="${command.state}"
                               size='25' labelCode='user.state.label'/>
        </g:if>
        <s2ui:textFieldRow name='areaCode' bean="${command}" value="${command.areaCode}"
                           size='25' labelCode='user.areacode.label' labelCodeDefault='Area Code'/>

        <g:if test="${params.role == Role.ROLE_CUSTOMER}">
            <tr class="prop">
                    <td valign="top" class="name">
                        <label for="country">${message(code: 'user.country.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
            <g:countrySelect name="country" from="${['US','IN'] }" optionValue="${country}"
                             valueMessagePrefix="user.country.label"/>
            </td>
               </tr>
            <s2ui:textFieldRow name='phone' bean="${command}" value="${command.phone}"
                               size='25' labelCode='user.phone.label'/>
            <s2ui:textFieldRow name='website' bean="${command}" value="${command.website}"
                               size='25' labelCode='user.website.label'/>

        </g:if>

        <tr>
              <td width="75px"></td>
              <td align="right">
                  &nbsp;<jcaptcha:jpeg name="image"/>
        </td>
              </tr>
              <tr>
              <td width="75px"></td>
              <td align="right" >
        <g:textField name="response" value="" size='25'/>
        </td>
          </tr>
          <tr>
              <td  colspan='2' width='100%' align="center">
                   <input type='submit' tabindex="2" value='Create Account' id='create_account' class='s2ui_hidden_button' />
               </td>
          </tr>
      </tbody>
      </table>
        <g:hiddenField name="role" value="${params.role}"/>
        <g:hiddenField name="targetUrl" value="${params.targetUrl}"/>
    </g:else>
    <g:hiddenField name="accountLocked" value="false"/>
    </div>
</g:form>

</div>
-->
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
   ${remoteFunction(controller: "register", action: "ajaxGetSubcategories", params: "'name=' + selectedCategory.value, accountLocked=false", onComplete: "updateSubcategories(e)")}

</g:javascript>

</body>
