package accounts.web;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

// This is a HealthIndicator example that uses the AbstractHealthIndicator
@Component
public class RestaurantHealthCheck2 extends AbstractHealthIndicator {
    private final RestaurantRepository restaurantRepository;

    public RestaurantHealthCheck2(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        Long restaurantCount = restaurantRepository.getRestaurantCount();
        if (restaurantCount > 0) {
            builder.up()
                   .withDetail("restaurantCount", restaurantCount);
        } else {
            builder.status("NO_RESTAURANTS");
        }
    }
}
