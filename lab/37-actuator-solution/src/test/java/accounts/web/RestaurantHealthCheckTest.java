package accounts.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import rewards.internal.restaurant.JpaRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class RestaurantHealthCheckTest {
    private RestaurantHealthCheck restaurantHealthCheck;
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp() {
        restaurantRepository = mock(JpaRestaurantRepository.class);
        restaurantHealthCheck = new RestaurantHealthCheck(restaurantRepository);
    }

    @Test
    public void testHealthReturnsUpIfThereAreRestaurants() {
        doReturn(1L).when(restaurantRepository).getRestaurantCount();

        Health result = restaurantHealthCheck.health();

        verify(restaurantRepository).getRestaurantCount();
        assert(result.getStatus()).equals(Status.UP);
    }

    @Test
    public void testHealthReturnsDownIfThereAreNoRestaurants() {
        doReturn(0L).when(restaurantRepository).getRestaurantCount();

        Health result = restaurantHealthCheck.health();

        verify(restaurantRepository).getRestaurantCount();
        assert(result.getStatus()).equals(Status.DOWN);
    }
}
