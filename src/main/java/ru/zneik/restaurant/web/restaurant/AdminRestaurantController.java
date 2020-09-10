package ru.zneik.restaurant.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zneik.restaurant.model.Restaurant;
import ru.zneik.restaurant.service.RestaurantService;
import ru.zneik.restaurant.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zneik.restaurant.web.restaurant.AdminRestaurantController.REST_URL;


@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    public final static String REST_URL = "/rest/admin/restaurants";

    private final RestaurantService restaurantService;

    public AdminRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant getById(@PathVariable int id) {
        return restaurantService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        final Restaurant createdRestaurant = restaurantService.create(restaurant);
        final URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdRestaurant.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(createdRestaurant);
    }

    @PutMapping(value = "/{id}")
    public Restaurant update(@PathVariable int id, @Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        return restaurantService.update(id, restaurant);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
