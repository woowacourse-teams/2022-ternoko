package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public List<Member> findCoaches() {
        return memberRepository.findAllByType(Type.COACH);
    }
}
