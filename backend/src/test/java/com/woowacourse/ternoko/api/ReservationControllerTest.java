package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1_RESERVATION_REQUEST1;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ReservationControllerTest extends ControllerTest {

    @Test
    @DisplayName("면담 예약을 생성한다.")
    void create() throws Exception {
        createCalendarTimes(COACH1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
                        .content(objectMapper.writeValueAsString(COACH1_RESERVATION_REQUEST1)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("면담 예약 상세 내역을 조회한다.")
    void findReservationById() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createReservations(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations/{reservationId}", 1)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("면담 예약 상세 내역 목록을 조회한다.")
    void findAll() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createReservations(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루가 면담 예약을 취소한다.")
    void delete() throws Exception {
        createCalendarTimes(COACH1.getId());
        Long reservationId = createReservation(CREW1.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }
}
