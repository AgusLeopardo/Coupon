package com.coupon.coupon.responses;

import java.util.List;

public class CouponResponse {
    private List<String> itemIds;
    private float total;

    public CouponResponse(List<String> itemIds, float total) {
        this.itemIds = itemIds;
        this.total = total;
    }

    public CouponResponse() {

    }

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
