package ru.zneik.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zneik.restaurant.RestaurantTestData;
import ru.zneik.restaurant.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zneik.restaurant.RestaurantTestData.*;
import static ru.zneik.restaurant.TestUtil.userHttpBasic;
import static ru.zneik.restaurant.UserTestData.USER_1;
import static ru.zneik.restaurant.web.restaurant.UserRestaurantController.REST_URL;

class UserRestaurantControllerTest extends AbstractControllerTest {
    @Test
    void getByNow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER.contentJson(RESTAURANT_1, RESTAURANT_2));
    }

    @Test
    void getByIdNow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_1_ID)
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER.contentJson(RESTAURANT_1));
    }

    @Test
    void getByIdNowNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/10")
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}