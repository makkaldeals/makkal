<%@ page import="com.grepdeals.consts.CategoryTree"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Settings</title>
<meta name='layout' content='main'/>
<g:javascript library="prototype" />
<g:javascript>
function fnMoveItems(lstbxFrom,lstbxTo)
{
 var varFromBox = document.all(lstbxFrom);
 var varToBox = document.all(lstbxTo); 
 if ((varFromBox != null) && (varToBox != null)) 
 { 
  if(varFromBox.length < 1) 
  {
   alert('There are no items in the source ListBox');
   return false;
  }
  if(varFromBox.options.selectedIndex == -1) // when no Item is selected the index will be -1

  {
   alert('Please select an Item to move');
   return false;
  }
  while ( varFromBox.options.selectedIndex >= 0 ) 
  { 
   var newOption = new Option(); // Create a new instance of ListItem 

   newOption.text = varFromBox.options[varFromBox.options.selectedIndex].text; 
   newOption.value = varFromBox.options[varFromBox.options.selectedIndex].value; 
   varToBox.options[varToBox.length] = newOption; //Append the item in Target Listbox
   varFromBox.remove(varFromBox.options.selectedIndex); //Remove the item from Source Listbox 

  } 
 }
 return false; 
}

function fnArrangeCategories() {
	 var varFromBox = document.all("AssignedCategories");
 	 var varToBox = document.all("All-Categories"); 
 	 
 	 for (var i = 0; i < varFromBox.options.length; i++) {
 	 	var valueToRemove = varFromBox.options[i].value;
 	 	for (var j=0; j < varToBox.options.length; j++) {
 	 		
 	 		if (valueToRemove == varToBox.options[j].value) {
 	 			varToBox.remove(j);
 	 		}
 	 	}
 	 }

}

function fnSelectAll () {
	
	var e_select = document.forms['userSettings'].elements["AssignedCategories"];

	for (var i = 0; i < e_select.options.length; i++)
	{
			e_select.options[i].selected = true;
	}
}

function fnclear() {

	alert ("Clearing the categories is pending")
}
</g:javascript>


<script type="text/javascript">
	function facebookLogin() {
		FB.getLoginStatus(function(response) {
			if (response.session) {
				// logged in and connected user, someone you know
				window.location ="${createLink(controller:'settings', action:'show')}";
			}
		});
	}
</script>


</head>
<body onLoad="fnArrangeCategories()">
	<%
	
    def user = session.user;
  %>

<div>
<h2 valign="center">
   <g:if test="${flash.message}">
       <div class="message">
              ${flash.message}
       </div>
     </g:if>

	<g:if test='${confirmationMessage}'>
      <br/>
      ${confirmationMessage}
	</g:if>
</h2>
</div>

  	<div class="column span-24 append-1 prepend-1 last">
		<g:form name="userSettings" controller="settings" action="updateSettings" class="inline">
			<h1>Settings</h1>

	<br/>
			<div>
				<P>You can provide the facebook credentials below</P>
				<table>
					<tbody>
					<div class="column span-24 last">
		                    <fb:login-button perms="email,publish_stream" onlogin="facebookLogin();" size="large">
		                        <g:message code="auth.login.facebook"/>
		                    </fb:login-button>
					</div>
						  <div class="column span-4">
						  	<label for="facebookId"><g:message code="user.facebookId.label" default="FacebookId:"/></label>
					      </div>
					      <div class="column span-20 last">
					      	<g:textField size="50" name="facebookId" class = "facebookId" value="${user.facebookId}"/>
					      </div>

						  <div class="column span-4">
						  	<label for="facebookPass"><g:message code="user.facebookPassword.label" default="Facebook Password:"/></label>
					      </div>
					      <div class="column span-20 last">
					      	<g:passwordField size="50" name="facebookPass" class = "facebookPassword" value="${user.facebookPassword}"/>
					      </div>
						  <div class="column span-12">
						  	<label for="SelectedCategory"><g:message code="user.selectedcategory.label" default="Assigned Categories:"/></label>
					      </div>
						  <div class="column span-12 last">
						  	<label for="AllCategory"><g:message code="user.allcategory.label" default="All Categories:"/></label>
					      </div>					   
					      
					   	  <div class="column span-6">
					      	<g:select name="AssignedCategories" size=50 from="${user.getCategories()}" value="${user.getCategories()}"
									multiple="true" />
						  </div>
						  <div class="column span-6 last">
								<input
									type='button' tabindex="2" value='Remove Categories' id='move2'
									class='s2ui_hidden_button' onclick = "fnMoveItems('AssignedCategories','All-Categories')">
														
								<input
									type='button' tabindex="2" value='Assign Categories' id='move1'
									class='s2ui_hidden_button' onclick = "fnMoveItems('All-Categories','AssignedCategories')">
						  	</div>					      
						  
					
					   	  <div class="column span-12 last">
					      	<g:select name="All-Categories" size=25 from="${CategoryTree.Category.allChildren()}" value="${CategoryTree.Category.allChildren()}" 
									multiple="true" />
						  </div>				      
						<div class="column span-12 prepend-8 last">
			    			<g:submitButton name="submit" form="userSettings" onClick="fnSelectAll()"
			            	value="${message(code:'settings.submit.button',default:'Submit')}"/>
			    		</div>
					</tbody>
				</table>
			</div>

		</g:form>
	</div>
</body>
