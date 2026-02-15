package com.arthur.textcorrector.client;


import com.arthur.textcorrector.dto.YandexError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class YandexClientApplyCorrectionsTests {
    private final YandexClient client = new YandexClient(null);

    @ParameterizedTest
    @MethodSource("correctionProvider")
    void shouldApplyCorrectionsCorrectly(String original, List<YandexError> errors, String expected) {
        String result = ReflectionTestUtils.invokeMethod(client, "applyCorrections", original, errors);
        assertEquals(expected, result);
    }

    static Stream<Arguments> correctionProvider() {
        return Stream.of(
                Arguments.of(
                        "Првиет",
                        new ArrayList<>(List.of(createError(0, 6, "Привет"))),
                        "Привет"
                ),
                Arguments.of(
                        "Ошыбка и ещо",
                        new ArrayList<>(List.of(
                                createError(0, 6, "Ошибка"),
                                createError(9, 3, "еще")
                        )),
                        "Ошибка и еще"
                )
        );
    }

    private static YandexError createError(int pos, int len, String replacement) {
        YandexError error = new YandexError();
        error.setPos(pos);
        error.setLen(len);
        error.setS(List.of(replacement));
        return error;
    }
}
