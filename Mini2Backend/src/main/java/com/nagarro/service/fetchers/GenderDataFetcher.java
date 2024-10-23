package com.nagarro.service.fetchers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.dto.Gender;

import reactor.core.publisher.Mono;

@Component
public class GenderDataFetcher {
    private final WebClient webClientGender;

    @Autowired
    public GenderDataFetcher(@Qualifier("api3WebClient") WebClient.Builder api3WebClientBuilder) {
        this.webClientGender = api3WebClientBuilder.build();
    }

    public CompletableFuture<Gender> fetchGenderData(String firstName) {
        return webClientGender
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", firstName).build())
                .retrieve()
                .bodyToMono(Gender.class)
                .doOnError(throwable -> handleError("Error fetching gender data"))
                .toFuture();
    }


    private Mono<? extends Throwable> handleError(String message) {
        return Mono.error(new RuntimeException(message));
    }
}