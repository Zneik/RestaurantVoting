package ru.zneik.restaurant.web.dish;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zneik.restaurant.model.Dish;
import ru.zneik.restaurant.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zneik.restaurant.web.dish.AdminDishController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    public final static String REST_URL = "/rest/admin";

    private final DishService dishService;

    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes/{id}")
    public Dish get(@PathVariable int id) {
        return dishService.getById(id);
    }

    @GetMapping("/restaurants/{restaurantId}/dishes")
    public List<Dish> getByRestaurant(@PathVariable int restaurantId) {
        return dishService.getByRestaurant(restaurantId);
    }

    @PostMapping("/restaurants/{restaurantId}/dishes")
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        Dish createdDish = dishService.create(dish, restaurantId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/dishes/{id}")
                .buildAndExpand(createdDish.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(createdDish);
    }

    @PutMapping("/restaurants/{restaurantId}/dishes/{dishId}")
    public Dish update(@PathVariable int restaurantId,
                       @PathVariable int dishId,
                       @Valid @RequestBody Dish dish) {
        return dishService.update(dish, restaurantId, dishId);
    }

    @DeleteMapping("/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        dishService.delete(dishId);
    }

}
