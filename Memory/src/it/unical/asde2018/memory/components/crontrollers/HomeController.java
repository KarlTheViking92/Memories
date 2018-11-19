package it.unical.asde2018.memory.components.crontrollers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.asde2018.memory.components.services.LoginService;

@Controller
public class HomeController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/")
	private String goToIndex(Model model, HttpSession session) {
		return "index";
	}

	@GetMapping("/registration")
	private String registration(Model model, HttpSession session, @RequestParam String username,
			@RequestParam String password) {
		if (!loginService.login(username, password)) {
			model.addAttribute("username", username);
			session.setAttribute("username", username);
			loginService.setCredentials(username, password);
		} else
			model.addAttribute("errorRegistration", "Wrong credentials registration!");

		return "index";
	}

	@GetMapping("/login")
	private String login(Model model, HttpSession session, @RequestParam String username,
			@RequestParam String password) {
		if (loginService.login(username, password)) {
//			model.addAttribute("username", username);
//			session.setAttribute("username", username);
//			loginService.setCredentials(username, password);ù
			return "index";
		} else
			model.addAttribute("errorLogin", "Wrong credentials login!");
		return "index";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		System.out.println("logout");
		session.invalidate();
		return "index";
	}
}
