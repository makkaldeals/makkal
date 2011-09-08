email {
  from = 'do.not.reply@localhost'
  register {
    body = '''\
Hi ,<br/>
<br/>
You (or someone pretending to be you) created an account with this email address.<br/>
<br/>
If you made the request, please click <a href="$url">here</a> to finish the registration.
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>
'''
    subject = 'New Account registration with grepdeals'
  }

  forgotPassword {
    body = '''\
Hi ,<br/>
<br/>
You (or someone pretending to be you) requested that your password be reset.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <a href="$url">here</a> to reset your password.
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>

'''

    subject = 'Password Reset for grepdeals Account'
  }

  approve {
    body = '''\
Hi Admin ,<br/>
<br/>
$user.email has registered new customer account with grepdeals.
<br/>
Please verify information provided by user below and click <a href="$url">here</a> to approve his registration.
<br/>
<br/>
<table>
<tr> <td> First Name </td> <td> $user.firstName </tr>
<tr> <td> Lsst Name </td> <td> $user.lastName </tr>
<tr> <td> Business Name </td> <td> $user.business.name </tr>
<tr> <td> Category </td> <td> $user.business.category </tr>
<tr> <td> Sub Category </td> <td> $user.business.subcategory </tr>
<tr> <td> Address </td> <td> $user.business.address </tr>
<tr> <td> City </td> <td> $user.business.city </tr>
<tr> <td> State </td> <td> $user.business.state </tr>
<tr> <td> Area Code </td> <td> $user.areaCode </tr>
<tr> <td> Country </td> <td> $user.business.country </tr>
<tr> <td> Phone </td> <td> $user.phone </tr>
<tr> <td> Website </td> <td> $user.business.website </tr>
</table>
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>
'''
    subject = 'New Customer registration with grepdeals'
  }

  postConfirmation {
    body = '''\
Hi $user.firstName,<br/>
<br/>
Your post is successfully posted to grepdeals.<br/>
<br/>
If you would like to view your post, click this  <a href="$url">$url</a>.
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>
'''
    subject = 'Your post is published'
  }

  advertisement {
	  body = '''\
	  
  <br/>
  The new deal is posted on Grepdeals.com<br/>
  
    <br/>
  The deals is -- $post.title<br/>
  
     <br/> $post.subject<br/>
     
  <br/>
  If you would like to view the deal, click this  <a href="$url">$url</a>.
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>
  '''
	  subject = 'Deal from GrepDeals'
	}
  
    contactUs {
        body = '''\
Hi Admin ,<br/>
<br/>
$firstName $lastName has sent the contact information.
<br/>
<br/>
<table>
<tr> <td> First Name </td> <td> $firstName  </td> </tr>
<tr> <td> Lsst Name </td> <td> $lastName  </td> </tr>
<tr> <td> Email </td> <td> $email </tr>
<tr> <td> Phone </td> <td> $phoneNumber </td> </tr>
<tr> <td> Reason to contact </td> <td> $reasonToContact </td></tr>
</table>
<br>
_______________________________________________________________________________________________

										Unsubscribe
</br>
<br>
 To unsubscribe, login to grepdeals.com and goto settings page and remove all categories assigned to you,
 you won't receive an email from us.
_______________________________________________________________________________________________ 
</br>

'''
        subject = 'User submitted contact information'
    }

}

