security {
	ui {
		register {
			emailBody = '''\
Hi ,<br/>
<br/>
You (or someone pretending to be you) created an account with this email address.<br/>
<br/>
If you made the request, please click <a href="$url">here</a> to finish the registration.
'''
			emailFrom = 'do.not.reply@localhost'
			emailSubject = 'New Account'
            //todo remove
			defaultRoleNames = ['ROLE_CLIENT']
			postRegisterUrl = null // use defaultTargetUrl if not set
		}

		forgotPassword {
			emailBody = '''\
Hi ,<br/>
<br/>
You (or someone pretending to be you) requested that your password be reset.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <a href="$url">here</a> to reset your password.
'''
			emailFrom = 'do.not.reply@localhost'
			emailSubject = 'Password Reset'
			postResetUrl = null // use defaultTargetUrl if not set
		}
	}
}
