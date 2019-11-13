package com.es.phoneshop.model.productService;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ArrayListProductDao productDao;

    private ProductService productService;

    private String query = "1L";
    private Product product = new Product();
    private Optional<Product> optionalProduct = Optional.of(product);
    private List<Product> products = Collections.singletonList(new Product());

    @Before
    public void setup() {
        productService = ProductService.getInstance();
        ProductService.setProductDao(productDao);

        when(productDao.findProducts()).thenReturn(products);
        when(productDao.findProducts(query)).thenReturn(products);
        when(productDao.findProductsByDescriptionAndSortedByPrice(query, true)).thenReturn(products);
        when(productDao.findProductsByDescriptionAndSortedByPrice(query, false)).thenReturn(products);
        when(productDao.findProductsByDescriptionAndSortedByDescription(query, true)).thenReturn(products);
        when(productDao.findProductsByDescriptionAndSortedByDescription(query, false)).thenReturn(products);
        when(productDao.getProduct(query)).thenReturn(optionalProduct);
    }

    @Test
    public void getProductWhenGetProduct() throws ProductNotFoundException {
        assertEquals(product, productService.getProduct(query));
    }

    @Test(expected = ProductNotFoundException.class)
    public void getErrorWhenGetNoneExistingProduct() throws ProductNotFoundException {
        productService.getProduct(null);
    }

    @Test
    public void getAllProductsWithNonNullPriceAndUpToZeroStockWhenFindProductsWithNullQueryString() {
        assertEquals(products, productService.getAllProducts(null));
    }

    @Test
    public void getAllProductsWithNonNullPriceAndUpToZeroStockWhenFindProductsWithNonNullQueryString() {
        assertEquals(products, productService.getAllProducts(query));
    }

    @Test
    public void getAllProductsWithNonNullPriceAndUpToZeroStockWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(null, null, null));
    }

    @Test
    public void getProductsByDescriptionWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(query, null, null));
    }

    @Test
    public void getProductsByDescriptionAndSortedByPriceInAscOrderWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(query, "price", "asc"));
    }

    @Test
    public void getProductsByDescriptionAndSortedByPriceInDescOrderWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(query, "price", "desc"));
    }

    @Test
    public void getProductsByDescriptionAndSortedByDescriptionInAscOrderWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(query, "description", "asc"));
    }

    @Test
    public void getProductsByDescriptionAndSortedByDescriptionInDescOrderWhenProcessQueryString() {
        assertEquals(products, productService.processQueryString(query, "description", "desc"));
    }
}
