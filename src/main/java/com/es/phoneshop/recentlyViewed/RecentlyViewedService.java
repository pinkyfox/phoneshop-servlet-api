package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RecentlyViewedService {
    private static RecentlyViewedService recentlyViewedService = null;
    private static final int MAX_SIZE_OF_RECENTLY_VIEWED_ITEMS = 3;

    private RecentlyViewedService() {
    }

    public static RecentlyViewedService getInstance() {
        if (recentlyViewedService == null) {
            recentlyViewedService = new RecentlyViewedService();
        }
        return recentlyViewedService;
    }

    private void add(RecentlyViewed recentlyViewed, Product product) {
        if (recentlyViewed.contains(product)) {
            recentlyViewed.remove(product);
        }  else if (recentlyViewed.size() == MAX_SIZE_OF_RECENTLY_VIEWED_ITEMS) {
            recentlyViewed.remove(MAX_SIZE_OF_RECENTLY_VIEWED_ITEMS - 1);
        }
        recentlyViewed.add(product);
    }

    public RecentlyViewed processRequest(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        RecentlyViewed recentlyViewed = (RecentlyViewed) session.getAttribute("recentlyViewed");
        if (recentlyViewed == null) {
            recentlyViewed = new RecentlyViewed();
        }
        add(recentlyViewed, product);
        return recentlyViewed;
    }
}
