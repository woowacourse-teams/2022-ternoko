package com.woowacourse.ternoko.core.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.DUPLICATED_MEMBER_NICKNAME;

import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidMemberNicknameException;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import com.woowacourse.ternoko.core.dto.request.CrewUpdateRequest;
import com.woowacourse.ternoko.core.dto.response.CrewResponse;
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
    private final MemberRepository memberRepository;

    public void partUpdateCrew(final Long crewId, final CrewUpdateRequest request) {
        final Crew crew = getCrewById(crewId);
        final String requestNickname = request.getNickname();

        if (!crew.isSameNickname(requestNickname)
                && memberRepository.existsByNickname(request.getNickname())) {
            throw new InvalidMemberNicknameException(DUPLICATED_MEMBER_NICKNAME);
        }
        crewRepository.updateNickNameAndImageUrl(crewId, request.getNickname(), request.getImageUrl());
    }

    public Crew save(final Crew crew) {
        return crewRepository.save(crew);
    }

    @Transactional(readOnly = true)
    public CrewResponse findCrew(final Long crewId) {
        final Crew crew = getCrewById(crewId);
        return CrewResponse.from(crew);
    }

    private Crew getCrewById(final Long crewId) {
        return crewRepository.findById(crewId)
                .orElseThrow(() -> new CrewNotFoundException(ExceptionType.CREW_NOT_FOUND, crewId));
    }
}
