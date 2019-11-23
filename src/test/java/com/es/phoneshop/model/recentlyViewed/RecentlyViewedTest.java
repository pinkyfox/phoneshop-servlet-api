package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.recentlyViewed.RecentlyViewed;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RecentlyViewedTest {
    private RecentlyViewed recentlyViewed;
    private List<Product> recentlyViewedList;

    @Before
    public void setup() {
        recentlyViewed = new RecentlyViewed();
        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" ));
        recentlyViewed.setRecentlyViewedList(recentlyViewedList);
    }

    @Test
    public void addingToRecentlyViewedListWhenAddNewItem() {
        Product product = new Product("1", "Samsung Galaxy S1", new BigDecimal(23), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        recentlyViewed.add(product);

        List<Product> result = recentlyViewed.getRecentlyViewedList();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("1L", result.get(1).getId());
    }

    @Test
    public void resetPositionOfRecentlyViewedProductWhenAddAlreadyExistItem() {
        Product product = new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );

        recentlyViewed.add(product);

        List<Product> result = recentlyViewed.getRecentlyViewedList();
        assertEquals(1, result.size());
        assertEquals("1L", result.get(0).getId());
    }

    @Test
    public void onlyThreeRecentlyViewedProductsWhenAddThreeItems() {
        Product product1 = new Product("1", "Samsung Galaxy S1", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );
        Product product2 = new Product("2", "Samsung Galaxy S2", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );
        Product product3 = new Product("3", "Samsung Galaxy S3", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );

        recentlyViewed.add(product1);
        recentlyViewed.add(product2);
        recentlyViewed.add(product3);

        List<Product> result = recentlyViewed.getRecentlyViewedList();
        assertEquals(3, result.size());
        assertEquals("3", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        assertEquals("1", result.get(2).getId());
    }
}
