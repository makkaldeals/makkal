package com.grepdeals


/**
 * com.grepdeals
 *
 * Created on Apr 11, 2011 . 11:10:46 PM
 * @Author E. Rajasekar
 *
 */
class ImageController {

    def index = { }

    def upload = {
      log.info("${params}");
      String imgUrl="https://lh6.googleusercontent.com/_WfNIF9XHe5M/TQRN24VmPvI/AAAAAAAADds/TG8zYf7c4uI/s720/IMG_0160.JPG";
      render """<script type='text/javascript' language='javascript'>
                  window.parent.CKEDITOR.tools.callFunction('${params.CKEditorFuncNum}', '${imgUrl}')
              </script>""" ;
    }
}
