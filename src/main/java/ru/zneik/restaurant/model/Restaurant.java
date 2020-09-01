package ru.zneik.restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.zneik.restaurant.model.base.AbstractNamedEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {
        @UniqueConstraint(name = "restaurants_unique_name_idx", columnNames = {"name"})
})
public class Restaurant extends AbstractNamedEntity {
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
