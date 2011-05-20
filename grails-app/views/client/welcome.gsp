<%--
  Created by IntelliJ IDEA.
  User: Rajasekar Elango
  Date: Feb 28, 2011
  Time: 5:08:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" media="screen" href="/grepdeals/css/tab-menu.css"/>

    <meta name='layout' content='register'/>
    <title>Grepdeals client</title>
    <resource:tabView skin="default"/>

</head>
<body>
<table>

    <tr>

        <div class="tabmenu">
            <h3><a href="welcome">Today's deals</a></h3>
            <h3><a href="oldPosts">Old deals</a></h3>
        </div>

        <div class="tabcontent">
            <g:each var="post" status="i" in="${posts}">
                <richui:tabView id="tabView${i}">
                    <richui:tabLabels>
                        <richui:tabLabel selected="true" title="${post.author.business.name}"/>
                        <richui:tabLabel title="Deal Details"/>

                    </richui:tabLabels>

                    <richui:tabContents>
                        <richui:tabContent>
                            ${post.title}
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
    </tr>
</table>



</body>
</html>


