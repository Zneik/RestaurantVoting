package ru.zneik.restaurant.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zneik.restaurant.UserTestData;
import ru.zneik.restaurant.model.User;
import ru.zneik.restaurant.util.UserUtil;
import ru.zneik.restaurant.web.AbstractControllerTest;
import ru.zneik.restaurant.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zneik.restaurant.TestUtil.readFromJson;
import static ru.zneik.restaurant.TestUtil.userHttpBasic;
import static ru.zneik.restaurant.UserTestData.USER_1;
import static ru.zneik.restaurant.web.user.UserController.REST_URL;

class UserControllerTest extends AbstractControllerTest {

    @Test
    void getCurrent() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(USER_1));
    }

    @Test
    void register() throws Exception {
        User expectedCreated = UserTestData.getNewUser();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(UserUtil.asTo(expectedCreated))))
                .andDo(print())
                .andExpect(status().isCreated());
        User actual = readFromJson(resultActions, User.class);
        expectedCreated.setId(actual.getId());
        UserTestData.USER_MATCHER.assertMatch(actual, expectedCreated);
    }

    @Test
    void registerValidationError() throws Exception {
        User expectedCreated = UserTestData.getNewUser();
        expectedCreated.setEmail("");
        expectedCreated.setPassword("");
        perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(UserUtil.asTo(expectedCreated))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}