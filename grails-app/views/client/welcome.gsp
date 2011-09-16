<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Feb 28, 2011
  Time: 5:08:56 PM
  To change this template use File | Settings | File Templates.
  s
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title>Grepdeals client</title> 
    <!--  
    <link rel="stylesheet" type="text/css" media="screen" href="/grepdeals/css/tabView.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/grepdeals/js/yui/tabview/assets/tabview-core.css" />
    -->
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css/yui', file: 'tabView.css')}" />
    <link rel="stylesheet" type="text/css" media="screen" href="${resource(dir: 'css/yui', file: 'tabview-core.css')}" />

      <resource:tabView skin="custom"/>
    
     
    <!-- 
    <resource:tabView skin="default"/>
    
 <resource:accordion skin="grey"/>
 -->
</head>
<body>
<br/>
        <div class="span-2 prepand-1">
        	&nbsp;
        </div>

        <div class="span-4">
        
            <a href="welcome">Today's deals</a> <br/>
            <a href="oldPosts">Old deals</a>
        </div>

        <div class="span-12">
         
            <g:each var="post" status="i" in="${posts}">
                <richui:tabView id="tabView${i}">
                    <richui:tabLabels>
                   
                        <richui:tabLabel selected="true" title="${post.author.business.name}" />
                        <richui:tabLabel title="Deal Details"/>

                    </richui:tabLabels>
					
                    <richui:tabContents>
                        <richui:tabContent>
                            ${post.title}
                            <br/>
                            <g:pdfLink pdfController="coupon" pdfAction="pdfLink" pdfId="${post.id}">
                           <!--   Print -->
                           <!--  icon is taken from the following url
                                                      http://findicons.com/icon/235462/printer?id=384231
                                         -->             
                            <img  src="/grepdeals/images/skin/printer.jpg" width="15px"/>
                            </g:pdfLink>
                        </richui:tabContent>

                        <richui:tabContent>
                            ${post.content}
                        </richui:tabContent>

                    </richui:tabContents>
                </richui:tabView>
                <br/>

            </g:each>
        </div>
</body>
</html>


