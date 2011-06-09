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
    <resource:tabView skin="default"/>

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

        <div class="span-10">
            <g:each var="post" status="i" in="${posts}">
                <richui:tabView id="tabView${i}">
                    <richui:tabLabels>
                        <richui:tabLabel selected="true" title="${post.author.business.name}"/>
                        <richui:tabLabel title="Deal Details"/>

                    </richui:tabLabels>
					
                    <richui:tabContents>
                        <richui:tabContent>
                            ${post.title}
                            <br/>
                            <g:pdfLink pdfController="coupon" pdfAction="generateCoupon" pdfId="${post.id}">Print</g:pdfLink>
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


