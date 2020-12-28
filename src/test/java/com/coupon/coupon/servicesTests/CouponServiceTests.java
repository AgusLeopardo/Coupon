package com.coupon.coupon.servicesTests;

import com.coupon.coupon.models.Item;
import com.coupon.coupon.responses.CouponResponse;
import com.coupon.coupon.services.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CouponServiceTests {
    @Autowired
    CouponService service;

    @Test
    void bestCombinationTest() throws Exception {
        List<Item> mockItems = new ArrayList<>();
        mockItems.add(new Item("MLA1", (float) 100));
        mockItems.add(new Item("MLA2", (float) 210));
        mockItems.add(new Item("MLA3", (float) 260));
        mockItems.add(new Item("MLA4", (float) 80));
        mockItems.add(new Item("MLA5", (float) 90));

        CouponResponse bestCombo = service.getBestCombinationForCoupon(mockItems, 500);

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("MLA1");
        mockReturn.add("MLA2");
        mockReturn.add("MLA4");
        mockReturn.add("MLA5");

        Collections.sort(bestCombo.getItemIds());
        assertEquals(mockReturn, bestCombo.getItemIds());
    }
}
