package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import config.AppConfig;

/**
 * TODO-01: Note that the Actuator starter is already set up for you.
 *          (Look for TO-DO-01 in the pom.xml or build.gradle)
 *
 * TODO-02: Run the application. Try these URLs:
 *  - http://localhost:8080/actuator - should work
 *  - http://localhost:8080/actuator/metrics - fails (404), why?
 *
 * TODO-03: Open a terminal window and run `jconsole`.
 *  - Select `accounts.ActuatorApplication` under `Local Process`
 *    then click `Connect`
 *  - Accept the insecure connection if prompted
 *  - Select the MBeans tab, find the `org.springframework.boot`
 *    folder, then open the Endpoint sub-folder
 *  - Note that all the actuator endpoints ARE exposed for JMX
 *
 * TODO-04: Expose some endpoints
 *          (Look for TO-DO-04 in application.properties)
 *
 * TODO-05: Expose all endpoints
 *          (Look for TO-DO-05 in application.properties)
 *
 * TODO-07: Follow the corresponding step in the lab document
 *
 * TODO-08: Follow the corresponding step in the lab document
 *
 * TODO-15: Look for TO-DO-15 in application.properties
 *
 * TODO-18: Restart the application and check the health
 * indicator - it should be DOWN as there are no restaurants.
 *
 * TODO-19: Modify the `spring.datasource.data` property to use
 * `data-with-restaurants.sql`. Wait for the application to
 * restart. Now the heath indicator should be UP.
 * 
 */
@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class ActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

}
