package it.unical.asde2018.memory.components.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde2018.memory.components.services.EventService;
import it.unical.asde2018.memory.components.services.GameService;
import it.unical.asde2018.memory.components.services.LobbyService;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private EventService eventService;

	private final String gameEvent = "game";
	private final String lobbyEvent = "lobby";

	@PostConstruct
	private void init() {
		eventService.addEventSource(gameEvent);
		eventService.addEventSource(lobbyEvent);
	}

	@RequestMapping("/createNewLobby")
	public String createNewLobby(@RequestParam String name, HttpSession session, Model model) {
		// YOU HAVE TO SET THAT USER IS THE CREATOR
		Player player = (Player) session.getAttribute("user");

//		player.setCreator(true);
//		session.setAttribute("user", player);
		lobbyService.createLobby(name, player);
		model.addAttribute("createdLobby", "Lobby " + name + " has been created by " + player.getUsername());
		session.setAttribute("lobby", lobbyService.getLobby(name));
		try {
			eventService.addEvent(session.getId(), lobbyEvent, name);
//			eventService.addEvent(id, eventSource, type);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		model.addAttribute("lobbies", lobbyService.getLobbies()); //NO NEED ANYMORE
		return "lobby";
	}

	@PostMapping("/getLobby")
	@ResponseBody
	public String getLobby(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			System.out.println("get Lobby list");
//	      User user = (User) session.getAttribute("user");
			JSONObject lob = createLobbyJSON(session);
			return lob.toJSONString();
		}
		return null;
	}

	private JSONObject createLobbyJSON(HttpSession session) {

		JSONObject obj = new JSONObject();
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		Player cUser = (Player) session.getAttribute("user");
		List<Player> players = lobbyService.getPlayers(lobby.getName());
		obj.put("user", cUser.getUsername());
		obj.put("creator", lobby.getCreator().getUsername());
		obj.put("playerSize", lobby.getNumberOfPlayers());
//		jsonArray.add(obj);
		JSONArray jsonArray = new JSONArray();
		System.out.println("lobby: " + lobby.getName());
		for (Player player : players) {
			JSONObject jsonPlayer = new JSONObject();
			jsonPlayer.put("player", player.getUsername());
			jsonArray.add(jsonPlayer);
		}
		obj.put("players", jsonArray);
		return obj;
	}

	@RequestMapping({ "/joinLobby" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		System.out.println("JOIN LOBBY FUNCT");
		if (!lobbyService.fullLobby(name)) {
			Player player = (Player) session.getAttribute("user");
			lobbyService.joinLobby(name, player);
			try {
				eventService.addEvent(session.getId(), lobbyEvent, "joined");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		return "listLobbies";
	}

	@RequestMapping("/refreshLobby")
	public String refreshLobby(HttpSession session, Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "listLobbies";
	}

	@PostMapping("/createGame")
	@ResponseBody
	public String createGame(HttpSession session, Model model, @RequestParam String difficulty) {
		System.out.println("CREATE GAME function");
		Player player = (Player) session.getAttribute("user");
		System.out.println(player.getUsername() + " vuole iniziare la partita ");
		Lobby lobbyPlayer = lobbyService.getLobbyPlayer(player);
		System.out.println("Voglio creare la partita per la lobby " + lobbyPlayer.getName());
		System.out.println("l'ha creata " + lobbyPlayer.getCreator().getUsername());
		if (lobbyPlayer.getCreator().equals(player)) {
			System.out.println("il creatore vuole iniziare la partita");
			if (lobbyPlayer.full()) {
				System.out.println("Tutto bene creo la partita con difficoltà " + difficulty);
				String gameID = gameService.createGame(lobbyPlayer.getPlayers(), Difficulty.valueOf(difficulty));
//				model.addAttribute("game", gameID);
				session.setAttribute("game", gameID);
//				model.addAttribute("cards", gameService.getCards(gameID));
				return "getGame";
			} else {
				System.out.println("lobby non piena");
			}
		}
		return "";
	}

	@GetMapping("getGame")
	public String getGame(HttpSession session, Model model) {
		model.addAttribute("cards", gameService.getCards((String) session.getAttribute("game")));
		return "memory";
	}

	@PostMapping("pickCard")
	@ResponseBody
	public String pickCard(HttpSession session, Model model, @RequestParam int imageId, @RequestParam int position) {
//		System.out.println("pick card function");
//		System.out.println("id : " + imageId);
//		System.out.println("counter " + position);

		return gameService.pickCard((String) session.getAttribute("game"), (Player) session.getAttribute("user"),
				imageId, position);
	}

	@GetMapping("/getEvents")
	@ResponseBody
	public DeferredResult<String> getEvents(HttpSession session, @RequestParam String eventSource) {

		System.out.println("CALL GET EVENTS Controller");
		DeferredResult<String> output = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {
			try {
				System.out.println("session id " + session.getId());
				output.setResult(eventService.nextEvent(session.getId(), eventSource));
			} catch (InterruptedException e) {
				output.setResult("An error occurred during event retrieval");
			}
		});

		return output;
	}

}
