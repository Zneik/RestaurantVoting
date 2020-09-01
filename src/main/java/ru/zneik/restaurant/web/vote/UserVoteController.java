package ru.zneik.restaurant.web.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.zneik.restaurant.AuthorizedUser;
import ru.zneik.restaurant.model.Vote;
import ru.zneik.restaurant.service.VoteService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.zneik.restaurant.web.vote.UserVoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {
    public final static String REST_URL = "/rest/restaurants";

    private final VoteService voteService;

    public UserVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/votes")
    public List<Vote> getUserHistory(@AuthenticationPrincipal AuthorizedUser user) {
        return voteService.getUserHistory(user.getId());
    }

    @PostMapping("/{restaurantId}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        voteService.create(restaurantId, user.getId(), LocalDateTime.now());
    }

    @PutMapping("/{restaurantId}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        voteService.update(restaurantId, user.getId(), LocalDateTime.now());
    }

    @DeleteMapping("/{restaurantId}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        voteService.delete(restaurantId, user.getId(), LocalDateTime.now());
    }
}
