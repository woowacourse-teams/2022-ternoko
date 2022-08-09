package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.OVER_LENGTH;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.ternoko.common.exception.InvalidLengthException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FormItemTest {

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("validParameters")
    @DisplayName("answer에는 1000자 까지 들어갈 수 있다.")
    void answerLengthTest(String input, String testName) {
        assertDoesNotThrow(() -> FormItem.from("사전질문1", input));
    }

    private static Stream<Arguments> validParameters() {
        return Stream.of(
                Arguments.of("a".repeat(999), "'a' 가 999개"),
                Arguments.of("a".repeat(1000), "'a' 가 1000개"),
                Arguments.of("한".repeat(999), "'한' 이 999개"),
                Arguments.of("한".repeat(1000), "'한' 이 1000개")
        );
    }

    @Test
    @DisplayName("answer에는 1000자가 넘어가면 예외를 반환해야 한다.")
    void answerInvalidLengthTest() {
        assertThatThrownBy(() -> FormItem.from("사전질문", "a".repeat(1001)))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(1000 + OVER_LENGTH.getMessage());
    }
}