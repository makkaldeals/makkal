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
      String imgUrl;
      String ckeditorCallBack;
      try {

        imgUrl = mediaService.uploadImage(params.upload);
        ckeditorCallBack = "window.parent.CKEDITOR.tools.callFunction('${params.CKEditorFuncNum}', '${imgUrl}')";

      }catch(Exception e){
        ckeditorCallBack = "window.parent.CKEDITOR.tools.callFunction('${params.CKEditorFuncNum}', '${imgUrl}', '${e.getMessage()}')";
      }

      render """<script type='text/javascript' language='javascript'>
                  ${ckeditorCallBack}
              </script>""" ;
    }

  
}
