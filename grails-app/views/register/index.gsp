<%@ page import="com.makkaldeals.auth.Role" %>
<head>
	<meta name='layout' content='register'/>
	<title><g:message code='spring.security.ui.register.title'/></title>
</head>

<body>

<p/>


<s2ui:form width='700' height='550' elementId='loginFormContainer'
           titleCode='spring.security.ui.register.description' center='true'>

<g:form action='register' name='registerForm'>

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
		                   size='40' labelCode='user.email.label' labelCodeDefault='E-mail'/>

		<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                             size='40' labelCodeDefault='Password' value="${command.password}"/>


        <g:if test="${params.role == Role.ROLE_CUSTOMER}">
             <s2ui:textFieldRow name='firstName' bean="${command}" value="${command.firstName}"
		                   size='40' labelCode='user.firstName.label'/>
             <s2ui:textFieldRow name='lastName' bean="${command}" value="${command.lastName}"
		                   size='40' labelCode='user.lastName.label'/>
             <s2ui:textFieldRow name='businessName' bean="${command}" value="${command.businessName}"
		                   size='40' labelCode='user.businessName.label'/>
 		     <tr class="prop">
		     	<td valign="top" class="name">
		     		<label for="category">${message(code: 'user.category.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value "> 
		     		<g:select name='category' from="${['AU'] }" valueMessagePrefix='user.category.label'
		     				optionKey="${command.category}" optionValue="${command.category}" labelCode='user.category.label' />
		     	</td>
 			 </tr>
 			 <tr class="prop">
		     	<td valign="top" class="name">
		     		<label for="subcategory">${message(code: 'user.subcategory.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
		     		<g:select name='subcategory' from="${['AGS','APA','ARS','BSP','CD','CWD','GSS','MD','MR','OC','P','SM','SI','TW','TO'] }" valueMessagePrefix='user.subcategory.label'
		     				optionKey="${command.subcategory}" optionValue="${command.subcategory}" labelCode='user.subcategory.label' />
		     	</td>
		     </tr>

             <s2ui:textFieldRow name='address' bean="${command}" value="${command.address}"
		                   size='40' labelCode='user.address.label'/>

             <s2ui:textFieldRow name='city' bean="${command}" value="${command.city}"
		                   size='40' labelCode='user.city.label'/>
             <s2ui:textFieldRow name='state' bean="${command}" value="${command.state}"
		                   size='40' labelCode='user.state.label'/>
       </g:if>
       <s2ui:textFieldRow name='areaCode' bean="${command}" value="${command.areaCode}" 
                                  size='40' labelCode='user.areacode.label' labelCodeDefault='Area Code'/>

       <g:if test="${params.role == Role.ROLE_CUSTOMER}">
  			<tr class="prop">
		     	<td valign="top" class="name">
		     		<label for="country">${message(code: 'user.country.label', default: labelCodeDefault)}</label>
		     	</td>
		     	<td valign="top" class="value ">
		     		<g:select name='country' from="${['US','IN'] }" valueMessagePrefix='user.country.label'
		     				optionKey="${command.country}" optionValue="${command.country}" labelCode='user.country.label' />
		     	</td>
			</tr>	
             <s2ui:textFieldRow name='phone' bean="${command}" value="${command.phone}"
		                   size='40' labelCode='user.phone.label'/>
             <s2ui:textFieldRow name='website' bean="${command}" value="${command.website}"
		                   size='40' labelCode='user.website.label'/>

       </g:if>


	
	</tbody>
	</table>
    </br>
    <g:hiddenField name="role" value="${params.role}" />
    <g:hiddenField name="targetUrl" value="${params.targetUrl}" /> 


 	<s2ui:submitButton elementId='create' form='registerForm' messageCode='spring.security.ui.register.submit'/>

	</g:else>

</g:form>

</s2ui:form>

<script>
$(document).ready(function() {
	$('#email').focus();
});
</script>

</body>
