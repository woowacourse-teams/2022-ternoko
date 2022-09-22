package com.woowacourse.ternoko.auth.presentation;

import com.woowacourse.ternoko.core.domain.member.MemberType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class MemberTypeCache {
    private MemberType memberType;

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
}
