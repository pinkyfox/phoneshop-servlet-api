package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> arrayListProductDao;
    private Long size = 0L;

    public ArrayListProductDao() {
        arrayListProductDao = new ArrayList<>();
    }

    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        return Optional.of(arrayListProductDao.stream()
                .filter((s) -> s.getId().equals(id) && s.getStock() > 0 && s.getPrice() != null)
                .findFirst()
                .get()
        );
    }

    @Override
    public synchronized List<Product> findProducts() {
        List<Product> result = arrayListProductDao.stream()
                .filter((s) -> s.getStock() > 0 && s.getPrice() != null)
                .collect(Collectors.toList());
        return result.size() == 0 ? null : result;

    }

    @Override
    public synchronized void save(Product product) {
        product.setId(size);
        arrayListProductDao.add(product);
        size++;
    }

    @Override
    public synchronized void delete(Long id) {
        Optional<Product> item = arrayListProductDao.stream()
                .filter((s) -> id.compareTo(s.getId()) == 0)
                .findFirst();
        if (item.isPresent()){
            arrayListProductDao.remove(item.get());
            size--;
        }
    }

    @Override
    public Long getSize(){
        return size;
    }
}
