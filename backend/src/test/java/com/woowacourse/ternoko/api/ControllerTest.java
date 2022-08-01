package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1_RESERVATION_REQUEST1;
import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH1_RESERVATION_REQUEST2;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ControllerTest extends RestDocsTestSupport {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public void createCalendarTimes(final Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(coachId)))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk());
    }

    public void createReservations(final Long crewId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(crewId)))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(COACH1_RESERVATION_REQUEST1)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(crewId)))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(COACH1_RESERVATION_REQUEST2)))
                .andExpect(status().isCreated());
    }
}
