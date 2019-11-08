package com.es.phoneshop.model.product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductDao productDao = ArrayListProductDao.getInstance();
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance =  new ProductService();
        }
        return instance;
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

    public List<Product> processQueryString(String query, String sort, String order) {
        List<Product> result;
        if (query == null) {
            result = getAllProducts();
        } else if (query != null && sort == null) {
            result = findProducts(query);
        } else {
            boolean isAscOrder = order.equals("asc");
            result = sort.equals("price") ?
                    findProducts(query, getAllProductsSortedByPrice(isAscOrder)) :
                    findProducts(query, getAllProductsSortedByDescription(isAscOrder));
        }
        return result;
    }

    public static List<Product> getProductDaoList() {
        return ArrayListProductDao.getArrayListProduct();
    }

    public static void setProductDao(ProductDao productDao) {
        ProductService.productDao = productDao;
    }
}
