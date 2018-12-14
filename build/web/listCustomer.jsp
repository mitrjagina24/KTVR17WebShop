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
    </head>
    <body>
        <h1>Список покупателей</h1>
        <ul>
            <c:forEach var="customer" items="${listCustomers}">
                <ul>
                <li>${customer.name} ${customer.surname}, ${customer.money} евро</li>
                </ul>
            </c:forEach>
      
    </body>
</html>
