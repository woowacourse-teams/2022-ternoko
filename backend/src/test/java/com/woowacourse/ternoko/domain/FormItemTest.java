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
    @MethodSource("validAnswerParameters")
    @DisplayName("answer에는 1000자 까지 들어갈 수 있다.")
    void answerLengthTest(String input, String testName) {
        assertDoesNotThrow(() -> FormItem.from("사전질문1", input));
    }

    private static Stream<Arguments> validAnswerParameters() {
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

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("validQuestionParameters")
    @DisplayName("question에는 100자 까지 들어갈 수 있다.")
    void questionLengthTest(String input, String testName) {
        assertDoesNotThrow(() -> FormItem.from(input, "답변"));
    }

    private static Stream<Arguments> validQuestionParameters() {
        return Stream.of(
                Arguments.of("a".repeat(254), "'a' 가 99개"),
                Arguments.of("a".repeat(255), "'a' 가 100개"),
                Arguments.of("한".repeat(254), "'한' 이 99개"),
                Arguments.of("한".repeat(255), "'한' 이 100개")
        );
    }

    @Test
    @DisplayName("question에는 255자가 넘어가면 예외를 반환해야 한다.")
    void questionInvalidLengthTest() {
        assertThatThrownBy(() -> FormItem.from("a".repeat(256), "답변"))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(255 + OVER_LENGTH.getMessage());
    }
}
