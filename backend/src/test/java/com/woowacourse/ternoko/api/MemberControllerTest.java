package com.woowacourse.ternoko.api;

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

class MemberControllerTest extends RestDocsTestSupport {

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations/coaches"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("coaches.[].id").type(JsonFieldType.NUMBER).description("코치 아이디"),
                                fieldWithPath("coaches.[].nickname").type(JsonFieldType.STRING).description("코치 닉네임"),
                                fieldWithPath("coaches.[].imageUrl").type(JsonFieldType.STRING).description("코치 사진 url")
                        )));
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/coaches/{coachId}/calendar/times", COACH1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(readJson("json/members/save-calendar-times.json")))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(fieldWithPath("calendarTimes.[].year").type(JsonFieldType.NUMBER)
                                        .description("년도"),
                                fieldWithPath("calendarTimes.[].month").type(JsonFieldType.NUMBER)
                                        .description("달"),
                                fieldWithPath("calendarTimes.[].times").type(JsonFieldType.ARRAY)
                                        .description("면담 가능 시간")
                        )));
    }

    @Test
    @DisplayName("코치의 면담 가능 시간 목록을 조회한다.")
    void findCalendarTimes() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches/{coachId}/calendar/times", COACH1.getId())
                        .queryParam("year", "2022")
                        .queryParam("month", "7"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(fieldWithPath("calendarTimes[]").type(JsonFieldType.ARRAY)
                                .description("면담 가능 시간 목록")
                        )));
    }

    private void createCalendarTimes(Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/coaches/{coachId}/calendar/times", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(readJson("json/members/save-calendar-times.json")))
                .andExpect(status().isOk());
    }
}
