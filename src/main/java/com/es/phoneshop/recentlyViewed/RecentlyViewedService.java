package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedService {
    private RecentlyViewed recentlyViewed = null;
    private static RecentlyViewedService recentlyViewedService = null;

    private RecentlyViewedService() {
    }

    public static RecentlyViewedService getInstance() {
        if (recentlyViewedService == null) {
            recentlyViewedService = new RecentlyViewedService();
        }
        return recentlyViewedService;
    }

    private void add(Product product) {
       recentlyViewed.add(product);
    }

    public List<Product> processRequest(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        List<Product> recentlyViewedList = (List<Product>) session.getAttribute("recentlyViewed");
        if (recentlyViewedList == null) {
            recentlyViewedList = new ArrayList<>();
            recentlyViewed = new RecentlyViewed();
        }
        recentlyViewed.setRecentlyViewedList(recentlyViewedList);
        add(product);
        return recentlyViewed.getRecentlyViewedList();
    }
}
