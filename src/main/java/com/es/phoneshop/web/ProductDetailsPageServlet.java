package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.validator.Validator;
import com.es.phoneshop.validator.implementation.ProductQuantityValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;


    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = CartService.getInstance();
        recentlyViewedService = RecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productService.getProduct(getId(request));
            request.setAttribute("product", product);
            request.setAttribute("isOutOfStockForUser", productService.isOutOfStockForUser(request, product));
            request.getSession().setAttribute("recentlyViewed", recentlyViewedService.processRequest(request, product));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, List<String>> errorMap = new HashMap<>();

        Validator<String, String> validator = new ProductQuantityValidator();
        validator.validate(request, errorMap);

        Cart cart = null;
        Product product = null;

        if (errorMap.isEmpty()) {
            try {
                product = productService.getProduct(getId(request));
                cart = cartService.addProductToUserSessionCart(request.getSession(), product, Integer.valueOf(request.getParameter("quantity")));
                request.getSession().setAttribute("cart", cart);
            } catch (ProductNotFoundException e) {
                request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
            } catch (NotEnoughStockException e) {
                validator.addErrorMessage(errorMap, "Out of stock. Available : " + e.getMessage());
            }
        }

        if (!errorMap.isEmpty()){
            request.setAttribute("error", errorMap.get("quantity").get(0));
            request.setAttribute("cart", cart);
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setRecentlyViewedService(RecentlyViewedService recentlyViewedService) {
        this.recentlyViewedService = recentlyViewedService;
    }

    private String getId(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    }
}
