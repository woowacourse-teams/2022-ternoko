package com.woowacourse.ternoko.fixture;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_SECOND_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;

import com.woowacourse.ternoko.interview.domain.formitem.Answer;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.domain.formitem.Question;
import com.woowacourse.ternoko.interview.dto.FormItemRequest;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InterviewFixture {

    public static final Long INTERVIEW_TIME = 30L;

    private static final LocalDateTime NOW = LocalDateTime.now().withNano(0).plusDays(2);
    public static final LocalDateTime AFTER_TWO_DAYS = LocalDateTime.of(NOW.getYear(), NOW.getMonthValue(),
            NOW.getDayOfMonth(), NOW.getHour(), NOW.getMinute());

    public static final List<FormItemRequest> FORM_ITEM_REQUESTS = List.of(new FormItemRequest("고정질문1", "답변1"),
            new FormItemRequest("고정질문2", "답변2"),
            new FormItemRequest("고정질문3", "답변3"));

    public static final List<FormItemRequest> FORM_ITEM_UPDATE_REQUESTS = List.of(new FormItemRequest("수정질문1", "수정답변1"),
            new FormItemRequest("수정질문2", "수정답변2"),
            new FormItemRequest("수정질문3", "수정답변3"));

    public static final FormItem FORM_ITEM1 = new FormItem(null, Question.of("고정질문1"), Answer.of("고정답변1"));
    public static final FormItem FORM_ITEM2 = new FormItem(null, Question.of("고정질문2"), Answer.of("고정답변2"));
    public static final FormItem FORM_ITEM3 = new FormItem(null, Question.of("고정질문3"), Answer.of("고정답변3"));
    public static final FormItem FORM_ITEM4 = new FormItem(null, Question.of("고정질문4"), Answer.of("고정답변4"));
    public static final FormItem FORM_ITEM5 = new FormItem(null, Question.of("고정질문5"), Answer.of("고정답변5"));
    public static final FormItem FORM_ITEM6 = new FormItem(null, Question.of("고정질문6"), Answer.of("고정답변6"));

    public static final ArrayList<FormItem> FORM_ITEMS1 = new ArrayList<>() {
        {
            add(FORM_ITEM1);
            add(FORM_ITEM2);
            add(FORM_ITEM3);
        }
    };


    public static final ArrayList<FormItem> FORM_ITEMS2 = new ArrayList<>() {
        {
            add(FORM_ITEM4);
            add(FORM_ITEM5);
            add(FORM_ITEM6);
        }
    };

    public static final ArrayList<FormItem> FORM_ITEMS3 = new ArrayList<>() {
        {
            add(FORM_ITEM1);
            add(FORM_ITEM2);
            add(FORM_ITEM3);
        }
    };

    public static final InterviewRequest COACH1_INTERVIEW_REQUEST1 = new InterviewRequest(COACH1.getId(),
            NOW_PLUS_2_DAYS_FIRST_TIME,
            FORM_ITEM_REQUESTS);

    public static final InterviewRequest COACH1_INTERVIEW_REQUEST2 = new InterviewRequest(COACH1.getId(),
            NOW_PLUS_2_DAYS_SECOND_TIME,
            FORM_ITEM_REQUESTS);
}
