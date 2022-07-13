package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
