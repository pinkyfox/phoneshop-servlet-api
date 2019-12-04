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
     <c:if test="${product.review.size() != 0}">
         <p>
         <table>
             <tr>
                 <td style="text-align: center">
                     <b>Username:</b>
                 </td>
                 <td style="text-align: center">
                     <b>Review:</b>
                 </td>
                 <td style="text-align: center">
                     <b>Mark (maximum 5):</b>
                 </td>
             </tr>
             <c:forEach var="review" items="${product.review}">
                 <tr>
                     <td>
                         <b>${review.username}</b>
                     </td>
                     <td>
                         <i>${review.comment}</i>
                     </td>
                     <td>
                         <i><b>${review.stars}</b></i>
                     </td>
                 </tr>
             </c:forEach>
         </table>
         </p>
     </c:if>
     <p>Leave comment!</p>
     <form method="get">
         <label style="display: inline">Name</label>
         <input name="username" type="text" required style="display: inline"/>
         <br/><label style="display: inline">Comment</label>
         <textarea cols="50" rows="5" name="comment" placeholder="Write your comment!" style="display: inline" required></textarea>
         <br/><label style="display: inline">Mark</label>
         <select name="star">
             <c:forEach var="num" begin="1" end="5" step="1">
                 <option value="${num}">${num}</option>
             </c:forEach>
         </select>
         <br/><input type="submit" value="Place Review"/>
     </form>
  </body>
</tags:master>