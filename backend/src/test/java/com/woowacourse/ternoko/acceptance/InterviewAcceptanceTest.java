//package com.woowacourse.ternoko.acceptance;
//
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//
//class InterviewAcceptanceTest extends AcceptanceSupporter {
//
//    @Test
//    @DisplayName("면담 예약을 생성한다.")
//    void create() {
//        // given
//        putAvailableTimes_Coach1();
//
//        // when
//        final ExtractableResponse<Response> response = createInterviewByCoach1(CREW1, COACH1_INTERVIEW_REQUEST1);
//
//        //then
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotBlank();
//    }
//}
