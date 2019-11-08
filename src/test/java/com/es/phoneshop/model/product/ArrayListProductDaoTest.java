package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;
    private List<Product> productList;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        productList = new ArrayList<>();
        productList.add(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" ));
        productList.add(new Product("2L",  "Samsung Galaxy S II", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        ArrayListProductDao.setArrayListProduct(productList);
    }

    @Test
    public void addingToDaoWhenSave() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertEquals(4, ArrayListProductDao.getArrayListProduct().size());
    }

    @Test
    public void rewritingItemWhenThisItemAlreadyExistsInDao() {
        productDao.save(new Product("1L", "Apple", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertFalse("Samsung Galaxy S".equals(ArrayListProductDao.getArrayListProduct().get(0).getDescription()));
        assertTrue("Samsung Galaxy S II".equals(ArrayListProductDao.getArrayListProduct().get(1).getDescription()));
    }

    @Test
    public void setIdAndAddingToDaoWhenSaveItemWithoutId() {
        productDao.save(new Product("Apple", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        assertNotNull(ArrayListProductDao.getArrayListProduct().get(2).getId());
    }

    @Test
    public void noExceptionsWhenDeleteNonExistentItem() {
        productDao.delete("yuyuqbfqfhuqi");
        assertEquals(2, ArrayListProductDao.getArrayListProduct().size());
    }

    @Test
    public void deleteItemsWhenDeleteExistentItem() {
        productDao.delete("2L");
        assertEquals(1, ArrayListProductDao.getArrayListProduct().size());
        assertEquals("1L", ArrayListProductDao.getArrayListProduct().get(0).getId());
    }

    @Test
    public void correctResponseWhenGetProduct() {
        Iterator<Product> iterator = ArrayListProductDao.getArrayListProduct().iterator();
        while (iterator.hasNext()) {
            Product instance = iterator.next();
            assertEquals(instance, productDao.getProduct(instance.getId()).get());
        }
    }

    @Test
    public void notEmptyListWhenFindProducts() {
        assertEquals(ArrayListProductDao.getArrayListProduct().size(), productDao.findProducts().size());
    }

    @Test
    public void emptyListWhenFindProducts() {
        productList.clear();
        productList.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productList.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        ArrayListProductDao.setArrayListProduct(productList);
        assertTrue(productDao.findProducts().isEmpty());
    }
}
