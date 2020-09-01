package ru.zneik.restaurant;


import ru.zneik.restaurant.model.Role;
import ru.zneik.restaurant.model.User;
import ru.zneik.restaurant.to.UserTo;
import ru.zneik.restaurant.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static ru.zneik.restaurant.model.base.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class, "registered", "password");

    public static final int USER_1_ID = START_SEQ;
    public static final int USER_2_ID = START_SEQ + 1;
    public static final int USER_3_ID = START_SEQ + 2;
    public static final int ADMIN_ID = START_SEQ + 3;

    public static final User USER_1 = new User(USER_1_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User USER_2 = new User(USER_2_ID, "VoteUser", "vote_user@mail.ru", "password", Role.USER);
    public static final User USER_3 = new User(USER_3_ID, "TestUser", "test_user@mail.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    static {
    }

    public static User getNewUser() {
        return new User(null, "New", "new@gmail.com", "newPass", true, new Date(), Collections.singleton(Role.USER));
    }

    public static UserTo getNewUserTo() {
        return new UserTo(null, "New", "new@gmail.com", "newPassword");
    }

    public static User getUpdated() {
        User updated = new User(USER_1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
