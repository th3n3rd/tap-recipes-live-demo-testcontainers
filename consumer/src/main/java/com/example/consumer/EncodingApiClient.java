package com.example.consumer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class EncodingApiClient {

    private final RestTemplate client;

    public EncodingApiClient(RestTemplateBuilder clientBuilder, EncodingApiConfig config) {
        this.client = clientBuilder.rootUri(config.uri()).build();
    }

    String encode(String input) {
        return client.postForObject("/", input, String.class);
    }
}
