<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/priceHistoryWindow.css">
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
 <body class="product-list">
  <header>
      <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop: ${cart.cartItemList}
    </a>
  </header>
  <main>
 <jsp:doBody/>
  </main>
  <c:if test="${not empty recentlyViewed}">
      <div>
          <p>
          <h2>Recently viewed products</h2>
          </p>
          <table>
              <tr>
                  <c:forEach items="${recentlyViewed}" var="product">
                      <td align="center">
                          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                          <p>
                              <a href = "products/${product.id}">${product.description}</a>
                          </p>
                      </td>
                  </c:forEach>
              </tr>
          </table>
      </div>
  </c:if>
</body>
</html>