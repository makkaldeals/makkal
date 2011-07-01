<!DOCTYPE html>
<html>

<head>
    <meta name='layout' content='main'/>
    <g:javascript library="prototype"/>
    <title>Contact Us</title>

</head>


<body>

<div id="registerContainer" class="formbody">

    <div class="formtitle span-24  last">
        Contact Us
    </div>



    <g:form action="sendContactus">

        <g:if test="${confirmationMessage}">
            <hr class="space"/>

            <div class="error">
                <h4>${confirmationMessage}</h4>
            </div>
        </g:if>






        <g:set var="labelSpan" value="3"/>
        <g:set var="fieldSpan" value="19"/>
        <div class="column span-22 append-1 prepend-1 last ">
            <hr class="space"/>



            Please fill the form below so that we can reach you


            <hr class="space"/>


            <gd:textFieldRow
                    name='firstName'
                    value="${command.firstName}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.firstName.label'
                    bean="${command}"
                    labelCodeDefault='First Name'/>

            <gd:textFieldRow
                    name='lastName'
                    value="${command.lastName}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.lastName.label'
                    bean="${command}"
                    labelCodeDefault='Last Name'/>

            <gd:textFieldRow
                    name='email'
                    value="${command.email}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.email.label'
                    bean="${command}"
                    labelCodeDefault='Email'/>

            <gd:textFieldRow
                    name='phoneNumber'
                    value="${command.phoneNumber}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.phone.label'
                    bean="${command}"
                    labelCodeDefault='Phone Number'/>



            <gd:textFieldRow
                    name='reasonToContact'
                    value="${command.reasonToContact}"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    class="text"
                    labelCode='user.reasonToContact.label'
                    bean="${command}"
                    labelCodeDefault='Reason To Contact'/>



            <gd:submitButtonRow
                    name="sumbit"
                    labelSpan="${labelSpan}"
                    fieldSpan="${fieldSpan}"
                    value="Submit"/>
        </div>

    </g:form>

</div>
</body>
</html>