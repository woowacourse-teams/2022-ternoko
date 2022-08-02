package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.dto.NicknameResponse;
import com.woowacourse.ternoko.repository.MemberRepository;
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
