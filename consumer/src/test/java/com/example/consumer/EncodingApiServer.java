package com.example.consumer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

class EncodingApiServer implements BeforeAllCallback, AfterAllCallback {

    private final MockServerContainer mockServer = new MockServerContainer(
        DockerImageName.parse("mockserver/mockserver:5.15.0")
    );

    public String baseUrl() {
        return mockServer.getEndpoint();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        mockServer.start();
        setupStubs();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        mockServer.stop();
    }

    private void setupStubs() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
            .when(request().withPath("/").withMethod("POST"))
            .respond(response().withBody("SGVsbG8gV29ybGQh"));
    }
}
