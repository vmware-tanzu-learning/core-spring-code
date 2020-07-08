package accounts.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

// TODO-24: Run the tests checking security configuration for the actuator endpoints
// - Take some time to understand what each test is for
// - Remove @Disabled annotation from each test and run it
// - Make sure all tests pass
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountClientSecurityTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    public void any_user_can_access_health_endpoint() {
        ResponseEntity<String> responseEntity
                = restTemplate.getForEntity("/actuator/health", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Disabled
    public void any_user_can_access_info_endpoint() {
        ResponseEntity<String> responseEntity
                = restTemplate.getForEntity("/actuator/info", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Disabled
    public void any_user_cannot_access_conditions_endpoint() {
        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("anyuser", "anyuser")
                              .getForEntity("/actuator/conditions", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Disabled
    public void user_in_ADMIN_role_can_access_conditions_endpoint() {
        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("admin", "admin")
                              .getForEntity("/actuator/conditions", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Disabled
    public void user_in_ACTUATOR_role_cannot_access_conditions_endpoint() {
        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("actuator", "actuator")
                              .getForEntity("/actuator/conditions", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}