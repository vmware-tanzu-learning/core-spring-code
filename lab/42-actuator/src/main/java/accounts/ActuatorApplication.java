package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import config.AppConfig;

/**
 * TODO-01: Look for TO-DO-01 in the pom.xml or build.gradle
 * <p>
 * TODO-02: Run the application. Try these URLs:
 * <li>http://localhost:8080/actuator - should work
 * <li>http://localhost:8080/actuator/metrics - fails (404), why?
 * <p>
 * TODO-03: Open a terminal window and run `jconsole`.
 * <li>Select `accounts.ActuatorApplication` under `Local Process`
 * then click `Connect`
 * <li>Accept the insecure connection if prompted
 * <li>Select the MBeans tab, find the `org.springframework.boot` folder, then
 * open the Endpoint sub-folder
 * <li>Note that all the actuator endpoints ARE enabled for JMX
 * <p>
 * TODO-04: Look for TO-DO-04 in application.properties
 * <p>
 * TODO-05: Look for TO-DO-05 in application.properties
 * <p>
 * TODO-13: Look for TO-DO-13 in application.properties
 * <p>
 * TODO-16: Restart the application and check the health
 * indicator - it should be DOWN as there are no restaurants.
 *<p>
 * TODO-17: Modify the `spring.datasource.data` property to use
 * `data-with-restaurants.sql`.  Wait for the application to
 * restart. Now the heath indicator should be UP.

 */
@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class ActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

}
