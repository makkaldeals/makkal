import com.makkaldeals.auth.Role

class UrlMappings {

  static mappings = {

   /*"/login/auth" {
      controller = 'openId'
      action = 'auth'
    }
    "/login/openIdCreateAccount" {
      controller = 'openId'
      action = 'createAccount'
    }    */
    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }

    "/" {
      controller = 'login'
      action = 'index'
      role =  Role.ROLE_CLIENT
      targetUrl = "/client/welcome"

    }

    "/customer" {
      controller = 'login'
      action = 'index'
      role =  Role.ROLE_CUSTOMER
      targetUrl = "/customer/welcome"
    }

    "500"(view: '/error')
  }
}
