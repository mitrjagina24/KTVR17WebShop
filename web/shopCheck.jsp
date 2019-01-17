<%-- 
    Document   : shoplibrary
    Created on : Nov 1, 2018, 10:54:41 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <h1>Чек</h1>
        <form action="takeProduct" method="POST" neme="form">
            <h2>Список продуктов</h2>
            <select name="selectedProduct">
                <c:forEach var="product" items="${listProduct}">
                    <option value="${product.id}">${product.nameProduct} ${product.price}</option>
                </c:forEach>
            </select>
                <h2>Список покупателей</h2>
            <select name="selectedCustomer">
                <c:forEach var="customer" items="${listCustomer}">
                    <option value="${customer.id}">${customer.name} ${customer.surname}</option>
                </c:forEach>
            </select>
                <button type="submit" name="takeProduct" value="Выдать книгу">
        </form>
    </body>
</html>
