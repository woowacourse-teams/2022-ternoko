package com.woowacourse.ternoko.core.application;

import com.woowacourse.ternoko.core.application.response.NicknameResponse;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public NicknameResponse hasNickname(final String nickname) {
        return new NicknameResponse(memberRepository.findByNickname(nickname).isPresent());
    }
}
