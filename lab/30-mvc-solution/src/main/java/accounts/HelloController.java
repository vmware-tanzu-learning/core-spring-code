package accounts;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/hello")
	public String hi(Model model) {
		model.addAttribute("name", "Spring Boot");
		return "welcome";
	}

}
