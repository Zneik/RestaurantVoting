package ru.zneik.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zneik.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);

    Optional<Vote> getByUserIdAndRestaurantIdAndDate(int userId, int restaurantId, LocalDate date);

    List<Vote> getByUserIdOrderByDateDesc(@Param("userId") int userId);

    List<Vote> getByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.date>=:startDate and v.date<=:endDate ORDER BY v.date DESC")
    List<Vote> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
