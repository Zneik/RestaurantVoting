package ru.zneik.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.zneik.restaurant.model.Restaurant;
import ru.zneik.restaurant.repository.RestaurantRepository;
import ru.zneik.restaurant.util.ValidationUtil;
import ru.zneik.restaurant.util.exception.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Cacheable("restaurant")
    public Restaurant getById(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    @Cacheable("restaurantWithMenu")
    public List<Restaurant> getByNow() {
        return restaurantRepository.getByDate(LocalDate.now());
    }

    @Cacheable("restaurantWithMenu")
    public Restaurant getByIdAndDate(Integer id, LocalDate date) {
        return restaurantRepository.getByIdAndDate(id, date)
                .orElseThrow(() -> new NotFoundException("Not found"));
    }

    @CacheEvict(value = {"restaurant", "restaurantWithMenu"}, allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not null");
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurant", "restaurantWithMenu"}, allEntries = true)
    @Transactional
    public Restaurant update(Integer id, Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not null");
        ValidationUtil.assureIdConsistent(restaurant, id);
        getById(id);
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurant", "restaurantWithMenu"}, allEntries = true)
    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }
}