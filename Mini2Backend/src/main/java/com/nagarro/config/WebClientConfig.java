package com.nagarro.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    // Bean Configuration for the first API
    @Bean(name = "api1WebClient")
    public WebClient.Builder api1WebClientBuilder() {
        return createWebClientBuilder("https://randomuser.me/api/", 2000);
    }

    // Bean configuration for the second API
    @Bean(name = "api2WebClient")
    public WebClient.Builder api2WebClientBuilder() {
        return createWebClientBuilder("https://api.nationalize.io/?name=Ved", 1000);
    }

    // Bean configuration for the third API
    @Bean(name = "api3WebClient")
    public WebClient.Builder api3WebClientBuilder() {
        return createWebClientBuilder("https://api.genderize.io/?name=Rishaan", 1000);
    }

    // Private method to create a WebClient.Builder with common configuration
    private WebClient.Builder createWebClientBuilder(String baseUrl, int timeoutMillis) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(createReactorClientHttpConnector(timeoutMillis))
                .filter((request, next) -> next.exchange(request)
                        .doOnError(TimeoutException.class, ex -> handleTimeoutException(request, ex)));
    }

    // Private method to create a ReactorClientHttpConnector with timeout configuration
    private ReactorClientHttpConnector createReactorClientHttpConnector(int timeoutMillis) {
        return new ReactorClientHttpConnector(HttpClient.create()
                .responseTimeout(Duration.ofMillis(timeoutMillis))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeoutMillis, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeoutMillis, TimeUnit.MILLISECONDS))));
    }

    // Private method to handle timeout exceptions
    private void handleTimeoutException(ClientRequest request, TimeoutException ex) {
        LOGGER.error("Timeout Exception for request: {} {}", request.method(), request.url(), ex);
    }
}
