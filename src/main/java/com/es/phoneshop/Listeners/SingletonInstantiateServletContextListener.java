package com.es.phoneshop.Listeners;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.order.ArrayListOrderDao;
import com.es.phoneshop.order.OrderService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SingletonInstantiateServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ArrayListProductDao.getInstance();
        ArrayListOrderDao.getInstance();
        ProductService.getInstance();
        OrderService.getInstance();
        CartService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
