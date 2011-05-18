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
    <meta name='layout' content='register'/>
    <title>Grepdeals client</title>
    <resource:tabView/>
    <resource:tabView skin="default"/>

</head>
<body>

Welcome ${session.user.email} ! , Area code : ${session.user.areaCode}  , Role : ${session.user.getAuthorities()}

<g:each var="post" status="i" in="${posts}">
    <div>
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

    </div>

</g:each>

</body>
</html>