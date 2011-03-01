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

    "500"(view: '/error')
  }
}
