class UrlMappings {

  static mappings = {


    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }

    "/" {
      controller = 'client'
      action = 'index'
    }

    "/customer" {
      controller = 'customer'
      action = 'index'
    }

    "/customer/tagged/$tag"(controller:"customer", action:"postsByTag")

    name postsByBusiness : "/$business"(controller:"customer", action:"postsByBusiness")

    "/customer/$author?/$year?/$month?/$day?"(controller:"customer", action:"showPosts") {
            constraints {
                year matches:/\d{4}/
                month matches:/\d{2}/
                day matches:/\d{2}/
            }
     }

    "500"(view: '/error')
  }
}
