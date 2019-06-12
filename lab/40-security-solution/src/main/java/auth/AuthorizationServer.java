package auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude= {JpaRepositoriesAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class AuthorizationServer {

	public static void main(String[] args) {
		// This is the name of the properties file to read. Instead of
		// application.properties it will read auth-server.properties.
		System.setProperty("spring.config.name", "auth-server");

		SpringApplication.run(AuthorizationServer.class, args);
	}

}
