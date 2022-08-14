package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST1;
import static com.woowacourse.ternoko.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST2;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerTest extends RestDocsTestSupport {

    protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public void createCalendarTimes(final Coach coach) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(coach))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk());
    }

    public Long createInterview(final Crew crew) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(crew))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST1)))
                .andExpect(status().isCreated())
                .andReturn();
        String redirectURI = mvcResult.getResponse().getHeader("Location");
        return Long.valueOf(redirectURI.split("/interviews/")[1]);
    }

    public void createInterviews(final Crew crew) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(crew))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST1)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(crew))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST2)))
                .andExpect(status().isCreated());
    }
}
