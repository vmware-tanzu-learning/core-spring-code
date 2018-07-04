package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * This will become the Web front-end for the microservices application.
 * <p>
 * Start this process LAST.
 */
@SpringBootApplication

// TODO-09: Annotate this class as a Discovery Server client
public class AccountsWebApplication {

	// In accounts-microservice.properties, spring.application.name is set to
	// "accounts-microservice". The service registers under this name, so now
	// we ask for the service with that name. Upper case is used by convention
	// (to imply it is not a REAL hostname but a microservice name), but upper
	// or lower case both work.

	// TODO-10: Set the URL to use - read the comment above for help
	public static final String ACCOUNTS_SERVICE_URL = "http://TODO";


	public static void main(String[] args) {
		SpringApplication.run(AccountsWebApplication.class, args);
	}


	// TODO-11: We will need a load-balanced RestTemplate bean. Add a new @Bean
    // method to create it here.
    
	
    // The remote account manager will fetch Account info from the Accounts
    // Microservice
	@Bean
	public AccountManager accountManager() {
		// TODO-12: Note that we will be using the RemoteAccountManager to access
		//          account information from the Microservice.  We work on this
		//          class next.  Move on to the next step, nothing to do here.
		return new RemoteAccountManager(ACCOUNTS_SERVICE_URL);
	}
	
	// TODO-16: Once RemoteAccountManager is configured, run this as a Spring
	//          Boot application.  Go to the home page: http://localhost:8080
	//          Can you list and view accounts?  If you get service not found 
	//          errors, you may have to wait a minute for this application to
	//          locate the Accounts Microservice. Check: do both microservices
    //          show up in the Registration Service dashboard?
}
