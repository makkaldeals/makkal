<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Mar 8, 2011
  Time: 1:21:55 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name='layout' content='main'/>
    <title>Grepdeals customer</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css/yui', file: 'tabView.css')}" />
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css/yui', file: 'tabview-core.css')}" />

      <resource:tabView skin="custom"/>
    
  </head>
  <body>
          <g:if test="${confirmationMessage}">
            <hr class="space"/>

            <div class="error">
                <h4>${confirmationMessage}</h4>
            </div>
        </g:if>
  
 <br/>
        <div class="span-2 prepand-1">
        	&nbsp;
        </div>

        <div class="span-4">
 
		    <sec:access expression="hasRole('ROLE_CUSTOMER')">
		        <g:link controller="posts" action="createPost"><g:message code="post.create.link" default="Create Post"></g:message></g:link>
		    </sec:access>
		</div>
        <div class="span-12">
		  <g:each var="post" status="i" in="${posts}">
		  		<richui:tabView id="tabView${i}">
                    <richui:tabLabels>
                   
                        <richui:tabLabel selected="true" title="${post.title}" />
                        <richui:tabLabel title="Details"/>
                        
                    </richui:tabLabels>
 					<richui:tabContents>
                        <richui:tabContent>
							  <p>${post.subject}</p>
							  <g:if test="${post.author.id == session.user.id}">
                                  <g:link controller="posts" action="editPost" params="[id:post.id]">
                                      <g:message code="post.edit.link" default="Edit"></g:message>
                                  </g:link>
                                  &nbsp;&nbsp;&nbsp;&nbsp;
                                  <g:link controller="posts" action="deletePost" params="[id:post.id]">
                                      <g:message code="post.delete.link" default="Delete"></g:message>
                                  </g:link>

                              </g:if>
                        </richui:tabContent>
                        
                        <richui:tabContent>
							    <!-- Show edit link only if he is author of post -->
							    
							
							  <p>${post.content}</p>
							  <p>
							    Posted by
							      <g:link mapping="postsByAuthor" params="[author:post.author.email]">${post.author.firstName} ${post.author.lastName}</g:link>
							
							    at
							
							      <g:link mapping="postsByBusiness" params="[business:post.author.business.name]">${post.author.business.name}</g:link>
							
							    on
							    <g:formatDate date="${post.dateCreated}" format="MMM dd, yyyy hh:mm a"/>
							    <br>
							    Tags :
							    <g:each status="j" var="tag" in="${post.tags}">
							      <g:link mapping="postsByTag" params="[tag:tag]">${tag}</g:link><g:if test="${j<post.tags.size()-1}">,</g:if>
							    </g:each>
							    <br>
							    Expires On : ${post.expiresOn} 
							  </p>
						
						</richui:tabContent>
						
                    </richui:tabContents>
	  			</richui:tabView>	
		  </g:each>
		</div>
  <br/>

  <div>
  <g:paginate controller="posts" action="${params.action}" params="${params}" total="${totalCount}"/>

  </div>

  </body>
</html>