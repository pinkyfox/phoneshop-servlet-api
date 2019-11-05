package com.es.phoneshop.model.product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductDao productDao = ArrayListProductDao.getInstance();
    private static volatile ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        ProductService localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductService.class) {
                if (localInstance == null) {
                    instance = localInstance = new ProductService();
                }
            }
        }
        return localInstance;
    }

    public List<Product> getAllProducts() {
        return productDao.findProducts();
    }

    public List<Product> getAllProductsSortedByPrice(boolean ascendingOrder) {
        List<Product> result = productDao.findProducts().stream()
                .sorted(Comparator.comparing(product -> product.getPrice()))
                .collect(Collectors.toList());
        if (!ascendingOrder) {
            Collections.reverse(result);
        }
        return result;
    }

    public List<Product> getAllProductsSortedByDescription(boolean ascendingOrder) {
        List<Product> result = productDao.findProducts().stream()
                .sorted(Comparator.comparing(product -> product.getDescription()))
                .collect(Collectors.toList());
        if (!ascendingOrder) {
            Collections.reverse(result);
        }
        return result;
    }

    public List<Product> findProducts(String query) {
        return productDao.findProducts().stream()
                .filter(product -> strContains(product.getDescription(), query))
                .collect(Collectors.toList());
    }

    public List<Product> findProducts(String query, List<Product> list) {
        return list.stream()
                .filter(product -> strContains(product.getDescription(), query))
                .collect(Collectors.toList());
    }

    public Product getProduct(String id) {
        return productDao.getProduct(id).get();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void remove(String id) {
        productDao.delete(id);
    }

    private boolean strContains (String sourceStr, String comparableStr) {
        boolean isContains = false;
        String[] lexems = comparableStr.split("\\s+");
        for (String lexema: lexems) {
            isContains = sourceStr.toLowerCase().contains(lexema.toLowerCase()) || isContains;
        }
        return isContains;
    }
}
