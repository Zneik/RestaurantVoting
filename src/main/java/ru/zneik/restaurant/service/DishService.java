package ru.zneik.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.zneik.restaurant.model.Dish;
import ru.zneik.restaurant.model.Restaurant;
import ru.zneik.restaurant.repository.DishRepository;
import ru.zneik.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantService restaurantService;

    public DishService(DishRepository dishRepository,
                       RestaurantService restaurantService) {
        this.dishRepository = dishRepository;
        this.restaurantService = restaurantService;
    }

    public Dish getById(int dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found"));
    }

    public List<Dish> getByRestaurant(int restaurantId) {
        restaurantService.getById(restaurantId);
        return dishRepository.getByRestaurant(restaurantId);
    }

    @CacheEvict(value = {"restaurantWithMenu"}, allEntries = true)
    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must not null");
        Restaurant restaurant = restaurantService.getById(restaurantId);
        dish.setRestaurant(restaurant);
        dish.setDate(LocalDate.now());
        return dishRepository.save(dish);
    }

    @CacheEvict(value = {"restaurantWithMenu"}, allEntries = true)
    @Transactional
    public Dish update(Dish dish, int restaurantId, int dishId) {
        Assert.notNull(dish, "Dish must not null");
        getById(dishId);
        Restaurant restaurant = restaurantService.getById(restaurantId);
        dish.setRestaurant(restaurant);
        dish.setDate(LocalDate.now());
        return dishRepository.save(dish);
    }

    @CacheEvict(value = {"restaurantWithMenu"}, allEntries = true)
    @Transactional
    public void delete(int dishId) {
        dishRepository.deleteById(dishId);
    }

}
