package com.initializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.validator.client.DBClient;
import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DBClient.class)
@ContextConfiguration(initializers = {WireMockInitializer.class})
@AutoConfigureWebTestClient

public class ValidatorRestControllerUnitTest {

    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private WebTestClient webTestClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private Integer port;

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @Test
    public void validateUserTest() throws JsonProcessingException {

        ValidatedUser validatedUser = new ValidatedUser(true);
        String request = objectMapper.writeValueAsString(validatedUser);
        User user = new User(1, "qqqq", "qqqq@qq.qq", true);
        String response = objectMapper.writeValueAsString(user);

        wireMockServer.stubFor(
                WireMock.patch(
                        WireMock.urlMatching("/api/validate-user/" + 1))
                        .withRequestBody(WireMock.equalToJson(request))
                        .willReturn(WireMock.aResponse().withBody(response).withStatus(200)));

        webTestClient.patch()
                .uri("http://localhost:" + port + "/api/validate-user/" + 1)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .json(response);
    }
}
