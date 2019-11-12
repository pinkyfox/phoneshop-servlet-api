package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(String id); // +
    List<Product> findProducts(); // +
    List<Product> findProducts(String query); // +
    List<Product> findProductsByDescriptionAndSortedByPrice(String query, boolean ascendingOrder);
    List<Product> findProductsByDescriptionAndSortedByDescription(String query, boolean ascendingOrder);
    void save(Product product); // +
    void delete(String id); // +
}
