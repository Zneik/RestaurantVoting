package ru.zneik.restaurant;

import ru.zneik.restaurant.model.Vote;

import java.time.LocalDate;

import static ru.zneik.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static ru.zneik.restaurant.RestaurantTestData.RESTAURANT_2_ID;
import static ru.zneik.restaurant.UserTestData.*;
import static ru.zneik.restaurant.model.base.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class);

    public final static int VOTE_1_ID = START_SEQ + 12;
    public final static int VOTE_2_ID = START_SEQ + 13;
    public final static int VOTE_3_ID = START_SEQ + 14;

    public final static Vote VOTE_1 = new Vote(VOTE_1_ID, USER_1_ID, RESTAURANT_1_ID, LocalDate.of(2020, 8, 23));
    public final static Vote VOTE_2 = new Vote(VOTE_2_ID, USER_1_ID, RESTAURANT_1_ID, LocalDate.now());
    public final static Vote VOTE_3 = new Vote(VOTE_3_ID, USER_2_ID, RESTAURANT_2_ID, LocalDate.now());

}
