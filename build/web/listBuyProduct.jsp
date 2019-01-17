<%-- 
    Document   : listPaid
    Created on : Dec 5, 2018, 2:50:06 PM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>


            h1{
                margin-top: 40px;
                text-align: center;

            }
            div{
                margin-right: 35%;
                margin-top: 2%;
                margin-left: 35%;
                background-color: lavender;
                padding: 1%;
            } 
            div ul{
                   text-align: justify;
                   font-size: 1em;
                    
            }
            </style>
    </head>
    <body>
        <h1>Список оплаченных товаров</h1>
        ${info}
        <form action="returnProduct" method="POST">
            <ul>
           <c:forEach var="purchase" items="${buyProducts}">
                    <li>Товар <b>"${purchase.product.name}"</b>по цене ${purchase.product.price} euro   - купил   <b>
                                <%--  ${purchase.customer.name} ${purchase.customer.surname}--%> ; </b>
                         <a href="returnProduct?purchaseId=${purchase.id}"></a></li> 
            </c:forEach>
            </ul>
        </form>
    </body>
</html>
