package com.nagarro.service.fetchers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.dto.Nationality;

import reactor.core.publisher.Mono;

@Component
public class NationalityDataFetcher {
    private final WebClient webClient;

    @Autowired
    public NationalityDataFetcher(@Qualifier("api2WebClient") WebClient.Builder api2WebClientBuilder) {
        this.webClient = api2WebClientBuilder.build();
    }


    public CompletableFuture<Nationality> fetchNationalityData(String firstName) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", firstName).build())
                .retrieve()
                .bodyToMono(Nationality.class)
                .doOnError(throwable -> handleError("Error fetching nationality data"))
                .toFuture();
    }

    private Mono<? extends Throwable> handleError(String message) {
        return Mono.error(new RuntimeException(message));
    }
}