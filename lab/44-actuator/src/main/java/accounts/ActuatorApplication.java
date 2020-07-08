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
 * TODO-03: Expose some endpoints
 *          (Look for TO-DO-03 in application.properties)
 *
 * TODO-04: Expose all endpoints
 *          (Look for TO-DO-04 in application.properties)
 *
 * TODO-05: Change log level via ./actuator/loggers endpoint
 * - Verify the current logging level of the "accounts.web" package is DEBUG
 * - Add "logger.debug("Logging message within accountSummary()");" inside
 *   "accountSummary()" method in the "AccountController" class - this
 *   message will be used to verify if we can change the
 *   logging level without restarting the application
 * - Restart the application and access "/accounts" URL and verify
 *   the log message gets displayed
 * - Change logging level of "accounts.web" package to INFO using curl
 *   command below (or use Postman)
 *   curl -i -XPOST -H"Content-Type: application/json" localhost:8080/actuator/loggers/accounts.web -d'{"configuredLevel": "INFO"}'
 * - Access "/accounts" URL (WITHOUT restarting the application) and verify
 *   the logging message no longer gets displayed
 *
 * TODO-06: Publish build information
 * - Add an appropriate plugin to pom.xml (for Maven) or BuildInfo task to
 *   build.gradle (for Gradle)
 * - Rebuild the application preferably at the command line
 *   ./mvnw -pl 00-rewards-common -pl 01-rewards-db -pl 44-actuator clean install (for Maven)
 *   ./gradlew 44-actuator:clean 44-actuator:build (for Gradle)
 * - Restart the application and access "info" endpoint and verify the build
 *   info gets displayed
 *
 * TODO-07: Add additional build properties to the plugin (for Maven)
 *          or BuildInfo task (for Gradle)
 * - Add "javaVersion" and "operatingSystem" properties
 * - Feel free to see the lab document or solution project for
 *   more detailed instruction
 * - Restart the application and access "info" endpoint and verify
 *   additional build properties are displayed
 *
 * ------------------------------------------------
 *
 * TODO-14: Look for TO-DO-14 in application.properties
 *
 * ------------------------------------------------
 *
 * TODO-17: Verify the behavior of custom health indicator
 * - Let the application to restart (via devtools)
 * - Access the health indicator - it should be DOWN as there are no restaurants.
 *
 * TODO-18: Verify the behavior of custom health indicator with change
 * - Modify the `spring.datasource.data` property in the application.properties
 *   to use `data-with-restaurants.sql`
 * - Let the application to restart (via devtools)
 * - Access the health indicator - it should be UP this time
 *
 * ------------------------------------------------
 *
 * TODO-20: Look for "TO-DO-20: Organize health indicators into groups" in the application.properties
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

/*
 * TODO-27 (Optional): Run "jconsole" (from <JDK-directory>/bin)
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
 */