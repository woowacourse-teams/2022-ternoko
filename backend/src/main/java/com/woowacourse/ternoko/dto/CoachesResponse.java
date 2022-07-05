package com.woowacourse.ternoko.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoachesResponse {

    private final List<CoachResponse> coaches;
}
