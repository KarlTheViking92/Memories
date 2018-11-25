package it.unical.asde2018.memory.components.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.asde2018.memory.components.services.GameService;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping("createGame")
	public String createGame(HttpSession session, Model model/*, @RequestParam String difficulty*/) {
		System.out.println("CREATE GAME GAMECONTROLLER");
		gameService.init();
		model.addAttribute("cards", gameService.getCards());
		return "memory";
	}
	
	@PostMapping("pickCard")
	@ResponseBody
	public String pickCard(HttpSession session, Model model, @RequestParam int id, @RequestParam int count) {
		System.out.println("pick card function");
		System.out.println("id : " + id);
		System.out.println("counter " + count);
		
		return gameService.pickCard(id, count); 
	}

}
