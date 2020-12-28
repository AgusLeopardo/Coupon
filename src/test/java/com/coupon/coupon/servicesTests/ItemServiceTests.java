package com.coupon.coupon.servicesTests;

import com.coupon.coupon.models.Item;
import com.coupon.coupon.services.ItemLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemServiceTests {
    @Autowired
    ItemLookupService service;

    @Test
    void getItemTest() throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        List<String> itemIds = Arrays.asList("MLA786078522", "MLA811931295");

        List<Item> items = service.getItems(itemIds);
        float sum = items.stream().map(x -> x.getPrice())
                .reduce((float) 0, Float::sum);

        assertEquals(items.size(), 2);
        assertEquals(sum, (float) 3398);
    }
}
