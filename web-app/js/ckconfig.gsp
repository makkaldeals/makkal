<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: May 7, 2011
  Time: 11:01:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/javascript"%>

// Add a reference to the new plugin
CKEDITOR.plugins.addExternal('MediaEmbed',
'${request.contextPath}/js/ckeditor/plugins/mediaembed/');

CKEDITOR.editorConfig = function( config )
{
    // Declare the additional plugin
    config.extraPlugins = 'MediaEmbed'; // Additional plugin

    // EXAMPLE TOOLBAR USING THE NEW PLUGIN
    config.toolbar_custom = [
        ['Templates','Styles','Format','Font','FontSize','TextColor','BGColor','Maximize'],
        ['Source', 'Preview'],
        ['Cut' , 'Copy' , 'Paste'],
        ['Bold','Italic','Underline','Strike'],
        ['Table', 'HorizontalRule', 'NumberedList','BulletedList','Outdent','Indent','Blockquote'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['Link','Image', 'MediaEmbed' , 'Flash']
    ]
};
