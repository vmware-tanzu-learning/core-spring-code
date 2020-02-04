package accounts.services;

import accounts.RestWsApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

// TODO-12: Perform end-to-end security testing with a running server
// - Take some time to understand what each test is for
// - Remove @Disabled annotation from each test and run it
// - Make sure all tests pass

@SpringBootTest(classes = {RestWsApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountServiceMethodLevelSecurityTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    void getAuthoritiesForUser_should_return_403_for_user() {

        ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("user", "user")
                                                         .getForEntity("/authorities?username=user", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Disabled
    void getAuthoritiesForUser_should_return_authorities_for_admin() {

        String[] authorities = restTemplate.withBasicAuth("admin", "admin")
                                           .getForObject("/authorities?username=admin", String[].class);
        assertThat(authorities.length).isEqualTo(2);
        assertThat(authorities.toString().contains("ROLE_ADMIN"));
        assertThat(authorities.toString().contains("ROLE_USER"));

    }

    @Test
    @Disabled
    void getAuthoritiesForUser_should_return_authorities_for_superadmin() {

        String[] authorities = restTemplate.withBasicAuth("superadmin", "superadmin")
                                           .getForObject("/authorities?username=superadmin", String[].class);
        assertThat(authorities.length).isEqualTo(3);
        assertThat(authorities.toString().contains("ROLE_SUPERADMIN"));
        assertThat(authorities.toString().contains("ROLE_ADMIN"));
        assertThat(authorities.toString().contains("ROLE_USER"));
    }

}