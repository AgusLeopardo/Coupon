package com.coupon.coupon.controllers;

import com.coupon.coupon.models.Item;
import com.coupon.coupon.requests.CouponRequest;
import com.coupon.coupon.responses.CouponResponse;
import com.coupon.coupon.services.CouponService;
import com.coupon.coupon.services.ItemLookupService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class CuponController {
    private static final Logger logger = LogManager.getLogger(CuponController.class);

    private CouponService couponService;
    private ItemLookupService itemService;

    @Autowired
    public CuponController(CouponService couponService, ItemLookupService itemService) {
        this.couponService = couponService;
        this.itemService = itemService;
    }

    @PostMapping(value = "/coupon/", consumes = "application/json")
    public @ResponseBody ResponseEntity BestCombinationForCoupon(@RequestBody CouponRequest request) throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        HashSet<String> uniqueIds = new HashSet<>(request.getItem_ids());
        CouponResponse bestCombination;

        if(request.getAmount() < 0)
            return ResponseEntity.badRequest().body("Coupon value cannot be negative.");

        try
        {
            List<Item> items = itemService.getItems(uniqueIds);
            bestCombination = couponService.getBestCombinationForCoupon(items, request.getAmount());
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            logger.log(Level.ERROR, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops! Something went wrong.");
        }

        if(bestCombination.getTotal() == 0){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bestCombination);
    }
}
