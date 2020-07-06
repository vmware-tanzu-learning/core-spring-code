package accounts.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "restaurant")
public class RestaurantCustomEndpoint {

    Map<String, String> map = new HashMap<>();

    public RestaurantCustomEndpoint(RestaurantRepository restaurantRepository,
                                    @Value("${info.restaurant.location: New York}") String location) {
        Long restaurantCount = restaurantRepository.getRestaurantCount();
        map.put("restaurant.count", restaurantCount.toString());
        map.put("restaurant.location", location);
    }

    @ReadOperation
    public Map<String, String> readOperation() {
        return map;
    }

    @WriteOperation
    public Map<String, String> writeOperation(String key, String value) {
        map.put(key, value);
        return map;
    }

    @DeleteOperation
    public Map<String, String> deleteOperation() {
        map.clear();
        return map;
    }
}
