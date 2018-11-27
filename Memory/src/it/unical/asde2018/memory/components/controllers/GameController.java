package it.unical.asde2018.memory.components.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.asde2018.memory.components.services.GameService;
import it.unical.asde2018.memory.model.Player;
import it.unical.asde2018.memory.model.Game.Difficulty;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@PostMapping("createGame")
	public String createGame(HttpSession session, Model model, @RequestParam int difficulty) { //@RequestBody List<Player> players) {
		System.out.println("CREATE GAME GAMECONTROLLER");
		System.out.println(difficulty);
		List<Player> players = new ArrayList<>();
		players.add(new Player("ciccio"));
		players.add(new Player("pippo"));
		Difficulty d;
		if(difficulty == 1) {
			d = Difficulty.EASY;
		}else if(difficulty == 2) {
			d = Difficulty.NORMAL;
		}else {
			d = Difficulty.HARD;
		}
		
		//capire che fare con l'ID della partita. Lo metto nell'oggetto lobby.
		String gameId = gameService.createGame(players, d);
		session.setAttribute("gameId", gameId);
		model.addAttribute("cards", gameService.getCards((String)session.getAttribute("gameId")));;
		return "memory";
	}
	
	@PostMapping("pickCard")
	@ResponseBody
	public String pickCard(HttpSession session, Model model, @RequestParam int imageId, @RequestParam int position) {
		System.out.println("pick card function");
		System.out.println("id : " + imageId);
		System.out.println("counter " + position);
		
		return gameService.pickCard((String)session.getAttribute("gameId"), imageId, position); 
	}

}
