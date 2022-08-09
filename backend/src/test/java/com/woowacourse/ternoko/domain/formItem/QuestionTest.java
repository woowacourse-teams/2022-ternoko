package com.woowacourse.ternoko.domain.formItem;

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

class QuestionTest {

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("validQuestionParameters")
    @DisplayName("question에는 255자 까지 들어갈 수 있다.")
    void questionLengthTest(String input, String testName) {
        assertDoesNotThrow(() -> Question.of(input));
    }

    private static Stream<Arguments> validQuestionParameters() {
        return Stream.of(
                Arguments.of("a".repeat(254), "'a' 가 254개"),
                Arguments.of("a".repeat(255), "'a' 가 255개"),
                Arguments.of("한".repeat(254), "'한' 이 254개"),
                Arguments.of("한".repeat(255), "'한' 이 255개")
        );
    }

    @Test
    @DisplayName("question에는 255자가 넘어가면 예외를 반환해야 한다.")
    void questionInvalidLengthTest() {
        assertThatThrownBy(() -> Question.of("한".repeat(256)))
                .isInstanceOf(InvalidLengthException.class)
                .hasMessage(255 + OVER_LENGTH.getMessage());
    }
}
