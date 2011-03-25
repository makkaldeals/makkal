<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>About Us</title>
<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>
<link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'screen.css')}"/>
    <!--[if IE]>
        <link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" type="text/css" media="screen, projection">
    <![endif]-->
 <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'grep.css')}" />   
 <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir:'css',file:'grep_form.css')}" />
</head>
<body>

 	<div class="container">
	        <div id="header" class="column span-24 last">
	            <h1 class="column span-8 last">Grep Deals</h1>
	            <div id="nav" class="column span-16 last">
	                <ul>
	                    <li><g:link controller='login' action='index' title="Grep Deals home">Home</g:link></li>
	                   	<li><g:link controller='login' action='contactus' title="Contact Us">Contact Us</g:link></li>
	                   	<li><g:link controller='customer' action='index' title="Customer Login">Customer Login</g:link></li>
	                   	<sec:ifLoggedIn>
							<li><g:link controller='logout' action='index' title="Logout">Logout</g:link></li>
						</sec:ifLoggedIn>
	                </ul>
	            </div>
	            <div id="mast" class="column span-24 last">
	    			<img  src="/grepdeals/images/rpm_guys.jpg" />
	            </div>
	            <div  id="subheader" class="column span-24 last"><h2>Grep Deals is coming soon</h2></div>
	        </div>
	 </div> 

	<div id="grepGroupbox" class="grep_group_form" style="width:90%;margin:70px auto;"> 
	      <form>      
		      <h1>
		      	About Us
		      </h1> 
			  <div class="formbody"> 
			      	<P align="left">Grepdeals is committed to provide good auto deals to subscribers from autodealers of USA</P>
			      	<P align="left">Thousands of subscribers are enjoying autodeals through grepdeals and hundreds of dealers are providing good deals to the subscribers</P>
			      	<p align="left">Grepdeals is one of the fastest growing online deals website in North America</p>
			      	<p align="left">We respect your privacy and we will not give or use your email address to any other commercial activity</p>
			      	<P align="left">You can reach us through <B>admin@grepdeals.com</B></P>
			      	<BR/>
			      	<P align="left">Our mission is to <B>make all happy</B></P>
			      	
			  </div> 
	    </form>   
    </div>
    
    <div id="footer">
	    <P align="center">
	    	<g:link controller='login' action='howitworks' title="How it Works" style="color: #fff;text-decoration: none;padding: 15px;">How it Works</g:link>
		    <g:link controller='login' action='aboutus' title="About Us" style="color: #fff;text-decoration: none;padding: 15px;">About Us</g:link>
	    </P>
	    <p>Copyright &copy; Grep Deals, all rights reserved.</p>
	</div>
  </body> 
</html>
