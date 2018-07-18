package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;


import config.RootConfig;

// TODO-01: Take a quick look at pom.xml to see the dependencies we are using.
//          DO NOT modify pom.xml in any way.
//          Note also that this class is already a Spring Boot application
//
// TODO-02: Run it now using Run As -> Spring Boot App (or as Java Application).
//          Go to http://localhost:8080 and you should see a simple home page.
//          None of the links should work (yet).
//          

@SpringBootApplication
@Import(RootConfig.class)
@EntityScan("rewards")
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
