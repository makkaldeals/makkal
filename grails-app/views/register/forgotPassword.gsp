<head>
<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
<meta name='layout' content='register'/>
</head>

<body>

<p/>



<s2ui:form width='350' height='220' elementId='forgotPasswordFormContainer'
           titleCode='spring.security.ui.forgotPassword.header' center='true'>

	<g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>

	<g:if test='${emailSent}'>
	<br/>
	<g:message code='spring.security.ui.forgotPassword.sent'/>
	</g:if>

	<g:else>

	<br/>
	<h4><g:message code='spring.security.ui.forgotPassword.description'/></h4>


    <g:if test="${flash.error}">
      <div class="errors">
             ${flash.error}
      </div>
    </g:if>

	<table>
		<tr>
			<td><label for="email"><g:message code='user.email.label'/></label></td>
			<td><g:textField name="email" size="25" /></td> 
		</tr>
	</table>

    <g:hiddenField name="targetUrl" value="${params.targetUrl}" />
	<s2ui:submitButton elementId='reset' form='forgotPasswordForm' messageCode='spring.security.ui.forgotPassword.submit'/>

	</g:else>

	</g:form>
</s2ui:form>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>
