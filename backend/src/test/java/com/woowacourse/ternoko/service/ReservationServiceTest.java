package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.dto.FormItemRequest;
import com.woowacourse.ternoko.dto.ReservationRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void create() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest("바니",
                LocalDateTime.of(2022, 7, 4, 14, 0, 0),
                Location.JAMSIL.getValue(),
                List.of(new FormItemRequest("고정질문1", "답변1"),
                        new FormItemRequest("고정질문2", "답변2"),
                        new FormItemRequest("고정질문3", "답변3")));

        final Long coachId = 1L;
        // when
        final Long id = reservationService.create(coachId, reservationRequest);

        // then
        assertThat(id).isNotNull();
    }
}
