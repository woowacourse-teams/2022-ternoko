package com.woowacourse.ternoko.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ControllerTest extends RestDocsTestSupport  {
    public void createCalendarTimes(final Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/coaches/{coachId}/calendar/times", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(readJson("/json/members/save-calendar-times.json")))
                .andExpect(status().isOk());
    }

    public void createReservations(final Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations/coaches/{coachId}", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(readJson("/json/reservations/create-reservation1.json")))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/reservations/coaches/{coachId}", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(readJson("/json/reservations/create-reservation2.json")))
                .andExpect(status().isCreated());
    }
}
