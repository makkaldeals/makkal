<!DOCTYPE html> 
<html> 
  <head> 
	<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
  </head> 
  <body> 

	<div id="grepGroupbox" class="grep_group_form" style="margin:70px auto;"> 
      <g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>
	      
	      <h1><g:message code='spring.security.ui.forgotPassword.header'/></h1>
		  
		  <div class="formbody"> 
		  		<g:if test='${emailSent}'>
					<div class="error_box">
						<g:message code='spring.security.ui.forgotPassword.sent'/>
					</div>
	 			</g:if>
		  		
		  		<g:if test="${flash.error}">
		  			<div class="error_box">
		   				${flash.error}
		   			</div>
				</g:if>		
				
		      	<label for="email"> 
		        	<g:message code='user.email.label'/><br /> 
		        	<input class="text"  name="login" style="width: 21em;" tabindex="1" type="text" value="" /> 
		      	</label>
		 
		      	<label> 
		        	<input type='submit' tabindex="2" value='Reset my password' id='reset_submit' class='s2ui_hidden_button' />
		      	</label>
		      	<g:hiddenField name="targetUrl" value="${params.targetUrl}" /> 
			</div> 
			
			  <script> 
					$(document).ready(function() {
						
						$("#reset").button();
						$('#reset').bind('click', function() {
						   document.forms.forgotPasswordForm.submit();
						});
					
					});
				</script> 
    	</g:form>  
    </div>
    
  </body> 
</html>