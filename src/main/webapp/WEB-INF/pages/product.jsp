<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<c:set var = "stock" value = "${product.stock}" scope = "request"/>
<tags:master pageTitle="Product details">
  <p>${product.description}</p>
  <body>
     <table>
        <tr>
           <td>Image</td>
           <td>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
           </td>
        </tr>
        <tr>
           <td>${product.description}</td>
           <td class="price">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
           </td>
        </tr>
     </table>
     <div class = "stockBox">
        <script>
           <%@include file="/WEB-INF/ajax/createSelectForm.js"%>
        </script>
        <c:if test = "${stock > 0}">
           <h3 style = "color : green; font-weight : bold">In Stock</h3>
           <form name = "stockForm" method = "post">
              <label style = "font-weight : bold; border : 1 px; border-color : black">Qty :
                 <select id = "stockOptions" name = "stickList" style = "border : none; width : 10 px" autofocus>
                 </select>
              </label>
              <input type="button" value="Add to cart" id="submit"/>
           </form>
        </c:if>
        <c:if test = "${stock <= 0}">
           <h3 style = "color : red; font-weight : bold">Out of Stock</h3>
        </c:if>
     </div>
  </body>
</tags:master>