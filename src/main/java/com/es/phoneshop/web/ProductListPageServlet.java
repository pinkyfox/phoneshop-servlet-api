package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.google.gson.Gson;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", processQueryString(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> processQueryString(HttpServletRequest request) {
        String[] qArray = {
                request.getParameter("query"),
                request.getParameter("sort"),
        };
        List<Product> result;
        if (qArray[0] == null) {
            result = productService.getAllProducts();
        } else if (qArray[0] != null && qArray[1] == null) {
            result = productService.findProducts(qArray[0]);
        } else {
            boolean isAscOrder = request.getParameter("order").equals("asc");
            result = qArray[1].equals("price") ? productService.findProducts(
                    qArray[0], productService.getAllProductsSortedByPrice(isAscOrder)
            ) : productService.findProducts(
                    qArray[0], productService.getAllProductsSortedByDescription(isAscOrder)
            );
        }
        return result;
    }
}
