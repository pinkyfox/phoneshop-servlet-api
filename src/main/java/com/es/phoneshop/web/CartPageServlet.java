package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.CartItemNotFoundException;
import com.es.phoneshop.exceptions.InvalidQuantityException;
import com.es.phoneshop.exceptions.NotANumberException;
import com.es.phoneshop.exceptions.NotEnoughStockException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;
    private final static String VALID = "valid";

    @Override
    public void init() {
        cartService = CartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("updateInfo", new HashMap<String, String>());
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<String,String> updateInfoMap = new HashMap<>();
        Map<String, String> cartUpdateValues = parseToMapCartUpdateValues(request.getParameter("cartUpdateValues"));
        for (String productId : cartUpdateValues.keySet()) {
            String newQuantity = cartUpdateValues.get(productId);
            try {
                cartService.updateCartItem(session, productId, newQuantity);
                updateInfoMap.put(productId, VALID);
            } catch (CartItemNotFoundException e) {
                continue;
            } catch (NotEnoughStockException e) {
                updateInfoMap.put(productId, e.getMessage());
            } catch (NotANumberException e) {
                updateInfoMap.put(productId, e.getMessage());
            } catch (InvalidQuantityException e) {
                updateInfoMap.put(productId, e.getMessage());
            }
        }
        request.setAttribute("updateInfo", updateInfoMap);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    private Map<String, String> parseToMapCartUpdateValues(String cartUpdateValues) {
        Gson gson = new Gson();
        Type empMapType = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(cartUpdateValues, empMapType);
    }
}
