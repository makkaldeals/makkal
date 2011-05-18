package com.grepdeals

import grails.plugins.springsecurity.Secured;

class SettingsController {

    static navigation = true;

	def index = {

      redirect action:'showSettings';
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED'])
    def showSettings = {
      render view:'settings';
    }
}
