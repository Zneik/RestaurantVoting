package ru.zneik.restaurant;

import ru.zneik.restaurant.model.Dish;

import java.time.LocalDate;

import static ru.zneik.restaurant.model.base.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class);

    public final static int DISH_1_ID = START_SEQ + 7;
    public final static int DISH_2_ID = START_SEQ + 8;
    public final static int DISH_3_ID = START_SEQ + 9;
    public final static int DISH_4_ID = START_SEQ + 10;
    public final static int DISH_5_ID = START_SEQ + 11;

    public final static Dish DISH_1 = new Dish(DISH_1_ID, "Филе форели", 300, LocalDate.of(2020, 8, 23));
    public final static Dish DISH_2 = new Dish(DISH_2_ID, "Борщ", 100, LocalDate.now());
    public final static Dish DISH_3 = new Dish(DISH_3_ID, "Куриное филе", 300, LocalDate.now());
    public final static Dish DISH_4 = new Dish(DISH_4_ID, "Котлета", 50, LocalDate.now());
    public final static Dish DISH_5 = new Dish(DISH_5_ID, "Щи", 70, LocalDate.now());

    public static Dish getNew() {
        return new Dish("Картофельное пюре", 30);
    }

    public static Dish getUpdated() {
        Dish dish = new Dish(DISH_2);
        dish.setName("Ролл");
        return dish;
    }
}
