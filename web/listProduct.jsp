<%-- 
    Document   : listProduct
    Created on : Nov 1, 2018, 11:12:49 AM
    Author     : pupil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список продуктов</title>
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
        <div>
        <h1>Список продуктов:</h1><br>
        <c:forEach var="product" items="${listProducts}" varStatus="count">
            <ul>
                <li>${count.index+1}. ${product.name} ${product.price} euro, ${product.count} tk. </li>
                
            </ul>
        </c:forEach>
        </div>
</body>
</html>
