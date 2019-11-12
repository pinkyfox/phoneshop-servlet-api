package com.es.phoneshop.model.product;

import com.es.phoneshop.Locker.Locker;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Collections;
import java.util.Arrays;
import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;
    private static List<Product> arrayListProduct;
    private static Locker locker;

    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
            locker = new Locker();
            arrayListProduct = getSampleProductList();
        }
        return instance;
    }

    @Override
    public Optional<Product> getProduct(String id) {
        return arrayListProduct.stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Product> findProducts() {
        return arrayListProduct.stream()
                .filter(product -> product.getStock() > 0 && product.getPrice() != null)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        synchronized (locker.getLock(product.getId())) {
            if (product.getId() == null) {
                product.setId(UUID.randomUUID().toString());
            } else if (isExist(product)) {
                arrayListProduct.set(getIndexOf(product), product);
            }
            if (!isExist(product)) {
                arrayListProduct.add(product);
                locker.getLock(product.getId());
            }
        }
    }

    @Override
    public void delete(String id) {
        synchronized (locker.getLock(id)) {
            arrayListProduct.removeIf(product -> id.equals(product.getId()));
        }
    }

    @Override
    public List<Product> findProductsByDescriptionAndSortedByPrice(String query, boolean ascendingOrder) {
        return sortProducts(ascendingOrder,
                product -> strContains(product.getDescription(), query),
                Comparator.comparing(Product::getPrice));
    }

    @Override
    public List<Product> findProductsByDescriptionAndSortedByDescription(String query, boolean ascendingOrder) {
        return sortProducts(ascendingOrder,
                product -> strContains(product.getDescription(), query),
                Comparator.comparing(Product::getDescription));
    }

    @Override
    public List<Product> findProducts(String query) {
        return findProducts().stream()
                .filter(product -> strContains(product.getDescription(), query))
                .collect(Collectors.toList());
    }

    private int getIndexOf(Product product) {
        return arrayListProduct.indexOf(product);
    }

    private boolean isExist(Product product) {
        return arrayListProduct.stream()
                .anyMatch(currentProduct -> product.getId().equals(currentProduct.getId()));
    }

    private static List<Product> getSampleProductList() {
        List<Product> products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        products.add(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" ));
        products.add(new Product("2L",  "Samsung Galaxy S II", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product("3L",  "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product("4L",  "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product("5L",  "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product("6L",  "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product("7L",  "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product("8L",  "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product("9L",  "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        products.add(new Product("10L", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        products.add(new Product("11L", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        products.add(new Product("12L", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        products.add(new Product("13L", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        for (Product product : products) {
            locker.getLock(product.getId());
        }
        return products;
    }

    public static void setArrayListProduct(List<Product> arrayListProduct) {
        ArrayListProductDao.arrayListProduct = arrayListProduct;
    }

    public static List<Product> getArrayListProduct() {
        return ArrayListProductDao.arrayListProduct;
    }

    private boolean strContains(String sourceStr, String comparableStr) {
        return Arrays.stream(comparableStr.split("\\s+"))
                .anyMatch(lexema -> sourceStr.toLowerCase().contains(lexema.toLowerCase()));
    }

    private List<Product> sortProducts(boolean isAscendingOrder, Predicate<Product> filterBy, Comparator<Product> sortBy) {
        List<Product> result = findProducts().stream()
                .filter(filterBy)
                .sorted(sortBy)
                .collect(Collectors.toList());
        if (!isAscendingOrder) {
            Collections.reverse(result);
        }
        return result;
    }
}
