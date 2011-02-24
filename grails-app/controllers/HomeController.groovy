

import grails.plugins.springsecurity.Secured


class HomeController {

  def springSecurityService;

  @Secured(['ROLE_ADMIN'])
  def admins = { render 'Logged in with ROLE_ADMIN' }

  @Secured(['ROLE_USER'])
  def users = { render 'Logged in with ROLE_USER' }

  def index = {

    if (!springSecurityService.isLoggedIn()) {
      redirect(controller: 'login', action: 'auth');

    } else {
      def user = springSecurityService.currentUser;
      render "Welcome ${user.username} ! : ${user.areaCode} -> ${user.getAuthorities()}";
    }

  }
}
