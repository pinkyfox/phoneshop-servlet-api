package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RecentlyViewed {
    private static final int MAX_SIZE = 3;

    private List<Product> recentlyViewedList = null;

    public RecentlyViewed() {
        recentlyViewedList = new ArrayList<>();
    }

    public void add(Product product) {
        if (recentlyViewedList.contains(product)) {
            recentlyViewedList.remove(product);
        } else if (recentlyViewedList.size() == MAX_SIZE) {
            recentlyViewedList.remove(2);
        }
        recentlyViewedList.add(0, product);
    }

    public void remove(Product product) {
        recentlyViewedList.remove(product);
    }


    public List<Product> getRecentlyViewedList() {
        return recentlyViewedList;
    }

    public void setRecentlyViewedList(List<Product> recentlyViewedList) {
        this.recentlyViewedList = recentlyViewedList;
    }

    public boolean contains(Product product) {
        return recentlyViewedList.contains(product);
    }

}
