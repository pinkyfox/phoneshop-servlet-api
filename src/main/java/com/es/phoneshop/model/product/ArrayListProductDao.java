package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.Currency;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> arrayListProduct;

    public ArrayListProductDao() {
        arrayListProduct = new ArrayList<>();
        sampleInit();
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

    private void sampleInit() {
        Currency usd = Currency.getInstance("USD");
        arrayListProduct.add(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        arrayListProduct.add(new Product("2L",  "Samsung Galaxy S II", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        arrayListProduct.add(new Product("3L",  "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        arrayListProduct.add(new Product("4L",  "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        arrayListProduct.add(new Product("5L",  "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        arrayListProduct.add(new Product("6L",  "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        arrayListProduct.add(new Product("7L",  "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        arrayListProduct.add(new Product("8L",  "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        arrayListProduct.add(new Product("9L",  "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        arrayListProduct.add(new Product("10L", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        arrayListProduct.add(new Product("11L", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        arrayListProduct.add(new Product("12L", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        arrayListProduct.add(new Product("13L", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    private boolean isExist(Product product) {
        return arrayListProduct.stream()
                .anyMatch(currentProduct -> product.getId().equals(currentProduct.getId()));
    }

    private int getIndexOf(Product product) {
        return arrayListProduct.indexOf(getProduct(product.getId()).get());
    }
}
