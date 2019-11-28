<%@page  contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
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
        <c:if test = "${isOutOfStockForUser == true}">
            <h3 style = "color : red; font-weight : bold">Out of Stock</h3>
        </c:if>
        <c:if test = "${isOutOfStockForUser == false}">
           <h3 style = "color : green; font-weight : bold">In Stock</h3>
           <form method = "post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
               <p>
                   <c:if test="${not empty error}">
                       <span style="color: red; font-weight: bold">${error}</span>
                   </c:if>
                   <c:if test="${param.success}">
                       <span style="color: green; font-weight: bold">Product was successfully added to cart</span>
                   </c:if>
               </p>
               <p>
                  <label>Qty: </label>
                  <input name="quantity" value="${param.quantity}"/>
                  <button>Add to cart</button>
               </p>
           </form>
        </c:if>
     </div>
  </body>
</tags:master>