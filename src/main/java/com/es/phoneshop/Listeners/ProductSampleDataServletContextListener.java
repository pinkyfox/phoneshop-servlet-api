package com.es.phoneshop.Listeners;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
