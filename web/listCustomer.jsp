<%-- 
    Document   : listCustomer
    Created on : Dec 5, 2018, 2:45:58 PM
    Author     : pupil
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список покупателей</title>
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
        <h1>Список покупателей</h1>
        <ul>
            <c:forEach var="customer" items="${listCustomers}">
                <ul>
                <li>${customer.name} ${customer.surname}, ${customer.money} евро</li>
                </ul>
            </c:forEach>
           
        </div>
    </body>
</html>
