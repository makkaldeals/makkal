<!DOCTYPE html> 
<html> 
  <head> 
	<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
     <link rel="stylesheet" type="text/css" media="screen" href="/makkaldeals/css/screen.css" />
    <!--[if IE]>
        <link rel="stylesheet" href="css/ie.css" type="text/css" media="screen, projection">
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="/makkaldeals/css/grep.css" />   
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/cupertino/jquery-ui.css" rel="stylesheet" type="text/css"/>	
	<link rel="stylesheet" type="text/css" media="screen" href="/makkaldeals/css/grep_form.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  </head> 
  <body>  
	<div class="container">
	        <div id="header" class="column span-24 last">
	            <h1 class="column span-8 last">Grep Deals</h1>
	            <div id="nav" class="column span-16 last">
	                <ul>
	                    <li><a class="active" title="Grep Deals home" href="#">Home</a></li>
	                    <li><a title="Contact Us" href="#">Contact Us</a></li>
	                    <li><a title="About" href="#">About</a></li>
	                    <li><a title="How it Works" href="#">How it Works</a></li>
	                </ul>
	            </div>
	            <div id="mast" class="column span-24 last">
	    			<img  src="/makkaldeals/images/rpm_guys.jpg" />
	            </div>
	            <div  id="subheader" class="column span-24 last"><h2>Grep Deals is the #1 online, midwest, <em>coupon</em> provider</h2></div>
	        </div>
	 </div>  

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
	
	<div id="footer">
        <p>Copyright &copy; Grep Deals, all rights reserved.</p>
    </div>
    
  </body> 
</html> 
