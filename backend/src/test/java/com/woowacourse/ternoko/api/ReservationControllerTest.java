package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1_RESERVATION_REQUEST1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1_RESERVATION_REQUEST2;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ReservationControllerTest extends ControllerTest {

    @Test
    @DisplayName("크루 - 면담 예약을 생성한다.")
    void createReservation() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());

        //when, then
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
    @DisplayName("크루 - 면담 예약 내역을 조회한다.")
    void findReservationById() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        final Long reservationId = createReservation(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations/{reservationId}", reservationId)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약 내역 목록을 조회한다.")
    void findAllReservations() throws Exception {
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
    @DisplayName("크루 - 면담 예약 내역을 수정한다.")
    void updateReservation() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        final Long reservationId = createReservation(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
                        .content(objectMapper.writeValueAsString(COACH1_RESERVATION_REQUEST2)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약을 취소한다.")
    void deleteReservation() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());
        final Long reservationId = createReservation(CREW1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 면담 예약을 취소한다.")
    void cancelReservation() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());
        final Long reservationId = createReservation(CREW1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }
}
