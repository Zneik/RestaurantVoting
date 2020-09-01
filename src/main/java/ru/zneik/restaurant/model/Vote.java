package ru.zneik.restaurant.model;

import ru.zneik.restaurant.model.base.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(name = "votes_unique_user_date_idx", columnNames = {
                "user_id",
                "date"
        })
})
public class Vote extends AbstractBaseEntity {
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Vote() {
    }

    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDate date) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurant_id) {
        this.restaurantId = restaurant_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
