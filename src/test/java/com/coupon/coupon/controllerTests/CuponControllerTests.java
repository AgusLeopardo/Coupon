package com.coupon.coupon.controllerTests;

import com.coupon.coupon.controllers.CuponController;
import com.coupon.coupon.requests.CouponRequest;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CuponControllerTests {
    @Autowired
    CuponController controller;

    @Test
    void getBestCombination() throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        List<String> itemIds = Arrays.asList("MLA786078522", "MLA811931295", "MLA844693854", "MLA902080859");
        Float coupon = (float) 40000;

        CouponRequest req = new CouponRequest();
        req.setItem_ids(itemIds);
        req.setAmount(coupon);

        ResponseEntity response = controller.BestCombinationForCoupon(req);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testFakeId() throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        List<String> itemIds = Arrays.asList("MLA1", "MLA811931295", "MLA844693854", "MLA902080859");
        Float coupon = (float) 40000;

        CouponRequest req = new CouponRequest();
        req.setItem_ids(itemIds);
        req.setAmount(coupon);

        ResponseEntity response = controller.BestCombinationForCoupon(req);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void testZeroCouponValue() throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        List<String> itemIds = Arrays.asList("MLA786078522", "MLA811931295", "MLA844693854", "MLA902080859");
        Float coupon = (float) 0;

        CouponRequest req = new CouponRequest();
        req.setItem_ids(itemIds);
        req.setAmount(coupon);

        ResponseEntity response = controller.BestCombinationForCoupon(req);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testNegativeCouponValue() throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        List<String> itemIds = Arrays.asList("MLA786078522", "MLA811931295", "MLA844693854", "MLA902080859");
        Float coupon = (float) 0;

        CouponRequest req = new CouponRequest();
        req.setItem_ids(itemIds);
        req.setAmount(coupon);

        ResponseEntity response = controller.BestCombinationForCoupon(req);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
