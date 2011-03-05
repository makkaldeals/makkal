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
			emailSubject = 'New Account registration with Makkal deals'
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
			emailSubject = 'Password Reset for Makkal deals Account'
		}

      approve {
           emailBody = '''\
Hi Admin ,<br/>
<br/>
$user.email has registered new customer account with Makkal Deals.
<br/>
Please verify information provided by user below and click <a href="$url">here</a> to approve his registration.
<br/>
<br/>
<table>
<tr> <td> First Name </td> <td> $user.firstName </tr>
<tr> <td> Lsst Name </td> <td> $user.lastName </tr>
<tr> <td> Business Name </td> <td> $user.business.name </tr>
<tr> <td> Category </td> <td> $user.business.category </tr>
<tr> <td> Address </td> <td> $user.business.address </tr>
<tr> <td> City </td> <td> $user.business.city </tr>
<tr> <td> State </td> <td> $user.business.state </tr>
<tr> <td> Area Code </td> <td> $user.areaCode </tr>
<tr> <td> Country </td> <td> $user.business.country </tr>
<tr> <td> Phone </td> <td> $user.phone </tr>
<tr> <td> Website </td> <td> $user.business.website </tr>
</table>

'''
			emailFrom = 'do.not.reply@localhost'
			emailSubject = 'New Customer registration with Makkal deals'
		}
	}
}
