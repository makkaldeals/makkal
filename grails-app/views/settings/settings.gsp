<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Settings</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'screen.css')}"/>
    <!--[if IE]>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ie.css')}" type="text/css" media="screen, projection">
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'grep.css')}"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'grep_form.css')}"/>
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
            <img src="/grepdeals/images/rpm_guys.jpg"/>
        </div>
        <div id="subheader" class="column span-24 last"><h2>Grep Deals is coming soon</h2></div>
    </div>
</div>

<div id="grepGroupbox" class="grep_group_form" style="margin:60px auto;">
    <g:form action="sendContactus">
        <h1>
            Settings
        </h1>
        <g:if test='${confirmationMessage}'>
            <br/>
            <font color="red">${confirmationMessage}</font>
            <br/>
        </g:if>

        <div class="formbody">
            <P>You can provide the facebook credentials below</P>
            <table>
                <tbody>
                <s2ui:textFieldRow name='facebookId' bean="${command}" value="${command.facebookId}"
                        size='25' labelCode='user.facebookId.label'/>
                <s2ui:textFieldRow name='facebookPass' bean="${command}" value="${command.facebookPassword}"
                        size='25' labelCode='user.facebookPassword.label'/>
                <tr>
                    <td/>
                    <td align='center'>
                        <input type='submit' tabindex="1" value='Submit'/>
                    </td>

                </tr>

                </tbody>
            </table>
        </div>

    </g:form>
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