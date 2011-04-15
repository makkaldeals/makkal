<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Mar 8, 2011
  Time: 2:01:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name='layout' content='register'/>
  <title>Grepdeals customer</title>
  <ckeditor:resources />
  <g:javascript>

    CKEDITOR.on('dialogDefinition', function(ev){
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition; 
        if (dialogName == 'flash'){
            var infotab = dialogDefinition.getContents('info');
            var f = dialogDefinition.onOk;
            dialogDefinition.onOk = function(ev) {
                var cur = this.getContentElement('info', 'src').getValue();
                var newurl = cur.replace('youtube.com/watch?v=', 'youtube.com/v/');
                if (cur != newurl) {
                    this.getContentElement('info', 'src').setValue(newurl);
                };
                f.apply(this, [ev]);  //change here
           }
        }
    })



  </g:javascript>
</head>


<body>

<div id="createPost">

  <h1>
    <g:if test="${post.id}">
      <g:message code="post.edit.title" default="Edit Post"></g:message>
    </g:if>
    <g:else>
      <g:message code="post.create.title" default="Create Post"></g:message>
    </g:else>
  </h1>
  <g:renderErrors bean="${post}"></g:renderErrors>
  <g:form name="createPostForm" controller="posts" action="publishPost" >
    <g:if test="${post.id}">
      <g:hiddenField name="id" value="${post.id}"/>
    </g:if>
    <p>
    <label for="post.title"><g:message code="post.title.label" default="Title:"/></label>
    <g:textField  size="50" name="post.title" value="${post.title}"/>

    </p>

    <p>
   

    <label for="post.content"><g:message code="post.content.label" default="Content:"/></label> <br>
    
    <!-- <g:textArea name="post.content" value="${post.content}" rows="5" cols="50"/>
     <richui:richTextEditor name="post.content" value="${post.content}" width="525" /> -->
    <!-- FIXME url's should not have grepdeals -->
    <ckeditor:editor name="post.content" height="400px" width="80%"  filebrowserImageBrowseUrl="/grepdeals/customer/welcome" filebrowserImageUploadUrl="/grepdeals/media/uploadImage" filebrowserUploadUrl="/grepdeals/media/uploadImage" >
    ${post.content}
    </ckeditor:editor>




    </p>
    <p>
    <label for="tags"><g:message code="post.tags.label" default="Tags:"/></label>
    <g:textField size="50" name="tags" value="${post.tags.join(',')}"/>
    </p>

    <g:submitButton name="publishPost" form="createPostForm" value="${message(code:'post.publish.button',default:'Publish')}"/>
  </g:form>
</div>
</body>
</html>