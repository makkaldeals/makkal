class SecurityFilters {
	def filters = {
		loginCheck(controller:'*', action:'(showSettings|postsByTag|postsByBusiness|showPosts|settings|welcome|createPost)') {
			before = {
				if(!session.user) {
					redirect(controller:'login', action:'auth')
					return false
				} else {
					return true
				}
			}
		}
	}
}