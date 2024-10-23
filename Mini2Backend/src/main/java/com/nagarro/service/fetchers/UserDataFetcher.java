package com.nagarro.service.fetchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.dto.RandomUser;

import reactor.core.publisher.Mono;

@Component
public class UserDataFetcher {
	private final WebClient webClient;

	@Autowired
	public UserDataFetcher(@Qualifier("api1WebClient") WebClient.Builder api1WebClientBuilder) {
	    this.webClient = api1WebClientBuilder.build();
	}


    public RandomUser fetchRandomUserData() {
    	return webClient
                .get()
                .retrieve()
                .onStatus(
                        status -> !HttpStatus.OK.equals(status),
                        response -> handleErrorResponse(response)
                )
                .bodyToMono(RandomUser.class)
                .doOnError(throwable -> handleError("Error in fetching user data"))
                .block();
    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        return Mono.error(new RuntimeException("Failed to fetch user data. Status code: " + response.statusCode()));
    }

    private Mono<? extends Throwable> handleError(String message) {
        return Mono.error(new RuntimeException(message));
    }
}

