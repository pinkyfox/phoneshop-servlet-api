<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
  <p>Welcome to Expert-Soft training!</p>
  <div class = "searchBox">
     <span style = "width : 160 px">
        <form id = "MyForm" onsubmit="return false;" style = "width : 158 px">
           <input id="textBox" name = "query" value = "${param.query}">
        </form>
     </span>
     <span>
        <input type="button" value="Search" id="myButton"/>
     </span>
  </div>
  <table id = "productTable">
     <thead>
        <tr>
           <td>Image</td>
           <td>Description
              <a id = "dasc" href = "/phoneshop-servlet-api/products?sort=description&order=asc&query=${param.query}" style = "${currentSort ? 'font-weight:bold' : ''}">⇑</a>
              <a id = "ddesc" href = "/phoneshop-servlet-api/products?sort=description&order=desc&query=${param.query}" style = "${currentSort ? 'font-weight:bold' : ''}">⇓</a>
           </td>
           <td class="price">Price
              <a id = "pasc" href = "/phoneshop-servlet-api/products?sort=price&order=asc&query=${param.query}" style = "${currentSort ? 'font-weight:bold' : ''}">⇑</a>
              <a id = "pdesc" href = "/phoneshop-servlet-api/products?sort=price&order=desc&query=${param.query}" style = "${currentSort ? 'font-weight:bold' : ''}">⇓</a>
           </td>
        </tr>
     </thead>
     <thead id="somediv">
        <script>
          <%@include file="/WEB-INF/ajax/SearchButtonAjaxRequest.js"%>
        </script>
        <c:forEach var="product" items="${products}">
           <tr>
              <td>
                 <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
              </td>
              <td>
                 <a href = "products/${product.id}">${product.description}</a>
              </td>
              <td class="price">
                 <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
              </td>
           </tr>
        </c:forEach>
     </thead>
  </table>
</tags:master>