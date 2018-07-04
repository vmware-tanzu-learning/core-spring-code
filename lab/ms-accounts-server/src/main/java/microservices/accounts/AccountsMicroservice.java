package microservices.accounts;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Once configured, this should run as a micro-service, registering with the
 * Discovery Server (Eureka).
 * <p>
 * Run this process SECOND.
 */
// TODO-06: Add annotations to make this a Spring Boot application and to ensure
// registration with the Discovery Server. Otherwise this is just a cut-down
// version of the REST Service you built earlier.
// Ignore any httpMapperProperties deprecated warning

// TODO-07: Run this as a Spring Boot application - make sure the Registration
// Server is still running. In a browser open this URL:
// http://localhost:NNNN - where NNNN is the port number
// Do you know where this port is defined?

// TODO-08: Has it registered with the Registration Server yet? Check the
// registration-server's dashboard at http://localhost:1111/
// You may have to wait a minute or so for this to occur.
//
// TODO-09: Finally go to ms-web-client and follow the TO DO steps there.

// This annotation gets Spring Boot to configure where JPA looks for Entities
@EntityScan("rewards")
public class AccountsMicroservice {

	protected Logger logger = Logger.getLogger(AccountsMicroservice.class.getName());

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AccountsMicroservice.class, args);
	}

	/**
	 * This service needs access to the accounts database. This is the same
	 * JpaAccountManager we have been using since the MVC lab.
	 * 
	 * @return An Account manager that uses JPA to access Account data.
	 */
	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}

}
