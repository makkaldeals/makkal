<!DOCTYPE html>
<html>
<head>
  <meta name='layout' content='main'/>
  <title>Contact Us</title>
</head>
<body>

<div id="grepGroupbox" class="grep_group_form">
  <g:form action="sendContactus">
    <h1>
      Contact Us
    </h1>
    <g:if test='${confirmationMessage}'>
      <br/>
      <font color="red">${confirmationMessage}</font>
      <br/>
    </g:if>

    <div class="formbody">
      <P>Please fill the form below so that we can reach you</P>
      <table>
        <tbody>
        <s2ui:textFieldRow name='firstName' bean="${command}" value="${command.firstName}"
                size='25' labelCode='user.firstName.label'/>
        <s2ui:textFieldRow name='lastName' bean="${command}" value="${command.lastName}"
                size='25' labelCode='user.lastName.label'/>
        <s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
                size='25' labelCode='user.email.label'/>
        <s2ui:textFieldRow name='phoneNumber' bean="${command}" value="${command.phoneNumber}"
                size='25' labelCode='user.phone.label'/>
        <s2ui:textFieldRow name='reasonToContact' bean="${command}" value="${command.reasonToContact}"
                size='25' labelCode='user.reasonToContact.label'/>
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
</body>
</html>