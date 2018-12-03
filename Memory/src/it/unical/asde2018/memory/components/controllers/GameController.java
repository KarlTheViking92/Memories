package it.unical.asde2018.memory.components.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import it.unical.asde2018.memory.components.services.EventService;
import it.unical.asde2018.memory.components.services.GameService;
import it.unical.asde2018.memory.components.services.LobbyService;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.Player;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private EventService eventService;

	private final static String gameEvent = "game";
	private final static String lobbyEvent = "lobby";

	@PostConstruct
	private void init() {
		eventService.addEventSource(gameEvent);
		eventService.addEventSource(lobbyEvent);
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
				String gameID = gameService.createGame(lobbyPlayer.getPlayers(), Difficulty.valueOf(difficulty));
				System.out.println("Tutto bene creo la partita con GAMEID " + gameID);
//				model.addAttribute("game", gameID);
				session.setAttribute("game", gameID);
				try {
					eventService.addEvent(lobbyPlayer.getName(), lobbyEvent, "gameStarted");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
		System.out.println("GET GAME CONTROLLER");
		Player player = (Player) session.getAttribute("user");
		Game myGame = gameService.getMyGame(player.getUsername());
		session.setAttribute("game", myGame.getGameID());
		model.addAttribute("cards", gameService.getCards((String) session.getAttribute("game")));
		model.addAttribute("inGame", "true");
		System.out.println("END GET GAME CONTROLLER");
		return "memory";
	}

	@PostMapping("pickCard")
	@ResponseBody
	public String pickCard(HttpSession session, Model model, @RequestParam int imageId, @RequestParam int position) {
//		System.out.println("pick card function");
//		System.out.println("id : " + imageId);
//		System.out.println("counter " + position);
		String gameID = (String)session.getAttribute("game");
		Game game = gameService.getGameByID(gameID);
		System.out.println(game.getGameID());
		String result = gameService.pickCard((String) session.getAttribute("game"),
				(Player) session.getAttribute("user"), imageId, position);
		if (result.equals("win")) {
			System.out.println("Ha vinto " + ((Player) session.getAttribute("user")).getUsername());
			try {
				eventService.addEvent(game.getGameID(), gameEvent, "finishGame");
				//MIGLIORARE
				gameService.getGameByID(gameID).setTime();;
				gameService.saveGame(gameService.getGameByID(gameID));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@GetMapping("/checkGameStarted")
	@ResponseBody
	public String checkGameStarted(HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("user");
		System.out.println("CHECK GAME " + player.getUsername());
		return gameService.gameReady(player.getUsername());
	}

	@GetMapping("/getResult")
	@ResponseBody
	public String getResults(HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("user");
		String gameID = (String) session.getAttribute("game");
		Game game = gameService.getGameByID(gameID);
		JSONObject jsonGame = new JSONObject();
		JSONArray array = new JSONArray();
		for (Player p : game.getPlayers()) {
			JSONObject obj = new JSONObject();
			obj.put("user", p.getUsername());
			if (p.getId() == game.getWinner()) {
				obj.put("status", "win");
			} else
				obj.put("status", "lose");
			array.add(obj);
		}
		jsonGame.put("players", array);
		jsonGame.put("time", game.getResultTime());

		return jsonGame.toJSONString();
	}

	@GetMapping("/getEvents")
	@ResponseBody
	@Async
	public DeferredResult<String> getEvents(HttpSession session, @RequestParam String eventSource) {

//		System.out.println("CALL GET EVENTS Controller");
//		System.out.println("eventSource " + eventSource);
		DeferredResult<String> output = new DeferredResult<>();
//		ForkJoinPool.commonPool().submit(() -> {
//
//		});
		System.out.println(
				"chi cazzo � " + ((Player) session.getAttribute("user")).getUsername() + " +++ " + eventSource);
		String eventTarget = "";
		if (eventSource.equals(gameEvent)) {
			eventTarget = (String) session.getAttribute("game");
		} else if (eventSource.equals(lobbyEvent)) {
			if (((Lobby) session.getAttribute("lobby")).getName() != null) {
				eventTarget = ((Lobby) session.getAttribute("lobby")).getName();
			}
		}
		try {
//			eventService.nextEvent(user_id, object_id, eventSource);
			String event = eventService.nextEvent(session.getId(), eventTarget, eventSource);
			Player attribute = (Player) session.getAttribute("user");
			System.out.println(" preleva l'evento " + attribute.getUsername() + " --- per il target  " + eventTarget
					+ " --- event " + event);
			output.setResult(event);
		} catch (InterruptedException e) {
			System.out.println("event exception");
			e.printStackTrace();
			output.setResult("An error occurred during event retrieval");
		}

		return output;
	}

}
