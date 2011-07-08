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

	"/settings" {
		controller = 'settings'
		action = 'settings'
	  }

    name postsByTag : "/posts/tagged/$tag"(controller:"posts", action:"postsByTag")

    name postsByBusiness : "/$business"(controller:"posts", action:"postsByBusiness")

    name postsByBusinessArea : "/business/$business/$city?/$areaCode?"(controller:"posts", action:"postsByBusiness") {
        constraints {
                areaCode matches:/\d+/
        }
    }

    name postsByAuthor : "/posts/author/$author?/$year?/$month?/$day?"(controller:"posts", action:"showPosts") {
            constraints {
                year matches:/\d{4}/
                month matches:/\d{2}/
                day matches:/\d{2}/
            }
     }

    "500"(view: '/error')
  }
}