package com.es.phoneshop.model.product;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;
    private static List<Product> arrayListProduct = new ArrayList<>();

    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public Optional<Product> getProduct(String id) {
        return arrayListProduct.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findProducts() {
        return arrayListProduct.stream()
                .filter(product -> product.getStock() > 0 && product.getPrice() != null)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product.getId().isEmpty()) {
            product.setId(UUID.randomUUID().toString());
        } else if (isExist(product)) {
            arrayListProduct.set(getIndexOf(product), product);
        }
        if (!isExist(product)) {
            arrayListProduct.add(product);
        }
    }

    @Override
    public void delete(String id) {
        arrayListProduct.removeIf(product -> id.equals(product.getId()));
    }

    private boolean isExist(Product product) {
        return arrayListProduct.stream()
                .anyMatch(currentProduct -> product.getId().equals(currentProduct.getId()));
    }

    private int getIndexOf(Product product) {
        return arrayListProduct.indexOf(getProduct(product.getId()).get());
    }
}
