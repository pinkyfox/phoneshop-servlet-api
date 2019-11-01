package com.es.phoneshop.model.product;

import java.util.List;

public class Service {
    private static ProductDao productDao;

    public Service () {
        productDao = new ArrayListProductDao();
    }

    public List<Product> getAllProducts() {
        return productDao.findProducts();
    }

    public Product getProduct(String id) throws NoSuchElementException {
        return productDao.getProduct(id).orElseThrow(NoSuchElementException::new);
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void remove(String id) {
        productDao.delete(id);
    }
}
