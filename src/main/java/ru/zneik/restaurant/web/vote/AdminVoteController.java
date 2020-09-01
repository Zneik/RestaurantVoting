package ru.zneik.restaurant.web.vote;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zneik.restaurant.model.Vote;
import ru.zneik.restaurant.service.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ru.zneik.restaurant.web.vote.AdminVoteController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    public final static String REST_URL = "/rest/admin/votes";

    private final VoteService voteService;

    public AdminVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public List<Vote> getToday() {
        return voteService.getByDate(LocalDate.now());
    }

    @GetMapping("/history")
    public List<Vote> getHistory(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return voteService.getBetweenDate(startDate, endDate);
    }
}
