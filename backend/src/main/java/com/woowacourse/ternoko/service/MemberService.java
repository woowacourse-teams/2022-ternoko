package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;

    @Transactional(readOnly = true)
    public CoachesResponse findCoaches() {
        final List<Member> coaches = memberRepository.findAllByType(Type.COACH);

        final List<CoachResponse> coachResponses = coaches.stream()
                .map(member -> CoachResponse.coachResponseBuilder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .imageUrl(member.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return new CoachesResponse(coachResponses);
    }

    public void putAvailableDateTimesByCoachId(final Long coachId,
                                               final AvailableDateTimesRequest availableDateTimesRequest) {
        final Member coach = memberRepository.findById(coachId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 코치를 찾을 수 없습니다."));
        final List<AvailableDateTime> availableDateTimes = availableDateTimesRequest.toAvailableDateTimes(coach);

        if (!availableDateTimes.isEmpty()) {
            final LocalDateTime localDateTime = availableDateTimes.get(0).getLocalDateTime();
            availableDateTimeRepository.deleteAllByCoachAndYearAndMonth(
                    coach.getId(),
                    localDateTime.getYear(),
                    localDateTime.getMonthValue());
            availableDateTimeRepository.saveAll(availableDateTimes);
        }
    }

    @Transactional(readOnly = true)
    public List<AvailableDateTime> findAvailableDateTimesByCoachId(final Long coachId) {
        return availableDateTimeRepository.findAvailableDateTimesByCoachId(coachId);
    }
}
