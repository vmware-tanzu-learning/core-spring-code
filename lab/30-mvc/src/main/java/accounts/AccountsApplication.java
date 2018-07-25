package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import config.RootConfig;

// TODO-01: Take a quick look at pom.xml to see the dependencies we are using,
//          specifically the spring-boot-web-starter.
//          DO NOT modify pom.xml in any way.
//          Note also that this class is already a Spring Boot application
//
// TODO-02: Run it now as a Spring Boot or Java application.
//          Go to http://localhost:8080 and you should see a simple home page.
//          None of the links should work (yet).
//          

@SpringBootApplication
@Import(RootConfig.class)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
