package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void saveTest() {
        int counter = 0;
        String[] expectedIds = new String[] {
                "1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "sgs", "sgs2"
        };
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        for (Product product:
                productDao.findProducts()) {
            assertEquals(expectedIds[counter++], product.getId());
        }
    }

    @Test
    public void noExceptionsWhenDeleteNonExistentItem() {
        productDao.delete("yuyuqbfqfhuqi");
    }

    @Test
    public void deleteItemsWhenDeleteExistentItem() {
        int counter = 0;
        String[] expectedIds = new String[] {
          "1L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L"
        };
        productDao.delete("2L");
        productDao.delete("13L");
        for (Product product:
             productDao.findProducts()) {
            assertEquals(expectedIds[counter++], product.getId());
        }
    }

    @Test
    public void correctResponseWhenGetProduct() {
        int counter = 0;
        List<Product> result = productDao.findProducts();
        for (Product product:
                result) {
            assertEquals(productDao.getProduct(product.getId()).get(), result.get(counter++));
        }
    }

    @Test
    public void notEmptyListWhenFindProducts() {
        int counter = 0;
        String[] expectedIds = new String[] {
                "1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "3ewfL", "2fwfL"
        };
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("1L",  "Samsung Galaxy S II", null, Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("3ewfL",  "Samsung Galaxy S III", new BigDecimal(200), Currency.getInstance("USD"), 95, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("2fwfL",  "Samsung Galaxy S II", new BigDecimal(2000), Currency.getInstance("USD"), 190, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        for (Product product:
                productDao.findProducts()) {
            assertEquals(product.getId(), expectedIds[counter++]);
        }
    }

    @Test
    public void EmptyListWhenFindProducts() {
        for (Product product:
             productDao.findProducts()) {
            productDao.delete(product.getId());
        }
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("uhfeuh",  "Samsung Galaxy S II", null, Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertTrue(productDao.findProducts().isEmpty());
    }
}
