package com.arthur.textcorrector.client;

import com.arthur.textcorrector.dto.YandexError;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class YandexClient {

    private final RestClient yandexRestClient;

    private static final int MAX_TEXT_LENGTH = 10000;

    public String correctText(String text, String language) {

        List<String> parts = splitText(text);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("lang", language.toLowerCase());

        for (String part : parts) {
            form.add("text", part);
        }

        List<List<YandexError>> response =
                yandexRestClient.post()
                        .uri("/services/spellservice.json/checkTexts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(form)
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {
                        });

        if (response == null || response.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.size(); i++) {
            List<YandexError> errors = response.get(i);
            result.append(applyCorrections(parts.get(i), errors));
        }

        return result.toString();
    }

    private List<String> splitText(String text) {

        if (text.length() <= MAX_TEXT_LENGTH) {
            return List.of(text);
        }

        List<String> parts = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + MAX_TEXT_LENGTH, text.length());

            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(" ", end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            parts.add(text.substring(start, end));
            start = end;
        }

        return parts;
    }

    private String applyCorrections(String text, List<YandexError> errors) {

        if (errors == null || errors.isEmpty()) {
            return text;
        }

        StringBuilder sb = new StringBuilder(text);

        errors.sort(Comparator.comparingInt(YandexError::getPos).reversed());

        for (YandexError error : errors) {

            if (error.getS() == null || error.getS().isEmpty()) {
                continue;
            }

            sb.replace(
                    error.getPos(),
                    error.getPos() + error.getLen(),
                    error.getS().getFirst()
            );
        }

        return sb.toString();
    }
}
