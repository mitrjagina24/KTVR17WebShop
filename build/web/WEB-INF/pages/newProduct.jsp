<%-- 
    Document   : newProduct
    Created on : Dec 5, 2018, 2:04:43 PM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новый товар </title>
         <style>


            h1{
                margin-top: 40px;
                text-align: center;

            }
            div{
                margin-right: 20%;
                margin-top: 2%;
                margin-left: 20%;
                background-color: lavender;
                padding: 2%;
            }
        </style>
    </head>
    <body>
        <div>
     <h1>Добавляем товар </h1>
        <form action="addProduct" method="POST" name="form1" id="_form1">
             Название:<br>
            <input type="text" name="name"><br><br>
             Цена товара:<br>
            <input type="text" name="price"><br><br>
             Количество товаров:<br>
            <input type="text" name="count"><br><br>
            <br>
            <input type="submit" value="Добавить">
        </form><br><br>
        <div>
    </body>
</html>
