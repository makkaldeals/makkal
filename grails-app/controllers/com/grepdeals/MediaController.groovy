package com.grepdeals

import grails.plugins.springsecurity.Secured

/**
 * com.grepdeals
 *
 * Created on Apr 11, 2011 . 11:10:46 PM
 * @Author E. Rajasekar
 *
 */
class MediaController {

    def index = { }
    def mediaService;

    @Secured(['ROLE_CUSTOMER', 'ROLE_ADMIN'])
    def uploadImage = {
      log.info("${params}");

      String imgUrl = mediaService.uploadImage(params.upload);
      render """<script type='text/javascript' language='javascript'>
                  window.parent.CKEDITOR.tools.callFunction('${params.CKEditorFuncNum}', '${imgUrl}')
              </script>""" ;
    }
}
