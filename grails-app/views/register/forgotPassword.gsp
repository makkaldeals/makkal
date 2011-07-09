<!DOCTYPE html>
<html>
<head>
<meta name='layout' content='main'/>
  <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
</head>
<body>

<div id="grepGroupbox" class="formbody" style="margin:70px auto;">
        <div class="formtitle span-24  last">
            <g:message code='spring.security.ui.forgotPassword.header'/>    
        </div>

  <g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off' class="inline">
  

    
	    <g:if test='${emailSent}'>
	      <div class="error_box">
	        <g:message code='spring.security.ui.forgotPassword.sent'/>
	      </div>
	    </g:if>
   		<g:else>
      
			    <g:if test="${flash.error}">
			      <div class="error_box">
			        ${flash.error}
			      </div>
			    </g:if>

 <div class="column span-22 append-1 prepend-1 last ">
		      <label for="email">
		        <g:message code='user.email.label'/>
		        <input class="text" name="email" style="width: 21em;" tabindex="1" type="text" value=""/>
		      </label>
		<br/>
		      <label>
		        <input type='submit' tabindex="2" value='Reset my password' id='reset_submit' class='s2ui_hidden_button'/>
		      </label>
		      <g:hiddenField name="targetUrl" value="${params.targetUrl}"/>
</div>
		      <script>
		        $(document).ready(function() {
		
		          $("#reset").button();
		          $('#reset').bind('click', function() {
		            document.forms.forgotPasswordForm.submit();
		          });
		
		        });
		      </script>
    	</g:else>
   
  </g:form>

 </div>
</body>
</html>