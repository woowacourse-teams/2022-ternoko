package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.dto.request.FormItemRequest;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationFixture {

    public static final Long INTERVIEW_TIME = 30L;

    private static final LocalDateTime NOW = LocalDateTime.now().withNano(0).plusDays(2);
    public static final LocalDateTime AFTER_TWO_DAYS = LocalDateTime.of(NOW.getYear(), NOW.getMonthValue(),
            NOW.getDayOfMonth(), NOW.getHour(), NOW.getMinute());

    public static final List<FormItemRequest> FORM_ITEM_REQUESTS = List.of(new FormItemRequest("고정질문1", "답변1"),
            new FormItemRequest("고정질문2", "답변2"),
            new FormItemRequest("고정질문3", "답변3"));

    public static final List<FormItem> FORM_ITEMS = List.of(new FormItem("고정질문1", "답변1"),
            new FormItem("고정질문2", "답변2"),
            new FormItem("고정질문3", "답변3"));

    public static final ReservationRequest RESERVATION_REQUEST1 = new ReservationRequest(AFTER_TWO_DAYS,
            FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST2 = new ReservationRequest(AFTER_TWO_DAYS,
            FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST3 = new ReservationRequest(AFTER_TWO_DAYS,
            FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST4 = new ReservationRequest(AFTER_TWO_DAYS,
            FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST5 = new ReservationRequest(AFTER_TWO_DAYS,
            FORM_ITEM_REQUESTS);
}
