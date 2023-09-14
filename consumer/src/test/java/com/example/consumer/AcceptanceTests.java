package com.example.consumer;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Tag("acceptance")
@SpringBootTest(
    webEnvironment = RANDOM_PORT
)
class AcceptanceTests {

    @Autowired
    private TestRestTemplate client;

    @RegisterExtension // must be static to execute beforeAll, afterAll
    public final static EncodingApiServer encodingApiServer = new EncodingApiServer();

    @DynamicPropertySource
    static void encodingApiProperties(DynamicPropertyRegistry registry) {
        registry.add("encoding-api.uri", encodingApiServer::baseUrl);
    }

    @Test
    void greetingIsEncoded() {
        var greeting = client.getForObject("/", String.class);
        assertThatJson(greeting).isEqualTo("""
        {
            "original": "Hello World!",
            "encoded": "SGVsbG8gV29ybGQh"
        }
        """);
    }

}
