package com.coupon.coupon.repositories;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Repository
public class ItemRepository {
    private final String uri = "https://api.mercadolibre.com/items/";

    @Async
    public CompletableFuture<HttpResponse<String>> getItemsAsync(String id) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri + id))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder()
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
