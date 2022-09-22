package com.woowacourse.ternoko.core.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoachesResponse {

    private List<CoachResponse> coaches;
}
