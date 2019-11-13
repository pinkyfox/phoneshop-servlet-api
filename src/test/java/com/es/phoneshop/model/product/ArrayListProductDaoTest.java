package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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
        productDao.save(new Product("sgs", "Samsung Galaxy SS", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S P", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> result = ArrayListProductDao.getArrayListProduct();

        assertEquals(4, result.size());
        assertEquals("1LSamsung Galaxy S", result.get(0).getId() + result.get(0).getDescription());
        assertEquals("2LSamsung Galaxy S II", result.get(1).getId() + result.get(1).getDescription());
        assertEquals("sgsSamsung Galaxy SS", result.get(2).getId() + result.get(2).getDescription());
        assertEquals("sgs2Samsung Galaxy S P", result.get(3).getId() + result.get(3).getDescription());
    }

    @Test
    public void rewritingItemWhenThisItemAlreadyExistsInDao() {
        productDao.save(new Product("1L", "Apple", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertNotEquals("Samsung Galaxy S", ArrayListProductDao.getArrayListProduct().get(0).getDescription());
        assertEquals("Samsung Galaxy S II", ArrayListProductDao.getArrayListProduct().get(1).getDescription());
    }

    @Test
    public void setIdAndAddingToDaoWhenSaveItemWithoutId() {
        ArrayListProductDao.setArrayListProduct(new ArrayList<>());

        productDao.save(new Product("Apple", new BigDecimal(200), Currency.getInstance("USD"), 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertNotNull(ArrayListProductDao.getArrayListProduct().get(0).getId());
    }

    @Test
    public void noExceptionsWhenDeleteNonExistentItem() {
        productDao.delete("yuyuqbfqfhuqi");

        assertEquals(2, ArrayListProductDao.getArrayListProduct().size());
        assertEquals("1L", ArrayListProductDao.getArrayListProduct().get(0).getId());
        assertEquals("2L", ArrayListProductDao.getArrayListProduct().get(1).getId());
    }

    @Test
    public void deleteItemsWhenDeleteExistentItem() {
        productDao.delete("2L");

        assertEquals(1, ArrayListProductDao.getArrayListProduct().size());
        assertEquals("1L", ArrayListProductDao.getArrayListProduct().get(0).getId());
    }

    @Test
    public void notEmptyListWhenFindProducts() {
        List<Product> result = productDao.findProducts();

        assertEquals(2, ArrayListProductDao.getArrayListProduct().size());
        assertEquals("1L", result.get(0).getId());
        assertEquals("2L", result.get(1).getId());
    }

    @Test
    public void emptyListWhenFindProductsWithStockBelowZero() {
        productList.clear();
        productList.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productList.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        ArrayListProductDao.setArrayListProduct(productList);

        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void sortedListByDescriptionWhenSortByDescription() {
        List<Product> result = productDao.findProductsByDescriptionAndSortedByDescription("", true);

        assertEquals("1L", result.get(0).getId());
        assertEquals("2L", result.get(1).getId());

        result.clear();
        result = productDao.findProductsByDescriptionAndSortedByDescription("", false);

        assertEquals("1L", result.get(1).getId());
        assertEquals("2L", result.get(0).getId());
    }

    @Test
    public void sortedListByPriceWhenSortByPrice() {
        List<Product> result = productDao.findProductsByDescriptionAndSortedByPrice("", true);

        assertEquals("1L", result.get(0).getId());
        assertEquals("2L", result.get(1).getId());

        result.clear();
        result = productDao.findProductsByDescriptionAndSortedByPrice("", false);

        assertEquals("1L", result.get(1).getId());
        assertEquals("2L", result.get(0).getId());
    }

    @Test
    public void listOfProductsWhenFindProducts() {
        List<Product> result = productDao.findProducts("ii");

        assertEquals(1, result.size());
        assertEquals("2L", result.get(0).getId());
    }

    @Test
    public void sortedListOfProductsWhenFindProducts() {
        List<Product> result = productDao.findProductsByDescriptionAndSortedByPrice("samsung",true);

        assertEquals(2, result.size());
        assertEquals("1L", result.get(0).getId());
        assertEquals("2L", result.get(1).getId());
    }
}
