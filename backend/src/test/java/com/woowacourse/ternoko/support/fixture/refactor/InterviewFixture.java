package com.woowacourse.ternoko.support.fixture.refactor;

import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_준_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.AvailableDateTimeFixture.면담가능시간_토미_2022_07_01_10_00;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.준;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.토미;

import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import java.util.List;

public class InterviewFixture {

    public static final FormItemRequest 면담사전질문요청정보 = createFormItemRequestBySize(1);

    public static final List<FormItemRequest> 면담사전질문요청정보들 = List.of(createFormItemRequestBySize(1),
            createFormItemRequestBySize(2),
            createFormItemRequestBySize(3));

    private static FormItemRequest createFormItemRequestBySize(final int number) {
        return new FormItemRequest("고정질문" + number, "답변" + number);
    }

    private static FormItem createFormItemBySize(int number) {
        return FormItem.of("고정질문" + number, "고정답변" + number);
    }

    public static final InterviewRequest 면담생성요청정보_준_2022_07_01_10_00 = new InterviewRequest(준.getId(),
            면담가능시간_준_2022_07_01_10_00.getId(),
            면담가능시간_준_2022_07_01_10_00.getLocalDateTime(),
            면담사전질문요청정보들);

    public static final InterviewRequest 면담생성요청정보_토미_2022_07_01_10_00 = new InterviewRequest(토미.getId(),
            면담가능시간_토미_2022_07_01_10_00.getId(),
            면담가능시간_토미_2022_07_01_10_00.getLocalDateTime(),
            면담사전질문요청정보들);
}
