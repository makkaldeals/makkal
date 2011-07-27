<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>About Us</title>
    <meta name='layout' content='main'/>


    <title><g:message code='spring.security.ui.login.title'/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'screen.css')}"/>
    <!--[if IE]>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ie.css')}" type="text/css" media="screen, projection">
    <![endif]-->
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css', file: 'grep.css')}"/>

    <style type="text/css" media="screen">

    #slider {
        width: 410px; /*important to be same as image width */
        height: 300px; /*important to be same as image height */
        position: relative; /*important */
        overflow: hidden; /*important */
    }

    #sliderContent {
        width: 410px; /* important to be same as image width or wider */
        position: absolute;
        top: 0;
        margin-left: 0;
    }

    .sliderImage {
        float: left;
        position: relative;
        display: none;
    }

    .sliderImage span {
        position: absolute;
        font: 10px/15px Arial, Helvetica, sans-serif;
        padding: 10px 13px;
        width: 384px;
        background-color: #000;
        filter: alpha(opacity = 70);
        -moz-opacity: 0.7;
        -khtml-opacity: 0.7;
        opacity: 0.7;
        color: #fff;
        display: none;
    }

    .clear {
        clear: both;
    }

    .sliderImage span strong {
        font-size: 14px;
    }

    .top {
        top: 0;
        left: 0;
    }

    .bottom {
        bottom: 0;
        left: 0;
    }

    ul {
        list-style-type: none;
    }
    </style>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 's3Slider.js')}"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#slider').s3Slider({
                        timeOut: 4000
                    });
        });
    </script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/cupertino/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#accordion").accordion();
        });
        $(document).ready(function() {
            $("#accordion1").accordion();
        });
    </script>
</head>

<body>

<div id="registerContainer" class="formbody">

    <div class="formtitle span-24  last">
        Login
    </div>

    <div id="mast" class="column span-24 last">
        <img src="${resource(dir: 'images', file: 'rpm_guys.jpg')}" alt="rpm guys" />
        <fieldset style="border:2px  #00f; margin:0;-moz-border-radius:5px;
        border-radius: 5px;  -webkit-border-radius: 5px;">
            <legend style="border:none; margin:0">&nbsp;</legend>
            <g:if test="${flash.message}">
                <div class="message">
                    <g:message code="${flash.message}"/>
                </div>
            </g:if>
            <form action='${postUrl}' method='post' id="loginForm" name="loginForm" autocomplete='off'>
                <g:hiddenField name="spring-security-redirect"
                               value="/login/authSuccess?targetUrl=${params.targetUrl}"/>
                <P>
                    <label for="email"><g:message code='user.email.label'/>:</label>
                    <input name="j_username" type="text" size="25" value=""/>
                    &nbsp;&nbsp;<label for="password"><g:message code='user.password.label'/>:</label>
                    <input size="15" name="j_password" value="" type="password"/>

                </P>

                <P>&nbsp;</P>

                <P align="center"><input name="${rememberMeParameter}" checked="checked" type="checkbox"/>
                    <label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label> |
                <g:link controller='register' params="${params}" action='forgotPassword'><g:message
                        code='spring.security.ui.login.forgotPassword'/></g:link>
                </P>

                <P>&nbsp;</P>

                <p align="center">
                    <input type='submit' value='Log in' id='loginButton' class='s2ui_hidden_button'/>
                    <script>
                        $(document).ready(function() {
                            $("#loginButton").button();
                            $('#loginButton').bind('click', function() {
                                document.forms.loginForm.submit();
                            });

                        });
                    </script>

                </p>

                <P>&nbsp;</P>

                <p align="center">
                    <s2ui:linkButton elementId='register' controller='register' params="${params}"
                                     messageCode='spring.security.ui.login.register'/>
                </p>
            </form>

        </fieldset>

    </div>

</div>

<div class="container">

    <P>&nbsp;</P>

    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td width="33%" valign="top">
                <div valign="top" id="accordion" style="width: 70%;">
                    <h3><a href="#">Top Deal -1</a></h3>

                    <p>Contact admin to show your deal here</p>

                    <h3><a href="#">Top deal 2</a></h3>

                    <p></p>

                    <h3><a href="#">Top deal 3</a></h3>

                    <p></p>


                    <h3><a href="#">Top deal 4</a></h3>

                    <p></p>

                    <h3><a href="#">Top deal 5</a></h3>

                    <p></p>
                </div>
            </td>
            <td align="left" width="20%" valign="bottom">
                <div id="slider" valign="bottom">
                    <ul id="sliderContent">
                        <li class="sliderImage">
                            <a href=""><img src="${resource(dir: 'images/410', file: '1.jpg')}" alt="1"/></a>
                            <span class="top"><strong>Restaurant deals</strong><br/></span>
                        </li>
                        <li class="sliderImage">
                            <a href=""><img src="${resource(dir: 'images/410', file: '2.jpg')}" alt="2"/></a>
                            <span class="top"><strong>Car deals</strong><br/>
                            </span>
                        </li>
                        <li class="sliderImage">
                            <img src="${resource(dir: 'images/410', file: '3.jpg')}" alt="3"/>
                            <span class="bottom"><strong>Grocery deals</strong><br/></span>
                        </li>
                        <li class="sliderImage">
                            <img src="${resource(dir: 'images/410', file: '4.jpg')}" alt="4"/>
                            <span class="bottom"><strong>Travel deals</strong><br/></span>
                        </li>
                        <li class="sliderImage">
                            <img src="${resource(dir: 'images/410', file: '5.jpg')}" alt="5"/>
                            <span class="top"><strong>Hotel deals</strong><br/></span>
                        </li>

                        <div class="clear sliderImage"></div>
                    </ul>
                </div>
            </td>
            <td width="15%" valign="top">
                &nbsp;
            </td>
            <td width="33%" valign="top">
                <div valign="top" align="center" id="accordion1" style="width: 70%;">
                    <h3><a href="#">Top Deal 6</a></h3>

                    <p>Contact admin to show your deal here</p>

                    <h3><a href="#">Top deal 7</a></h3>

                    <p></p>

                    <h3><a href="#">Top Deal 8</a></h3>

                    <p></p>

                    <h3><a href="#">Top deal 9</a></h3>

                    <p></p>

                    <h3><a href="#">Top Deal 10</a></h3>

                    <p></p>
                </div>
            </td>
        </tr>
    </table>

</div>

<script>
    $(document).ready(function() {
        $('#email').focus();
    });

    <s2ui:initCheckboxes/>

</script>

</body>
</html>
