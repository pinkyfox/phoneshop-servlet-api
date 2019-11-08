package com.es.phoneshop.model.productService;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest {
    private ProductService productService;

    @Before
    public void setup() {
        productService = ProductService.getInstance();
        ArrayListProductDao productDao = ArrayListProductDao.getInstance();
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("1L", "A", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" ));
        productList.add(new Product("2L",  "C", new BigDecimal(200), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productList.add(new Product("3L",  "B", new BigDecimal(50), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        ArrayListProductDao.setArrayListProduct(productList);
        ProductService.setProductDao(productDao);
    }

    @Test
    public void sortedListByDescriptionWhenSortByDescription() {
        List<Product> result = productService.getAllProductsSortedByDescription(true);
        assertEquals("1L", result.get(0).getId());
        assertEquals("2L", result.get(2).getId());
        assertEquals("3L",  result.get(1).getId());
        result.clear();
        result = productService.getAllProductsSortedByDescription(false);
        assertEquals("1L", result.get(2).getId());
        assertEquals("2L", result.get(0).getId());
        assertEquals("3L",  result.get(1).getId());
    }

    @Test
    public void sortedListByPriceWhenSortByPrice() {
        List<Product> result = productService.getAllProductsSortedByPrice(true);
        assertEquals("1L", result.get(1).getId());
        assertEquals("2L", result.get(2).getId());
        assertEquals("3L",  result.get(0).getId());
        result.clear();
        result = productService.getAllProductsSortedByPrice(false);
        assertEquals("1L", result.get(1).getId());
        assertEquals("2L", result.get(0).getId());
        assertEquals("3L",  result.get(2).getId());
    }

    @Test
    public void listOfProductsWhenFindProducts() {
        List<Product> result = productService.findProducts("c");
        assertEquals(1, result.size());
        assertEquals("2L", result.get(0).getId());
    }

    @Test
    public void sortedListOfProductsWhenFindProducts() {
        List<Product> result = productService.findProducts( "a and b", productService.getAllProductsSortedByPrice(true));
        assertEquals(2, result.size());
        assertEquals("3L", result.get(0).getId());
        assertEquals("1L", result.get(1).getId());
    }
}
