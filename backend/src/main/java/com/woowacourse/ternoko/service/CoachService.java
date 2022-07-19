package com.woowacourse.ternoko.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.repository.CoachRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;

    @Transactional(readOnly = true)
    public CoachesResponse findCoaches() {
        final List<Coach> coaches = coachRepository.findAll();

        final List<CoachResponse> coachResponses = coaches.stream()
                .map(coach -> CoachResponse.coachResponseBuilder()
                        .id(coach.getId())
                        .nickname(coach.getNickname())
                        .imageUrl(coach.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    public void putAvailableDateTimesByCoachId(final Long coachId,
                                               final AvailableDateTimesRequest availableDateTimesRequest) {
        final Coach coach = coachRepository.findById(coachId)
            .orElseThrow(() -> new NoSuchElementException("해당하는 코치를 찾을 수 없습니다."));

        final List<AvailableDateTimeRequest> availableDateTimeRequests = availableDateTimesRequest
            .getCalendarTimes();
        for (AvailableDateTimeRequest availableDateTime : availableDateTimeRequests) {
            putAvailableTime(coach, availableDateTime);
        }
    }

    private void putAvailableTime(final Coach coach, final AvailableDateTimeRequest availableDateTime) {
        availableDateTimeRepository.deleteAllByCoachAndYearAndMonth(
            coach.getId(),
            availableDateTime.getYear(),
            availableDateTime.getMonth());
        availableDateTimeRepository.saveAll(toAvailableDateTimes(coach, availableDateTime.getTimes()));
    }

    public List<AvailableDateTime> toAvailableDateTimes(final Coach coach, final List<LocalDateTime> times) {
        return times.stream()
            .map(time -> new AvailableDateTime(coach, time))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachId(final Long coachId) {
        return availableDateTimeRepository.findAvailableDateTimesByCoachId(coachId);
    }
}
