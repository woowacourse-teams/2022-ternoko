package com.woowacourse.ternoko.auth.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class RandomMemberIdGenerator {

    final static Random RANDOM = new Random(System.currentTimeMillis());

    final long startCrewId;
    final long endCrewId;
    final long startCoachId;
    final long endCoachId;
    List<Long> crewIds;
    List<Long> coachIds;

    public RandomMemberIdGenerator(final long startCrewId, final long endCrewId, final long startCoachId,
                                   final long endCoachId,
                                   final List<Long> crewIds, final List<Long> coachIds) {
        this.startCrewId = startCrewId;
        this.endCrewId = endCrewId;
        this.startCoachId = startCoachId;
        this.endCoachId = endCoachId;
        this.crewIds = crewIds;
        this.coachIds = coachIds;
    }

    public static RandomMemberIdGenerator of(final long startCrewId,
                                             final long endCrewId,
                                             final long startCoachId,
                                             final long endCoachId) {
        final List<Long> crewIds = getIds(startCrewId, endCrewId);

        final List<Long> coachIds = new ArrayList<>();
        for (long i = startCoachId; i <= endCoachId; i++) {
            coachIds.add(i);
        }

        return new RandomMemberIdGenerator(startCrewId, endCrewId, startCoachId, endCoachId, crewIds, coachIds);
    }

    @NotNull
    private static List<Long> getIds(final long startId, final long endId) {
        final List<Long> ids = Collections.synchronizedList(new ArrayList<>());
        for (long i = startId; i <= endId; i++) {
            ids.add(i);
        }
        return ids;
    }

    public Long getRandomCrewId() {
        if (crewIds.size() == 0) {
            crewIds = getIds(startCrewId, endCrewId);
        }

        final int randomIndex = RANDOM.nextInt(crewIds.size());
        return crewIds.remove(randomIndex);
    }

    public Long getRandomCoachId() {
        if (coachIds.size() == 0) {
            coachIds = getIds(startCoachId, endCoachId);
        }

        final int randomIndex = RANDOM.nextInt(coachIds.size());
        return coachIds.remove(randomIndex);
    }
}
