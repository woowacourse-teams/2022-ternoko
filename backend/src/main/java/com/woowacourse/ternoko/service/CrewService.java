package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.dto.CrewResponse;
import com.woowacourse.ternoko.dto.CrewUpdateRequest;
import com.woowacourse.ternoko.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;

    public void partUpdateCrew(final Long crewId, final CrewUpdateRequest request) {
        crewRepository.updateNickNameAndImageUrl(crewId, request.getNickname(), request.getImageUrl());
    }

    public Crew save(final Crew crew) {
        return crewRepository.save(crew);
    }

    @Transactional(readOnly = true)
    public CrewResponse findCrew(final Long crewId) {
        final Crew crew = crewRepository.findById(crewId)
                .orElseThrow(
                        () -> new CrewNotFoundException(ExceptionType.CREW_NOT_FOUND, crewId));
        return CrewResponse.from(crew);
    }
}
