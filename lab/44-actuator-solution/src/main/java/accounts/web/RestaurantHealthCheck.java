package accounts.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

@Component
public class RestaurantHealthCheck implements HealthIndicator {
    private final RestaurantRepository restaurantRepository;

    public RestaurantHealthCheck(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Health health() {
        Long restaurantCount = restaurantRepository.getRestaurantCount();
        if (restaurantCount > 0) {
            return Health.up()
                         .withDetail("restaurantCount", restaurantCount)
                         .build();
        } else {
            return Health.status("NO_RESTAURANTS")
                         .build();
        }
    }
}
