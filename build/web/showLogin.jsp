<%-- 
    Document   : showLogin
    Created on : Dec 17, 2018, 3:55:02 PM
    Author     : Anastasia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Вход в систему</title>
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
                padding: 5%;
            }
            </style>
            
    </head>
    <body>
        <div>
          <h3>Для входа в систему введите ЛОГИН и ПАРОЛЬ</h3>
        ${info}<br>
        <form action="login" method="POST" name="form1" id="_form1">
             Логин:<br>
            <input type="text" name="login"><br>
            <br>
             Пароль:<br>
            <input type="password" name="password"><br>
            <br>
            <input type="submit" value="Войти">
        </form><br><br>
        <a href="newCustomer">Зарегистрироваться </a>
        
    </div>
    </body>
</html>
