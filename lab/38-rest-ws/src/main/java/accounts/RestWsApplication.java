package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import config.AppConfig;
import config.DbConfig;

@SpringBootApplication
@Import({AppConfig.class,DbConfig.class})
public class RestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWsApplication.class, args);
    }
    
	//	TODO 01: Run this Spring Boot application 
    //	(first make sure that you are not still running an application from a prior lab)
    //	Verify you can reach http://localhost:8080 from a browser. 
    
}
