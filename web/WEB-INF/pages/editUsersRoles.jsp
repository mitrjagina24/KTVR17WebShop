<%-- 
    Document   : editUsersRoles
    Created on : Dec 17, 2018, 3:54:28 PM
    Author     : Anastasia
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Назначение ролей</title>
        <style>
        </style>
    </head>
    <body>
            <h3  style='margin-left: 100px;color:red;'>Назначение ролей</h3>
        <form action="addUserRole" method="POST">
            <select name="user">
                <c:forEach var="entry" items="${mapUsers}">
                    <option  value="${entry.key.id}">${entry.key.login}, роль: ${entry.value}</option>
                </c:forEach>

            </select>
            <select  name="role">
                <c:forEach var="role" items="${listRoles}">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>

            </select>
            <input type="submit" value="Назначить">
        </form>
        
    </body>
</html>
