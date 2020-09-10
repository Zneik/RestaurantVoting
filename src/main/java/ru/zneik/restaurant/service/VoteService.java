package ru.zneik.restaurant.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.zneik.restaurant.model.Vote;
import ru.zneik.restaurant.repository.VoteRepository;
import ru.zneik.restaurant.util.exception.ExistVoteException;
import ru.zneik.restaurant.util.exception.NotFoundException;
import ru.zneik.restaurant.util.exception.UnsupportedVoteTime;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {
    public static final LocalTime END_VOTING_TIME = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;

    public VoteService(VoteRepository voteRepository,
                       RestaurantService restaurantService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
    }

    public Vote getByUserIdAndDate(int userId, LocalDate todayDate) {
        return voteRepository.getByUserIdAndDate(userId, todayDate)
                .orElseThrow(() -> new NotFoundException("Not found vote"));
    }

    @Transactional
    public void create(int restaurantId, int userId, LocalDateTime dateTime) {
        LocalDate todayDate = LocalDate.now();
        if (voteRepository.getByUserIdAndDate(userId, todayDate).isPresent()) {
            throw new ExistVoteException("User is voting today");
        }
        restaurantService.getByIdAndDate(restaurantId, todayDate);
        Vote vote = new Vote(null, userId, restaurantId, dateTime.toLocalDate());
        voteRepository.save(vote);
    }

    @Transactional
    public void update(int restaurantId, int userId, LocalDateTime dateTime) {
        checkTime(dateTime);
        Vote vote = getByUserIdAndDate(userId, dateTime.toLocalDate());
        restaurantService.getByIdAndDate(restaurantId, LocalDate.now());
        vote.setRestaurantId(restaurantId);
        voteRepository.save(vote);
    }

    @Transactional
    public void delete(int restaurantId, int userId, LocalDateTime dateTime) {
        checkTime(dateTime);
        long countDeleted = voteRepository.deleteByRestaurantIdAndUserIdAndAndDate(restaurantId,
                userId,
                dateTime.toLocalDate());
        LoggerFactory.getLogger(VoteService.class).info(String.valueOf(countDeleted));
        if (countDeleted == 0) {
            throw new ExistVoteException("User not vote today");
        }
    }

    private void checkTime(LocalDateTime dateTime) {
        LocalDate todayDate = LocalDate.now();
        if (dateTime.toLocalDate().equals(todayDate) && dateTime.toLocalTime().isAfter(END_VOTING_TIME)) {
            throw new UnsupportedVoteTime("Vote time is end");
        }
    }

    public List<Vote> getUserHistory(int userId) {
        return voteRepository.getByUserIdOrderByDateDesc(userId);
    }

    public List<Vote> getByDate(LocalDate date) {
        return voteRepository.getByDate(date);
    }

    public List<Vote> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        return voteRepository.getBetween(startDate == null ?
                        LocalDate.of(1, 1, 1) :
                        startDate,
                endDate == null ?
                        LocalDate.now().plusDays(1) :
                        endDate);
    }

}
