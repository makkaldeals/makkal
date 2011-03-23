<%@page import="com.makkaldeals.consts.CategoryTree"%>
<%@ page import="com.makkaldeals.auth.Role" %>
<head>
	<meta name='layout' content='register'/>
	<title><g:message code='spring.security.ui.register.title'/></title>
</head>

<body>

<p/>

<div id="grepGroupbox" class="grep_group_form" style="margin:70px auto;"> 
<!--  
<s2ui:form width='400' height='275' elementId='loginFormContainer'
           titleCode='spring.security.ui.register.description' center='true'>
-->
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
		     		<g:select name='category' from="${com.makkaldeals.CategoryTree.Category.children()}" valueMessagePrefix='user.category.label'
		     				  optionValue='${category}' labelCode='user.category.label' />
		     	</td>
 			 </tr>
 			<tr class="prop">
		     	<td valign="top" class="name">
		     		<label for="subcategory">${message(code: 'user.subcategory.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
		     		<g:select name='subcategory' from="${com.makkaldeals.CategoryTree.AU.allChildren()}" valueMessagePrefix='user.subcategory.label'
		     				optionKey="${subcategory}" optionValue="${subcategory}" labelCode='user.subcategory.label' />
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
		     		<g:countrySelect name="country" from="${['US','IN'] }" optionValue="${country}" valueMessagePrefix="user.country.label" />
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
				&nbsp;<jcaptcha:jpeg name="image" /> 
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
    <g:hiddenField name="role" value="${params.role}" />
    <g:hiddenField name="targetUrl" value="${params.targetUrl}" /> 

	</g:else>
</div>
</g:form>
<!--  
</s2ui:form>
-->
</div>
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
