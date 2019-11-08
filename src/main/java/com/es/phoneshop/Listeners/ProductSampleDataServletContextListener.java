package com.es.phoneshop.Listeners;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductSampleDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ArrayListProductDao.getInstance();
        ProductService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
