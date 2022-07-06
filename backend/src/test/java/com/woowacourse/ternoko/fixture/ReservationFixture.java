package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.dto.FormItemRequest;
import com.woowacourse.ternoko.dto.ReservationRequest;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationFixture {

    public static final Long INTERVIEW_TIME = 30L;

    public static final Member COACH1 = new Member(1L, "준", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH2 = new Member(2L, "브리", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH3 = new Member(3L, "토미", "test@email.com", Type.COACH, "imageUrl");
    public static final Member COACH4 = new Member(4L, "네오", "test@email.com", Type.COACH, "imageUrl");

    public static final List<FormItemRequest> FORM_ITEM_REQUESTS = List.of(new FormItemRequest("고정질문1", "답변1"),
            new FormItemRequest("고정질문2", "답변2"),
            new FormItemRequest("고정질문3", "답변3"));

    public static final ReservationRequest RESERVATION_REQUEST1 = new ReservationRequest("바니",
            LocalDateTime.of(2022, 7, 4, 14, 0, 0),
            Location.JAMSIL.getValue(), FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST2 = new ReservationRequest("열음",
            LocalDateTime.of(2022, 7, 4, 14, 0, 0),
            Location.JAMSIL.getValue(), FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST3 = new ReservationRequest("앤지",
            LocalDateTime.of(2022, 7, 4, 14, 0, 0),
            Location.JAMSIL.getValue(), FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST4 = new ReservationRequest("애쉬",
            LocalDateTime.of(2022, 7, 4, 14, 0, 0),
            Location.JAMSIL.getValue(), FORM_ITEM_REQUESTS);

    public static final ReservationRequest RESERVATION_REQUEST5 = new ReservationRequest("수달",
            LocalDateTime.of(2022, 7, 4, 14, 0, 0),
            Location.JAMSIL.getValue(), FORM_ITEM_REQUESTS);
}
