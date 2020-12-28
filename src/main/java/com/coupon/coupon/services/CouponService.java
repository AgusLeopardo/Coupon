package com.coupon.coupon.services;

import com.coupon.coupon.models.Item;
import com.coupon.coupon.responses.CouponResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponService {
    public CouponResponse getBestCombinationForCoupon(List<Item> items, float maxValue) {
        CouponResponse bestCombination = new CouponResponse(null, 0);
        searchCombinations(items, maxValue, 0, null, bestCombination);
        return bestCombination;
    }

    private void searchCombinations(List<Item> items, float maxValue, float currentValue,
                                    List<String> addedItems, CouponResponse bestCombination) {
        if(addedItems == null)
            addedItems = new ArrayList<>();

        if(bestCombination.getTotal() == maxValue)
            return;

        for (Item entry : items) {
            float couponTotal = currentValue + entry.getPrice();

            if(couponTotal <= maxValue) {
                List<String> addedItemsCopy = deepCopyList(addedItems);
                addedItemsCopy.add(entry.getId());
                List<Item> itemsCopy = copyListWithoutItem(items, entry.getId());
                searchCombinations(itemsCopy, maxValue, couponTotal, addedItemsCopy, bestCombination);
            }
            else {
                if(currentValue > bestCombination.getTotal()){
                    setBestCombination(currentValue, addedItems, bestCombination);
                }
            }
        }
    }

    private void setBestCombination(float currentValue, List<String> addedItems, CouponResponse bestCombination) {
        bestCombination.setItemIds(addedItems);
        bestCombination.setTotal(currentValue);
    }

    private List<Item> copyListWithoutItem(List<Item> toCopy, String idToRemove){
       return toCopy.stream()
               .filter(m -> m.getId() != idToRemove)
               .collect(Collectors.toList());
    }

    private List<String> deepCopyList(List<String> toCopy){
        return toCopy.stream().map(String::new).collect(Collectors.toList());
    }
}
