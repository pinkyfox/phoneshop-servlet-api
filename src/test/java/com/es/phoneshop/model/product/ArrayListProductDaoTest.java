package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void init() {
        productDao = new ArrayListProductDao();
        ((ArrayListProductDao)productDao).sampleInit();
    }

    @Test
    public void addingToDaoWhenSave() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertNotNull(productDao.getProduct("sgs").get());
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertNotNull(productDao.getProduct("sgs2").get());
    }

    @Test
    public void noExceptionsWhenDeleteNonExistentItem() {
        productDao.delete("yuyuqbfqfhuqi");
        for (int i = 1; i < 14; i++) {
            assertEquals(i + "L", productDao.getProduct(i + "L").get().getId());
        }
    }

    @Test
    public void deleteItemsWhenDeleteExistentItem() {
        productDao.delete("2L");
        assertFalse(productDao.getProduct("2L").isPresent());
        productDao.delete("13L");
        assertFalse(productDao.getProduct("13L").isPresent());
    }

    @Test
    public void correctResponseWhenGetProduct() {
        int counter = 0;
        List<Product> result = productDao.findProducts();
        for (Product product : result) {
            assertEquals(productDao.getProduct(product.getId()).get(), result.get(counter++));
        }
    }

    @Test
    public void notEmptyListWhenFindProducts() {
        int counter = 0;
        String[] expectedIds = new String[] {
                "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L"
        };
        productDao.save(new Product("1L",  "!!!!replaced Samsung Galaxy S II", null, Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        for (Product product : productDao.findProducts()) {
            assertEquals(product.getId(), expectedIds[counter++]);
        }
    }

    @Test
    public void emptyListWhenFindProducts() {
        for (Product product : productDao.findProducts()) {
            productDao.delete(product.getId());
        }
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("uhfeuh",  "Samsung Galaxy S II", null, Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertTrue(productDao.findProducts().isEmpty());
    }
}
