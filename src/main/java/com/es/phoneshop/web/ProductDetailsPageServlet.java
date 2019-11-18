package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.CannotParseToIntException;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.exceptions.ValueBelowOrEqualsZeroException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.recentlyViewed.RecentlyViewed;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;

    private static final String NOT_ENOUGH_STOCK = "Not enough stock. Available stock : ";
    private static final String VALUE_BELOW_OR_EQUALS_ZERO = "Invalid input. You're entered invalid quantity : ";
    private static final String CANNOT_PARSE_TO_INT = "Invalid input. Not a number : ";

    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = CartService.getInstance();
        recentlyViewedService = RecentlyViewedService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productService.getProduct(getId(request));
            request.setAttribute("product", product);
            request.getSession().setAttribute("recentlyViewed", recentlyViewedService.processRequest(request, product));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = null;
        Product product = null;
        Cart cart = null;

        try {
            product = productService.getProduct(getId(request));
            cart = cartService.processRequest(request, product);
            request.getSession().setAttribute("cart", cart);
        } catch (ProductNotFoundException e) {
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        } catch (NotEnoughStockException e) {
            errorMessage = NOT_ENOUGH_STOCK + product.getStock();
        } catch (ValueBelowOrEqualsZeroException e) {
            errorMessage = VALUE_BELOW_OR_EQUALS_ZERO + request.getParameter("quantity");
        } catch (CannotParseToIntException e) {
            errorMessage = CANNOT_PARSE_TO_INT + request.getParameter("quantity");
        }

        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            request.setAttribute("cart", cart);
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private String getId(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    }

    void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setRecentlyViewedService(RecentlyViewedService recentlyViewedService) {
        this.recentlyViewedService = recentlyViewedService;
    }
}
