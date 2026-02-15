package com.arthur.textcorrector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient yandexRestClient() {
        return RestClient.builder()
                .baseUrl("https://speller.yandex.net")
                .build();
    }
}
