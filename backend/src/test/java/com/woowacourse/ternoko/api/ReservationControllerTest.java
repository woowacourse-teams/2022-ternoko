package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ReservationControllerTest extends ControllerTest {

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() throws Exception {
        createCalendarTimes(COACH1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations/coaches/{coachId}", COACH1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .characterEncoding("utf-8")
                        .content(readJson("/json/reservations/create-reservation1.json")))
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("crewNickname").type(JsonFieldType.STRING).description("크루 닉네임"),
                                fieldWithPath("interviewDatetime").type(JsonFieldType.STRING)
                                        .description("인터뷰 시간"),
                                fieldWithPath("interviewQuestions.[].question").description("면담질문1"),
                                fieldWithPath("interviewQuestions.[].answer").description("면담답변1")
                        )));
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createReservations(COACH1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations/{reservationId}", 1)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("면담 예약 상세 내역 목록을 조회한다.")
    void findAll() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createReservations(COACH1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
                        responseFields( //response parameter
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("예약 아이디"),
                                fieldWithPath("[].coachNickname").type(JsonFieldType.STRING).description("코치 닉네임"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("코치 이미지url"),
                                fieldWithPath("[].crewNickname").type(JsonFieldType.STRING).description("크루 닉네임"),
                                fieldWithPath("[].interviewStartTime").type(JsonFieldType.STRING)
                                        .description("면담 시작 시간"),
                                fieldWithPath("[].interviewEndTime").type(JsonFieldType.STRING).description("면담 종료 시간"),
                                fieldWithPath("[].interviewQuestions").type(JsonFieldType.ARRAY).description("면담 내용"),
                                fieldWithPath("[].interviewQuestions[].question").type(JsonFieldType.STRING)
                                        .description("면담 질문"),
                                fieldWithPath("[].interviewQuestions[].answer").type(JsonFieldType.STRING)
                                        .description("면담 내용")
                        );
    }
}
