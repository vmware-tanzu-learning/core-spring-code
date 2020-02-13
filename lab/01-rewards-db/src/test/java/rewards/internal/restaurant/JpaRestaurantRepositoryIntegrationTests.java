package rewards.internal.restaurant;

import config.AppConfig;
import config.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for the JPA-based restaurant repository implementation.
 * Verifies that the JpaRestaurantRepository works with its underlying
 * components and that Spring is configuring things properly.
 */
@ActiveProfiles("jpa")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DbConfig.class})
public class JpaRestaurantRepositoryIntegrationTests extends AbstractRestaurantRepositoryTests {

    @Test
    @Override
    public void testProfile() {
        assertTrue(restaurantRepository.getInfo().equals(JpaRestaurantRepository.INFO)
                , "JPA expected but found " + restaurantRepository.getInfo());
    }

}
