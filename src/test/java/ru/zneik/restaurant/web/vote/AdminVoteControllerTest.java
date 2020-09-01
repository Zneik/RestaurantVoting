package ru.zneik.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zneik.restaurant.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zneik.restaurant.TestUtil.userHttpBasic;
import static ru.zneik.restaurant.UserTestData.ADMIN;
import static ru.zneik.restaurant.VoteTestData.*;
import static ru.zneik.restaurant.web.vote.AdminVoteController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Test
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(TEST_MATCHER.contentJson(List.of(VOTE_2, VOTE_3)));
    }

    @Test
    void getHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/history")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(TEST_MATCHER.contentJson(List.of(VOTE_2, VOTE_3, VOTE_1)));
    }

    @Test
    void getHistoryBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/history")
                .with(userHttpBasic(ADMIN))
                .param("startDate", "2020-08-23")
                .param("endDate", "2020-08-23"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(TEST_MATCHER.contentJson(List.of(VOTE_1)));
    }
}