package ru.zneik.restaurant.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zneik.restaurant.DishTestData;
import ru.zneik.restaurant.model.Dish;
import ru.zneik.restaurant.service.DishService;
import ru.zneik.restaurant.util.exception.NotFoundException;
import ru.zneik.restaurant.web.AbstractControllerTest;
import ru.zneik.restaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zneik.restaurant.DishTestData.*;
import static ru.zneik.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static ru.zneik.restaurant.TestUtil.readFromJson;
import static ru.zneik.restaurant.TestUtil.userHttpBasic;
import static ru.zneik.restaurant.UserTestData.ADMIN;
import static ru.zneik.restaurant.web.dish.AdminDishController.REST_URL;

class AdminDishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes/" + DISH_2_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DishTestData.TEST_MATCHER.contentJson(DISH_2));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes/10")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/" + RESTAURANT_1_ID + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DishTestData.TEST_MATCHER.contentJson(List.of(DISH_1, DISH_2, DISH_3, DISH_4)));
    }

    @Test
    void create() throws Exception {
        Dish expectedCreated = getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expectedCreated)))
                .andDo(print())
                .andExpect(status().isCreated());
        Dish actual = readFromJson(resultActions, Dish.class);
        expectedCreated.setId(actual.getId());
        expectedCreated.setDate(actual.getDate());
        TEST_MATCHER.assertMatch(actual, expectedCreated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createNotFoundRestaurant() throws Exception {
        Dish expectedCreated = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/10/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expectedCreated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Dish dish = new Dish(DISH_2);
        dish.setId(null);
        dish.setRestaurant(null);
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(dish)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        Dish expectedUpdated = getUpdated();
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/" +
                RESTAURANT_1_ID +
                "/dishes/" +
                DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expectedUpdated)))
                .andDo(print())
                .andExpect(status().isOk());
        Dish actual = readFromJson(resultActions, Dish.class);
        TEST_MATCHER.assertMatch(actual, expectedUpdated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Dish dish = new Dish(DISH_3);
        dish.setId(DISH_2_ID);
        perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/" +
                RESTAURANT_1_ID +
                "/dishes/" +
                DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(dish)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateNotFoundRestaurant() throws Exception {
        Dish expectedUpdated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/10/dishes/" + DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expectedUpdated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/dishes/" + DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(DISH_2_ID));
    }

}