package com.arthur.textcorrector.client;

import com.arthur.textcorrector.config.RestClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class YandexClientHttpTest {

    @Autowired
    private RestClientConfig builder;

    private MockRestServiceServer server;

    private YandexClient client;

    @BeforeEach
    void setup() {
        RestClient restClient = builder.yandexRestClient();
        server = MockRestServiceServer.bindTo(restClient.mutate()).build();
        client = new YandexClient(restClient);
    }

    @Test
    void shouldCorrectText() {

        String responseJson = """
                [
                  [
                    {
                      "pos":0,
                      "len":6,
                      "s":["Корова"]
                    }
                  ]
                ]
                """;

        server.expect(requestTo("/services/spellservice.json/checkTexts"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        String result = client.correctText("Карова", "ru");

        assertEquals("Корова", result);
    }
}
