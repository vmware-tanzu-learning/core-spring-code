package accounts.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// TODO-07d: Refactor both methods to only return a logical view name
// Once the application restarts, check the web-site still works.
public class HelloController {

	@RequestMapping("/")
	public String home() {
		return "classpath:/templates/index.html";
	}

	// TODO-03: Time to write a simple controller method.
	//
	// Add a new method called hello and make it respond to URLs ending in /hello.
	// You will need to use the Model and set the name attribute to "Spring Boot".
	// Return the view-name "classpath:/templates/welcome.html.
	//
	// NOTE: src/main/resources/templates/welcome.html already exists - if you look
	// inside you will see it is expecting an attribute {{name}}. It is deliberately
	// minimal to keep things simple.

	// TODO-04: The application should automatically restart because we included the
	// spring-boot-devtools dependency. Go to the home page an click the "Hello
	// page" link. You should see the text "Hello Spring Boot" displayed.
}
