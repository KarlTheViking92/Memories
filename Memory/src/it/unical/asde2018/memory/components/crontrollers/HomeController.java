package it.unical.asde2018.memory.components.crontrollers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	private String goToIndex(Model model, HttpSession session) {
		return "index";
	}
}
