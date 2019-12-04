<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:master pageTitle="Product List">
    <p>Welcome to Expert-Soft training!</p>
    <c:choose>
        <c:when test="${isDeleteSuccessfully == true}">
            <p class="deleteLabel">
            <h4 class="deleteLabel">
                <label class="deleteLabel" style="background-color: #c3ffd7; color: green">
                    Product successfully removed!
                </label>
            </h4>
            </p>
        </c:when>
        <c:when test="${isDeleteSuccessfully == false}">
            <p class="deleteLabel">
            <h4 class="deleteLabel">
                <label class="deleteLabel" style="background-color: #ff73a6; color: red">
                    Product doesn't exist!
                </label>
            </h4>
            </p>
        </c:when>
    </c:choose>
    <c:if test="${cart.cartItemList.size() != 0}">
        <table id = "productTable">
            <tr>
                <td style="text-align: center">Image</td>
                <td style="text-align: center">Description</td>
                <td style="text-align: center" class="price">Price</td>
                <td style="text-align: center">Quantity</td>
            </tr>
            <c:set var="cartList" value="${cart.cartItemList}"/>
            <c:forEach var="cartItem" items="${cartList}">
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
                        <input class="quantity" name="finalQuantity" value="${qty}" style="display: inline">
                        <button class = "deleteBtn" style="background-color: #ff73a6; color: black;" value="${product.id}">Delete</button>
                        <c:if test="${updateInfo != null && (updateInfo.get(product.id) eq 'valid') == false}">
                            <br/><label style="color: red; display: inline">${updateInfo.get(product.id)}</label>
                        </c:if>
                        <br/><label style="text-align: right; display: inline">Summary:
                            <ftm:formatNumber value="${product.price.multiply(qty)}" type="currency" currencySymbol="${product.currency.symbol}"/>
                        </label>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button id="js-submitButton" style="display: inline; align-self: center">Update cart</button>
        <form action="http://localhost:8080/phoneshop-servlet-api/checkout" method="get">
            <input type="submit" value="Checkout" id="js-checkout" style="display: inline"/>
        </form>
        <label style="text-align: center; font-weight: bold">
            <h2>
                Total price :
                <fmt:formatNumber value="${cart.totalPrice}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </h2>
        </label>
    </c:if>
    <c:if test="${cart.cartItemList.size() == 0}">
        <label>Your cart is empty. <a href="products">Let's shopping now!</a></label>
    </c:if>
    <script>
        <%@ include file="/WEB-INF/ajax/updateCart.js"%>
        <%@ include file="/WEB-INF/ajax/deleteCart.js"%>
    </script>
</tags:master>