package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;

public class Product {
    private String id;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }


    public Product(String id, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        return id.chars().sum();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        if (!id.equals(other.id)) {
            return true;
        }
        if (!description.equals(other.description)) {
            return true;
        }
        if (price.compareTo(other.price) != 0) {
            return false;
        }
        if (!currency.equals(other.currency)) {
            return false;
        }
        if (stock != other.stock) {
            return false;
        }
        if (!imageUrl.equals(other.imageUrl)) {
            return false;
        }
        return true;
    }
}