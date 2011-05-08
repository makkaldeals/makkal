/**
 *
 *
 * Created on May 7, 2011 . 11:45:46 PM
 * @Author E. Rajasekar
 *
 */
CKEDITOR.on('dialogDefinition', function(ev) {
      var dialogName = ev.data.name;
      var dialogDefinition = ev.data.definition;


      if (dialogName == 'flash') {
        dialogDefinition.removeContents('advanced');
        dialogDefinition.removeContents('properties');
        dialogDefinition.removeContents('Upload');
        var infotab = dialogDefinition.getContents('info');
        var f = dialogDefinition.onOk;
        dialogDefinition.onOk = function(ev) {
          var cur = this.getContentElement('info', 'src').getValue();
          var newurl = cur.replace('youtube.com/watch?v=', 'youtube.com/v/');
          if (cur != newurl) {
            this.getContentElement('info', 'src').setValue(newurl);
          }
          ;
          f.apply(this, [ev]);  //change here
        }
      }
      else if (dialogName == 'image') {
        dialogDefinition.removeContents('advanced');
        dialogDefinition.removeContents('Link');
        var imageInfoTab = dialogDefinition.getContents('info');
        imageInfoTab.remove('txtAlt');

      }
      else if (dialogName == 'link') {

          dialogDefinition.removeContents('upload');
          dialogDefinition.removeContents('advanced');
          var linkInfoTab = dialogDefinition.getContents('info');
          linkInfoTab.remove('browse');
          linkInfoTab.remove('linkType');

        }
    })
