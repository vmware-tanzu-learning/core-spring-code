package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

// TODO-01: Verify the presence of Spring security dependencies
// - See TO-DO-01 in the pom.xml for Maven or build.gradle for Gradle

// TODO-02: Observe the default behaviour of the Spring Boot application
// - Run this application
// - Try to access "http://localhost:8080/accounts" and observe that
//   browser generated login popup gets displayed
// - Enter "user" in the username field and Spring Boot generated password
//   into the password field.
//   (If the browser keeps displaying the login page, try to
//   use Chrome Incognito browser.)


@SpringBootApplication
// TODO-03: Import security configuration
// - Uncomment the line below
//@Import(SecurityConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestWsApplication.class, args);
    }

}
