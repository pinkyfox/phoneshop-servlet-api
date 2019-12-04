<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:master pageTitle="Product List">
    <p>Welcome to Expert-Soft training!</p>
    <table id = "productTable">
        <tr>
            <td style="text-align: center">Image</td>
            <td style="text-align: center">Description</td>
            <td style="text-align: center" class="price">Price</td>
            <td style="text-align: center">Quantity</td>
        </tr>
        <c:forEach var="cartItem" items="${cart.cartItemList}">
            <c:set var="product" value="${cartItem.product}"/>
            <c:set var="qty" value="${cartItem.quantity}"/>
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href = "products/${product.id}">${product.description}</a>
                    <label class="productId" style="display: none">${product.id}</label>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
                <td>
                    <label style="display: inline">${qty}</label>
                    <br/><label style="text-align: right; display: inline">Summary:
                    <ftm:formatNumber value="${product.price.multiply(qty)}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </label>
                </td>
            </tr>
        </c:forEach>
    </table>
    <label style="text-align: center; font-weight: bold">
        <h3>
            Total price :
            <fmt:formatNumber value="${cart.totalPrice}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </h3>
    </label>
    <form method="post">
        <label style="display: inline">First Name</label>
        <input name="firstName" required style="display: inline"/>
        <br/><label style="display: inline">Last Name</label>
        <input name="lastName" required style="display: inline"/>
        <br/><label style="display: inline">Phone Number</label>
        <input name="phoneNumber" type="tel" placeholder="+357(95)123-45-67" pattern="[\+]\d{3}[\(]\d{2}[\)]\d{3}[\-]\d{2}[\-]\d{2}" required style="display: inline"/>
        <br/><label style="display: inline">Delivery Date</label>
        <input name="deliveryDate" type="date" required style="display: inline"/>
        <br/><label style="display: inline">Delivery Address</label>
        <input name="deliveryAddress" required style="display: inline"/>
        <br/><label style="display: inline">Payment Method</label>
        <select name="paymentMethod" required>
                <option value="Money">Money</option>
            <option value="Card">Credit Card</option>
        </select>
        <br/><input type="submit" value="Place Order"/>
    </form>
</tags:master>