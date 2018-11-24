package it.unical.asde2018.memory.components.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.asde2018.memory.components.services.LobbyService;
import it.unical.asde2018.memory.components.services.LoginService;
import it.unical.asde2018.memory.model.Player;

@Controller
public class HomeController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LobbyService lobbyService;

	@GetMapping("/")
	private String goToIndex(Model model, HttpSession session) {
		return "index";
	}

	@GetMapping("/registration")
	private String registration(Model model, @RequestParam String username, @RequestParam String password) {
		if (!loginService.login(username, password)) {
			loginService.setCredentials(username, password);
			model.addAttribute("errorRegistration", username + " you are now registed, you can Sign in");
		} else
			model.addAttribute("errorRegistration", username + " has already been chosen has username");
		return "index";
	}

	@GetMapping("/login")
	private String login(Model model, HttpSession session, @RequestParam String username,
			@RequestParam String password) {
		if (loginService.login(username, password)) {
			// SEE WHERE WE NEED USERNAME AND CHANGE IT TO USER.USERNAME
//			Credentials user = new Credentials(username, password);
			Player player = new Player(username);
			session.setAttribute("user", player);
			model.addAttribute("lobbies", lobbyService.getLobbies());
			return "listLobbies";
		} else
			model.addAttribute("errorLogin", "Wrong credentials login!");
		return "index";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		return "index";
	}

//
	@RequestMapping("/createLobby")
	public String createLobby() {
		return "createLobby";
	}

	@RequestMapping("/joinListLobby")
	public String listLobbies(Model model, HttpSession session) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "listLobbies";
	}

	@RequestMapping("/createNewLobby")
	public String createNewLobby(@RequestParam String name, HttpSession session, Model model) {
		// YOU HAVE TO SET THAT USER IS THE CREATOR
		Player player = (Player) session.getAttribute("user");

//		player.setCreator(true);
//		session.setAttribute("user", player);
		lobbyService.createLobby(name, player, 2);
		model.addAttribute("createdLobby", "Lobby " + name + " has been created by " + player.getUsername());
		session.setAttribute("lobby", lobbyService.getLobby(name));
		// model.addAttribute("lobbies", lobbyService.getLobbies()); //NO NEED ANYMORE
		return "lobby";
	}

	@RequestMapping({ "/joinLobby" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		if (lobbyService.notFullLoby(name)) {
			Player player = (Player) session.getAttribute("user");
			lobbyService.joinLobby(name, player);
//			model.addAttribute("players", lobbyService.getPlayers(name));
//			model.addAttribute("nameLobby", name);

			session.setAttribute("lobby", lobbyService.getLobby(name));
			System.out.println(lobbyService.getPlayers(name));
			// session
			return "lobby";
		} else {
			model.addAttribute("errorLobby", name + " is full");
			model.addAttribute("lobbies", lobbyService.getLobbies());
		}
		return "lobby";
	}

	@RequestMapping({ "/leaveLobby" })
	public String leaveLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		Player player = (Player) session.getAttribute("user");
		lobbyService.leaveLobby(name, player);
		model.addAttribute("lobbies", lobbyService.getLobbies());
//		if(player.equals(lobbyService.getLobby(name).getCreator())){
//			
//		}
		return "listLobbies";
	}

	@RequestMapping("/refreshLobby")
	public String refreshLobby(HttpSession session, Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "listLobbies";
	}

	@RequestMapping({ "/startGame" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model) {
		return "game";
	}

	@RequestMapping("/rules")
	public String rules(HttpSession session, Model model) {
		return "rules";
	}

//	@RequestMapping("/matchHistory")
//	public String matchHistory(HttpSession session, Model model) {
//		model.addAttribute("games", gameService.init());
//		List<Player> list = loginService.getAllUsers();
//		System.out.println("__________-----__----" + list.size());
//		for (Player player : list) {
//			System.out.println(player.getUsername());
//		}
//		return "matchHistory";
//	}

}
