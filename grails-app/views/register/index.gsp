<head>
	<meta name='layout' content='register'/>
	<title><g:message code='spring.security.ui.register.title'/></title>
</head>

<body>

<p/>


<s2ui:form width='650' height='300' elementId='loginFormContainer'
           titleCode='spring.security.ui.register.description' center='true'>

<g:form action='register' name='registerForm'>

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

        <s2ui:textFieldRow name='areaCode' bean="${command}" value="${command.areaCode}" 
		                   size='40' labelCode='user.areacode.label' labelCodeDefault='Area Code'/>

	
	</tbody>
	</table>

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