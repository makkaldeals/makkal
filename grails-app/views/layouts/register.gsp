<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title><g:layoutTitle default='User Registration'/></title>

<resource:include components="richTextEditor" autoComplete="[skin: 'default']" /> 


<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>

<g:javascript library='jquery' plugin='jquery' />
<g:javascript src='jquery/jquery.jgrowl.js'/>
<g:javascript src='jquery/jquery.checkbox.js'/>
<g:javascript src='spring-security-ui.js'/>
<jqui:resources/>
<jqval:resources />
<jqvalui:resources />
<blueprint:resources/>

<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'reset.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'spring-security-ui.css')}"/>

<link rel="stylesheet" media="screen" href="${resource(dir:'css/smoothness',file:'jquery-ui-1.8.2.custom.css',plugin:'spring-security-ui')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.jgrowl.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.safari-checkbox.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'auth.css')}"/>
<link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'screen.css')}"/>
    <!--[if IE]>
        <link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" type="text/css" media="screen, projection">
    <![endif]-->
 <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'grep.css')}" />   
 <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'grep_form.css')}" />
  <!-- TODO clean up nav plugin -->
<nav:resources/>
<g:layoutHead/>
 
</head>

<body>
<!-- 
<div id="menu">
  <nav:render /> 
</div>
 -->
 <div class="container"> 
	        <div id="header" class="column span-24 last">
	            <h1 class="column span-8 last">Grep Deals</h1>
	            <div id="nav" class="column span-24 last">
	                <ul>
	                    <li><g:link controller='login' action='index' title="Grep Deals home">Home</g:link></li>
	                   	<li><g:link controller='login' action='contactus' title="Contact Us">Contact Us</g:link></li>
	                   	<li><g:link controller='customer' action='index' title="Customer Login">Customer Login</g:link></li>
	                   	<sec:ifLoggedIn>
							<li><g:link controller='logout' action='index' title="Logout">Logout</g:link></li>
							<li><g:link controller='settings' action='index' title="Settings">Settings</g:link></li>
						</sec:ifLoggedIn>
	                </ul>
	            </div>
	            <div id="mast" class="column span-24 last">
	    			<img  src="/grepdeals/images/rpm_guys.jpg" />
	            </div>
	            <div  id="subheader" class="column span-24 last"><h2>Grep Deals is coming soon</h2></div>
	        </div>
	 </div> 

<g:layoutBody/>

<s2ui:showFlash/>

<div id="footer">
    <P align="center">
    	<g:link controller='login' action='howitworks' title="How it Works" style="color: #fff;text-decoration: none;padding: 15px;">How it Works</g:link>
	    <g:link controller='login' action='aboutus' title="About Us" style="color: #fff;text-decoration: none;padding: 15px;">About Us</g:link>
    </P>
    <p>Copyright &copy; Grep Deals, all rights reserved.</p>
</div>

</body>
</html>
