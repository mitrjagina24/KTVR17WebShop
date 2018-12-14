

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Список товаров и покупателей</h1>
      <form  action="buyProduct" method="POST" name="form1">
            <h2 style="font-size: 16px;color:blue;">Список товаров</h2>
            <select name="selected">
                <c:forEach var="product" items="${listProducts}">
                    <option value="${product.id}">${product.name} ${product.price}euro, ${product.count} tk.</option>
                </c:forEach>
            </select>
                <h2>Список покупателей</h2>
            <select name="selectedCustomer">
                <c:forEach var="customer" items="${listCustomer}">
                    <option value="${customer.id}">${customer.name} ${customer.surname}${customer.money}</option>
                </c:forEach>
            </select>
                <button type="submit" name="buyProduct">Купить  продукт</button>
        </form>
    </body>
</html>
