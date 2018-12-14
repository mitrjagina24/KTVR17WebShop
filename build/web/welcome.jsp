<%-- 
    Document   : welcome
    Created on : Dec 5, 2018, 1:46:41 PM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Главная</title>
    </head>
    <body>
         <h1>Навигация по сайту</h1>
        ${textToPage}<br>
        <a href="newProduct">Добавить  продукт</a><br>
        <a href="showProduct">Список продуктов</a><br>
        <a href="newCustomer">Добавить покупателя</a><br>
        <a href="showCustomer">Список покупателей</a><br>
        <a href="listAll">Покупка</a><br>

    </body>
</html>
