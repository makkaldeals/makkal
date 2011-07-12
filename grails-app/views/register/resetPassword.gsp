<head>
<title><g:message code='spring.security.ui.resetPassword.title'/></title>
<meta name='layout' content='main'/>
</head>

<body>

<p/>

<div id="grepGroupbox" class="formbody" style="margin:70px auto;">
    
 		<div class="formtitle span-24  last">
            <g:message code='spring.security.ui.resetPassword.header'/>    
        </div>
  <g:form action='resetPassword' name="resetPasswordForm" autocomplete='off'>
	<g:hiddenField name='t' value='${token}'/>
    <div class="column span-22 append-1 prepend-1 last ">	    
		 <label for="password">
		 	<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}" labelCodeDefault='Password' value="${command?.password}"/>
		 </label>
		<BR/><BR/>
		 <label>
	       <input type='submit' tabindex="2" value='Update my password' id='reset_submit' class='s2ui_hidden_button'/>
		 </label>
		 
		 <g:hiddenField name="targetUrl" value="${params.targetUrl}"/>
		 
		 <script>
				$(document).ready(function() {
					
					$("#reset").button();
					$('#reset').bind('click', function() {
					   document.forms.resetPasswordForm.submit();
					});
				
				});
		 </script>
    </div>
  </g:form>
</div>

<script>
$(document).ready(function() {
	$('#password').focus();
});
</script>

</body>
