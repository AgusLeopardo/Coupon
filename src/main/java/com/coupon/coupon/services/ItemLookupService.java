package com.coupon.coupon.services;

import com.coupon.coupon.models.Item;
import com.coupon.coupon.repositories.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ItemLookupService {
    private ItemRepository repository;

    @Autowired
    public ItemLookupService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getItems(Collection<String> idList) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        List<CompletableFuture<HttpResponse<String>>> futureResponses = getCompletableFutures(idList);
        CompletableFuture<List<HttpResponse<String>>> allResponses = waitAllResponses(futureResponses);
        List<Item> itemList = populateItemList(allResponses.get());

        if(hasNull(itemList))
            throw new IllegalArgumentException("One or more item IDs don't exist.");

        return itemList;
    }

    private List<CompletableFuture<HttpResponse<String>>> getCompletableFutures(Collection<String> idList) throws URISyntaxException {
        List<CompletableFuture<HttpResponse<String>>> futureResponses = new ArrayList<>();

        for(String id : idList){
            futureResponses.add(repository.getItemsAsync(id));
        }
        return futureResponses;
    }

    private CompletableFuture<List<HttpResponse<String>>> waitAllResponses(List<CompletableFuture<HttpResponse<String>>> futureResponses) {
        return CompletableFuture.allOf(futureResponses.toArray(new CompletableFuture[0]))
                .thenApply(f -> futureResponses.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    private boolean hasNull(List<Item> itemList) {
        return itemList.stream().anyMatch(item -> item.getId() == null);
    }

    private List<Item> populateItemList(List<HttpResponse<String>> responses) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Item> itemList = new ArrayList<>();

        for(HttpResponse<String> response : responses){
            String body =  response.body();
            itemList.add(mapper.readValue(body, new TypeReference<Item>() {}));
        }

        return itemList;
    }
}
