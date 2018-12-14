<%-- 
    Document   : customer
    Created on : Dec 5, 2018, 1:58:59 PM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Покупатель</title>
    </head>
    <body>
         <h1>Добавляем нового покупателя</h1>
        <form action="addCustomer" method="POST" name="form1" id="_form1">
             Имя:<br>
            <input type="text" name="name"><br><br>
             Фамилия:<br>
            <input type="text" name="surname"><br><br>
             Деньги:<br>
            <input type="text" name="money"><br>
            <br>
            <input type="submit" value="Добавить">
        </form><br><br>
    </body>
</html>
