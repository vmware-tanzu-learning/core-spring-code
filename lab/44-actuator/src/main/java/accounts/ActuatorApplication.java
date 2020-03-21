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
 * TODO-02: Run this application. Try these URLs:
 * - http://localhost:8080/actuator - should work
 * - http://localhost:8080/actuator/metrics - fails (404), why?
 *
 * TODO-03 (Optional): Open a terminal window and run "jconsole".
 * (If you are short on time, skip this step.)
 * - Select "accounts.ActuatorApplication" under "Local Process"
 *   then click "Connect"
 * - Click "insecure connection" if prompted
 * - Select the MBeans tab, find the "org.springframework.boot"
 *   folder, then open the "Endpoint" sub-folder
 * - Note that all the actuator endpoints ARE exposed for JMX
 * - Expand Health->Operations->health
 * - Click "health" button on the top right pane
 * - Observe the health data gets displayed
 *
 * TODO-04: Expose some endpoints
 *          (Look for TO-DO-04 in application.properties)
 *
 * TODO-05: Expose all endpoints
 *          (Look for TO-DO-05 in application.properties)
 *
 * TODO-06: Change log level via ./actuator/loggers endpoint
 * - Verify the current logging level of the accounts.web package is DEBUG
 * - Add logger.debug("Logging message within accountSummary()") inside
 *   accountSummary() method
 * - Restart the application and access "/accounts" URL and verify
 *   the log message gets displayed
 * - Change logging level of accounts.web package to INFO using curl
 *   command below (or use Postman)q
 *   curl -i -XPOST -H"Content-Type: application/json" localhost:8080/actuator/loggers/accounts.web -d'{"configuredLevel": "INFO"}'
 * - Access "/accounts" URL (without restarting the application) and verify
 *   the logging message no longer gets displayed
 *
 * TODO-07: Publish build information
 * - Add an appropriate plugin to pom.xml (for Maven) or BuildInfo task to
 *   build.gradle (for Gradle)
 *
 * TODO-08: Add additional build properties to the plugin (for Maven)
 *          or BuildInfo task (for Gradle)
 * - Add "javaVersion" and "operatingSystem" properties
 * - Feel free to see the lab document for more detailed instruction
 *
 * TODO-15: Look for TO-DO-15 in application.properties
 *
 * TODO-18: Restart the application and check the health
 *          indicator - it should be DOWN as there are no restaurants.
 *
 * TODO-19: Modify the `spring.datasource.data` property to use
 *         `data-with-restaurants.sql`. Wait for the application to
 *          restart. Now the heath indicator should be UP.
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
