package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.recentlyViewed.RecentlyViewed;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedServiceTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private RecentlyViewed recentlyViewed;

    private String recentlyViewedParam = "recentlyViewed";
    private Product product = new Product();
    private List<Product> recentlyViewedProducts = Collections.singletonList(new Product());

    private RecentlyViewedService recentlyViewedService;

    @Before
    public void setup() {
        recentlyViewedService = RecentlyViewedService.getInstance();
        recentlyViewedService.setRecentlyViewed(recentlyViewed);

        when(request.getSession()).thenReturn(session);
        when(recentlyViewed.getRecentlyViewedList()).thenReturn(recentlyViewedProducts);
        when(session.getAttribute(recentlyViewedParam)).thenReturn(recentlyViewedProducts);
    }

    @Test
    public void getRecentlyViewedListWhenProcessRequest() {
        assertEquals(recentlyViewedProducts, recentlyViewedService.processRequest(request, product));
    }
}
