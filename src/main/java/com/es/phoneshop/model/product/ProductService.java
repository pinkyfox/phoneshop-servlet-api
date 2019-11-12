package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.List;

public class ProductService {
    private static ProductDao productDao;
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance =  new ProductService();
            productDao = ArrayListProductDao.getInstance();
        }
        return instance;
    }

    public Product getProduct(String id) throws ProductNotFoundException {
        return productDao.getProduct(id).orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getAllProducts(String description) {
        return description == null ?
                productDao.findProducts() :
                productDao.findProducts(description);
    }

    public List<Product> processQueryString(String query, String sort, String order) {
        List<Product> result;
        if (query == null) {
            result = getAllProducts(null);
        } else if (sort == null) {
            result = getAllProducts(query);
        } else {
            boolean isAscOrder = order.equals("asc");
            result = sort.equals("price") ?
                    getProductsByDescriptionAndSortedByPrice(query ,isAscOrder) :
                    getProductsByDescriptionAndSortedByDescription(query, isAscOrder);
        }
        return result;
    }

    public static void setProductDao(ProductDao productDao) {
        ProductService.productDao = productDao;
    }

    private List<Product> getProductsByDescriptionAndSortedByDescription(String query, boolean isAscOrder) {
        return productDao.findProductsByDescriptionAndSortedByDescription(query, isAscOrder);
    }

    private List<Product> getProductsByDescriptionAndSortedByPrice(String query, boolean isAscOrder) {
        return productDao.findProductsByDescriptionAndSortedByPrice(query, isAscOrder);
    }
}
