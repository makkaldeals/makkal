<%@ page import="com.grepdeals.consts.CategoryTree"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Settings</title>
<link rel="shortcut icon"
	href="${resource(dir: 'images', file: 'favicon.ico')}"
	type="image/x-icon" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${resource(dir: 'css', file: 'screen.css')}" />
<!--[if IE]>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ie.css')}" type="text/css" media="screen, projection">
    <![endif]-->
<link rel="stylesheet" type="text/css" media="screen"
	href="${resource(dir: 'css', file: 'grep.css')}" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${resource(dir: 'css', file: 'grep_form.css')}" />
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
</g:javascript>	
</head>
<body>
	<%
	
    def user = session.user;
 //   if (!user.isAttached()){
 //     user.attach();
  //  }

  %>
	<div class="container">
		<div id="header" class="column span-24 last">
			<h1 class="column span-8 last">Grep Deals</h1>
			<div id="nav" class="column span-16 last">
				<ul>
					<li><g:link controller='login' action='index'
							title="Grep Deals home">Home</g:link>
					</li>
					<li><g:link controller='login' action='contactus'
							title="Contact Us">Contact Us</g:link>
					</li>
					<li><g:link controller='customer' action='index'
							title="Customer Login">Customer Login</g:link>
					</li>
					<sec:ifLoggedIn>
						<li><g:link controller='logout' action='index' title="Logout">Logout</g:link>
						</li>
					</sec:ifLoggedIn>
				</ul>
			</div>
			<div id="mast" class="column span-24 last">
				<img src="/grepdeals/images/rpm_guys.jpg" />
			</div>
			<div id="subheader" class="column span-24 last">
				<h2>Grep Deals is coming soon</h2>
			</div>
		</div>
	</div>

	<div id="grepGroupbox" class="grep_group_form"
		style="margin: 40px auto;">
		<g:form action="userSettings" controller="settings" action="updateSettings">
			<h1>Settings</h1>
			<g:if test='${confirmationMessage}'>
				<br />
				<font color="red">
					${confirmationMessage}
				</font>
				<br />
			</g:if>

			<div class="formbody">
				<P>You can provide the facebook credentials below</P>
				<table>
					<tbody>
						<s2ui:textFieldRow name='facebookId' bean="${user}"
							value="${user.facebookId}" size='25'
							labelCode='user.facebookId.label' />
						<s2ui:passwordFieldRow name='facebookPass' bean="${user}"
							value="${user.facebookPassword}" size='25'
							labelCode='user.facebookPassword.label' />
						<tr>
							</td>
						</tr>
						<tr>
							<td valign="top" class="name"><label for="SelectedCategory">
									${message(code: 'user.selectedcategory.label', default: labelCodeDefault)}
							</label></td>

							<td valign="top" class="value "><g:select
									name="AssignedCategories" size=25
									from="${user.getCategories()}" value="${user.getCategories()}"
									multiple="true" /></td>
						</tr>
						<tr>
							<td width='100%' align="Left"><input
								type='button' tabindex="2" value='Remove Categories' id='move2'
								class='s2ui_hidden_button' onclick = "fnMoveItems('AssignedCategories','All-Categories')">
							</td>						
							<td width='100%' align="Right"><input
								type='button' tabindex="2" value='Assign Categories' id='move1'
								class='s2ui_hidden_button' onclick = "fnMoveItems('All-Categories','AssignedCategories')"></td>

								
						</tr>
						<tr>
							<td valign="top" class="name"><label for="AllCategory">
									${message(code: 'user.allcategory.label', default: labelCodeDefault)}
							</label></td>

							<td valign="top" class="value "><g:select
									name="All-Categories" size=25
									from="${CategoryTree.Category.allChildren()}"
									value="${CategoryTree.Category.allChildren()}" multiple="true" />
							</td>
						</tr>

						<tr>
							<td />
						<tr>
							<td colspan='2' width='100%' align="center"><input
								type='submit' tabindex="2" value='Submit' id='submit'
								class='s2ui_hidden_button' /></td>
						</tr>
						</tr>

					</tbody>
				</table>
			</div>

		</g:form>
	</div>

	<div id="footer">
		<P align="center">
			<g:link controller='login' action='howitworks' title="How it Works"
				style="color: #fff;text-decoration: none;padding: 15px;">How it Works</g:link>
			<g:link controller='login' action='aboutus' title="About Us"
				style="color: #fff;text-decoration: none;padding: 15px;">About Us</g:link>
		</P>
		<p>Copyright &copy; Grep Deals, all rights reserved.</p>
	</div>
</body>
