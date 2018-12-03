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
		Player player = (Player) session.getAttribute("user");
		Lobby lobbyPlayer = lobbyService.getLobbyPlayer(player);
		if (lobbyPlayer.getCreator().equals(player)) {
			if (lobbyPlayer.full()) {
				String gameID = gameService.createGame(lobbyPlayer.getPlayers(), Difficulty.valueOf(difficulty));
				session.setAttribute("game", gameID);
				session.removeAttribute("lobby");
				lobbyService.removeLobby(lobbyPlayer.getName());
				try {
					eventService.addEvent(lobbyPlayer.getName(), lobbyEvent, "gameStarted");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "getGame";
			} else {
			}
		}
		return "";
	}

	@GetMapping("getGame")
	public String getGame(HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("user");
		Game myGame = gameService.getMyGame(player.getUsername());
		session.setAttribute("game", myGame.getGameID());
		model.addAttribute("cards", gameService.getCards((String) session.getAttribute("game")));
		model.addAttribute("inGame", "true");
		session.removeAttribute("lobby");
		return "memory";
	}

	@PostMapping("pickCard")
	@ResponseBody
	public String pickCard(HttpSession session, Model model, @RequestParam int imageId, @RequestParam int position) {
		String gameID = (String) session.getAttribute("game");
		Game game = gameService.getGameByID(gameID);
		String result = gameService.pickCard((String) session.getAttribute("game"),
				(Player) session.getAttribute("user"), imageId, position);
		if (result.equals("win")) {
			try {
				eventService.addEvent(game.getGameID(), gameEvent, "finishGame");
				session.setAttribute("resultLastGame", getResultsJSON(session, model));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@GetMapping("saveResults")
	public void asd(HttpSession session, Model model) {
		session.setAttribute("resultLastGame", getResultsJSON(session, model));
	}
	
	private String saveAndDeleteGame(HttpSession session, Model model) {
		String gameID = (String) session.getAttribute("game");
		Game game = gameService.getGameByID(gameID);
		gameService.saveGame(game);
		gameService.deleteGame(gameID);
		session.removeAttribute("game");
		return "";
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
		String tmp = (String) session.getAttribute("resultLastGame");
		return tmp;
	}

	@SuppressWarnings("unchecked")
	public String getResultsJSON(HttpSession session, Model model) {
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
		if (player.getId() == game.getWinner())
			jsonGame.put("winner", "true");

		return jsonGame.toJSONString();
	}

	@GetMapping("/getEvents")
	@ResponseBody
	@Async
	public DeferredResult<String> getEvents(HttpSession session, @RequestParam String eventSource) {

		DeferredResult<String> output = new DeferredResult<>();
		String eventTarget = "";
		if (eventSource.equals(gameEvent)) {
			eventTarget = (String) session.getAttribute("game");
		} else if (eventSource.equals(lobbyEvent)) {
			if (((Lobby) session.getAttribute("lobby")).getName() != null) {
				eventTarget = ((Lobby) session.getAttribute("lobby")).getName();
			}
		}
		try {
			String event = eventService.nextEvent(session.getId(), eventTarget, eventSource);
			Player attribute = (Player) session.getAttribute("user");
			output.setResult(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
			output.setResult("An error occurred during event retrieval");
		}

		return output;
	}

}
