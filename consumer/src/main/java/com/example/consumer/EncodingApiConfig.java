package com.example.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("encoding-api")
record EncodingApiConfig(String uri) {}
