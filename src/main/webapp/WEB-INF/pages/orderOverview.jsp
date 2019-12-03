<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>

<html>
    <head>
        <title>${pageTitle}</title>
        <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/priceHistoryWindow.css">
    </head>
    <body class="product-list">
        <header>
            <a href="${pageContext.servletContext.contextPath}">
                <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
                PhoneShop
            </a>
        </header>
        <p><h3 style="color: aquamarine">Your order successfully placed</h3></p>
        <table>
            <c:forEach var="orderItem" items="${order.orderItems}">
                <c:set var="product" value="${orderItem.product}"/>
                <c:set var="qty" value="${orderItem.quantity}"/>
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>
                        <a href = "products/${product.id}">${product.description}</a>
                        <label class="productId" style="display: none">${product.id}</label>
                    </td>
                    <td class="price">
                        <ftm:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </td>
                    <td>
                        <label style="display: inline">Qty: ${qty}</label>
                        <br/><label style="text-align: right; display: inline">Summary:
                        <ftm:formatNumber value="${product.price.multiply(qty)}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </label>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <label style="display: inline; alignment: left">Subtotal Cost:
            <ftm:formatNumber value="${order.subtotal}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </label>
        <br/><label style="display: inline; alignment: left">Shipping Cost:
            <ftm:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </label>
        <br/><b><label style="display: inline; alignment: left">Total Cost:
            <ftm:formatNumber value="${order.subtotal.add(order.deliveryCost)}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </label></b>
        <p>Personal Info:</p>
        <label style="display: inline">First Name: <b>${order.firstName}</b></label>
        <br/><label style="display: inline">Last Name: <b>${order.lastName}</b></label>
        <br/><label style="display: inline">Phone Number: <b>${order.phoneNumber}</b></label>
        <br/><label style="display: inline">Delivery Date: <b>${order.date}</b></label>
        <br/><label style="display: inline">Delivery Address: <b>${order.deliveryAddress}</b></label>
        <br/><label style="display: inline">Payment Method: <b>${order.paymentMethod}</b></label>
        <br/><a href="${pageContext.servletContext.contextPath}" style="display: inline; alignment: center">Continue shopping!</a>
    </body>
</html>