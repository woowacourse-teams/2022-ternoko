package com.woowacourse.ternoko.fixture;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_SECOND_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;

import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.dto.request.FormItemRequest;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationFixture {

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

    public static final List<FormItem> FORM_ITEMS = List.of(new FormItem("고정질문1", "답변1"));
//    public static final List<FormItem> FORM_ITEMS1 = List.of(new FormItem("고정질문1", "답변1"),
//            new FormItem("고정질문1", "답변1"),
//            new FormItem("고정질문1", "답변1"));

    public static final ArrayList<FormItem> FORM_ITEMS1 = new ArrayList<>() {{
        add(new FormItem("고정질문1", "답변1"));
        add(new FormItem("고정질문1", "답변1"));
        add(new FormItem("고정질문1", "답변1"));
    }};

    public static final ArrayList<FormItem> FORM_ITEMS2 = new ArrayList<>() {{
        add(new FormItem("고정질문2", "답변2"));
        add(new FormItem("고정질문2", "답변2"));
        add(new FormItem("고정질문2", "답변2"));
    }};

    public static final ArrayList<FormItem> FORM_ITEMS3 = new ArrayList<>() {{
        add(new FormItem("고정질문3", "답변3"));
        add(new FormItem("고정질문3", "답변3"));
        add(new FormItem("고정질문3", "답변3"));
    }};

    public static final ReservationRequest COACH1_RESERVATION_REQUEST1 = new ReservationRequest(COACH1.getId(),
            NOW_PLUS_2_DAYS_FIRST_TIME,
            FORM_ITEM_REQUESTS);

    public static final ReservationRequest COACH1_RESERVATION_REQUEST2 = new ReservationRequest(COACH1.getId(),
            NOW_PLUS_2_DAYS_SECOND_TIME,
            FORM_ITEM_REQUESTS);
}
