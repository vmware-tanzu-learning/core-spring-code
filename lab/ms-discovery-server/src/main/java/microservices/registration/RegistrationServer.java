package microservices.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Once modified, this is all you need to run a Eureka registration server.
 * <p>
 * Start this process FIRST.
 */
// TODO-01: Add annotations to make this a Eureka server using Spring Boot
//
// Older versions of STS/Eclipse may generate an httpMapperProperties deprecated
// warning, which can safely be ignored.
public class RegistrationServer {

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// TODO-02: Configure this to run using SpringApplication

		// TODO-03: Run this as a Spring Boot application by doing
		// right-click -> Run As -> Spring Boot App
		// Then open the server's Dashboard at http://localhost:1111/ in
		// a browser
		//
		// TODO-04: Why is this server's port 1111? Where was this configured?
		//
		// TODO-05: Now go to the ms-account-server project - follow its
		// TO DO instructions.
	}

}
