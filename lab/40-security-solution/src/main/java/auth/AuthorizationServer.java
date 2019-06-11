package auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude= {JpaRepositoriesAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class AuthorizationServer {

	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

	public static final String ACCOUNT_READ = "account.read";
	public static final String ACCOUNT_WRITE = "account.write";

	public static void main(String[] args) {
		// This is the name of the properties file to read. Instead of
		// application.properties it will read account-server.properties.
		System.setProperty("spring.config.name", "auth-server");

		SpringApplication.run(AuthorizationServer.class, args);
	}

}
