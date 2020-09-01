package ru.zneik.restaurant.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zneik.restaurant.AuthorizedUser;
import ru.zneik.restaurant.model.User;
import ru.zneik.restaurant.service.UserService;
import ru.zneik.restaurant.to.UserTo;
import ru.zneik.restaurant.util.UserUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.zneik.restaurant.web.user.UserController.REST_URL;


@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    public final static String REST_URL = "/rest/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getCurrent(@AuthenticationPrincipal AuthorizedUser user) {
        return userService.get(user.getId());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User createdUser = userService.create(UserUtil.createNewFromTo(userTo));
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build()
                .toUri();
        return ResponseEntity.created(uri)
                .body(createdUser);
    }
}
