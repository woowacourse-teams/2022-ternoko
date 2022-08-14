package com.woowacourse.ternoko.login.aop;

import com.woowacourse.ternoko.login.domain.MemberType;
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
