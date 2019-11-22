package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewed {

    private List<Product> recentlyViewedList;

    public RecentlyViewed() {
        recentlyViewedList = new ArrayList<>();
    }

    public void add(Product product) {
        recentlyViewedList.add(0, product);
    }

    public void remove(int index) {
        recentlyViewedList.remove(index);
    }

    public void remove(Product product) {
        recentlyViewedList.remove(product);
    }

    public int size() {
        return recentlyViewedList.size();
    }

    public boolean contains(Product product) {
        return recentlyViewedList.contains(product);
    }

    public List<Product> getRecentlyViewedList() {
        return recentlyViewedList;
    }

    public void setRecentlyViewedList(List<Product> recentlyViewedList) {
        this.recentlyViewedList = recentlyViewedList;
    }
}
