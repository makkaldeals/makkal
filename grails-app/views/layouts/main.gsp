<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
<%@ page import="com.grepdeals.auth.Role" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title><g:layoutTitle default='Welcome to Grepdeals'/></title>

    <resource:include components="richTextEditor" autoComplete="[skin: 'default']"/>


    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>

    <g:javascript library='jquery' plugin='jquery'/>
    <g:javascript src='jquery/jquery.jgrowl.js'/>
    <g:javascript src='jquery/jquery.checkbox.js'/>
    <g:javascript src='spring-security-ui.js'/>
    <jqui:resources/>
    <jqval:resources/>
    <jqvalui:resources/>
    <blueprint:resources/>

    <link rel="stylesheet" media="screen" href="${resource(dir: 'css', file: 'reset.css')}"/>
    <link rel="stylesheet" media="screen" href="${resource(dir: 'css', file: 'spring-security-ui.css')}"/>

    <link rel="stylesheet" media="screen"
          href="${resource(dir: 'css/smoothness', file: 'jquery-ui-1.8.2.custom.css', plugin: 'spring-security-ui')}"/>
    <link rel="stylesheet" media="screen" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}"/>
    <link rel="stylesheet" media="screen" href="${resource(dir: 'css', file: 'jquery.safari-checkbox.css')}"/>
    <link rel="stylesheet" media="screen" href="${resource(dir: 'css', file: 'auth.css')}"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'grep.css')}"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'grep_form.css')}"/>
    <g:layoutHead/>

</head>

<body>

<div class="container ">
    <div id="header" class="column span-24 last">
        <h1 class="column span-6  last">Grep Deals</h1>

        <div id="nav" class="column span-18 last">
            <ul>
                <li><g:link controller='login' action='index' title="Grep Deals home">Home</g:link></li>
                <li><g:link controller='login' action='contactus' title="Contact Us">Contact Us</g:link></li>
                <sec:access expression="hasRole('ROLE_CUSTOMER')">
                	<li><g:link controller='customer' action='index' title="Post Advertisement">Post Advertisement</g:link></li>
                </sec:access>
                <sec:access expression="!hasRole('ROLE_CUSTOMER')">
                	<li><g:link controller='customer' action='index' title="Merchant Signup">Merchant Signup</g:link></li>
                </sec:access>
                <sec:ifLoggedIn>
                    <li><g:link controller='logout' action='index' title="Logout">Logout</g:link></li>
                    <li><g:link controller='settings' action='index' title="Settings">Settings</g:link></li>
                </sec:ifLoggedIn>
            </ul>
        </div>
    </div>


    <div class="column containerbody span-24 last">
        <g:layoutBody/>
    </div>

    <div id="footer" class="column span-24 last">
        <P align="center">
            <g:link controller='login' action='howitworks' title="How it Works"
                    style="color: #fff;text-decoration: none;padding: 15px;">How it Works</g:link>
            <g:link controller='login' action='aboutus' title="About Us"
                    style="color: #fff;text-decoration: none;padding: 15px;">About Us</g:link>
        </P>

        <p>Copyright &copy; Grep Deals, all rights reserved.</p>
    </div>
</div>
<fbg:resources/>
</body>
</html>
