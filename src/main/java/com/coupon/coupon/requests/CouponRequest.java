package com.coupon.coupon.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

public class CouponRequest {
    private List<String> item_ids;
    private float amount;

    public List<String> getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(List<String> item_ids) {
        this.item_ids = item_ids;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
