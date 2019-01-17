<%-- 
    Document   : editUsersRoles
    Created on : Dec 17, 2018, 3:54:28 PM
    Author     : Anastasia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Роли</title>
    </head>
    <body>
        <h3>Назначение ролей</h3>
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
