package com.woowacourse.ternoko.login.aop;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.COACH_NOT_ALLOWED;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.CREW_NOT_ALLOWED;

import com.woowacourse.ternoko.domain.member.MemberType;
import com.woowacourse.ternoko.login.exception.CoachNotAllowedException;
import com.woowacourse.ternoko.login.exception.CrewNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginMemberVerifier {

    private final MemberTypeCache memberTypeCache;

    @Before("@annotation(com.woowacourse.ternoko.login.aop.CrewOnly)")
    public void checkMemberTyeCrew() {
        final MemberType memberType = memberTypeCache.getMemberType();
        if (!memberType.equals(MemberType.CREW)) {
            throw new CrewNotAllowedException(CREW_NOT_ALLOWED);
        }
    }

    @Before("@annotation(com.woowacourse.ternoko.login.aop.CoachOnly)")
    public void checkMemberTyeCoach() {
        final MemberType memberType = memberTypeCache.getMemberType();
        if (!memberType.equals(MemberType.COACH)) {
            throw new CoachNotAllowedException(COACH_NOT_ALLOWED);
        }
    }
}
