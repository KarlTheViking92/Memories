package it.unical.asde2018.memory.components.controllers;

import java.util.List;

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

import it.unical.asde2018.memory.components.services.EventService;
import it.unical.asde2018.memory.components.services.LobbyService;
import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.Player;

@Controller
public class LobbyController {

	@Autowired
	LobbyService lobbyService;

	@Autowired
	EventService eventService;

	private final static String gameEvent = "game";
	private final static String lobbyEvent = "lobby";

	@RequestMapping("/createNewLobby")
	public String createNewLobby(@RequestParam String name, HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("user");
		lobbyService.createLobby(name, player);
		model.addAttribute("createdLobby", "Lobby " + name + " has been created by " + player.getUsername());
		session.setAttribute("lobby", lobbyService.getLobby(name));
		try {
			eventService.addEvent(name, lobbyEvent, "newLobby");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "lobby";
	}

	@PostMapping("/getLobby")
	@ResponseBody
	public String getLobby(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			JSONObject lob = createLobbyJSON(session);
			return lob.toJSONString();
		}
		return null;
	}

	@RequestMapping({ "/joinLobby" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String lobbyName) {
		if (!lobbyService.fullLobby(lobbyName)) {
			Player player = (Player) session.getAttribute("user");
			lobbyService.joinLobby(lobbyName, player);
			try {
				eventService.addEvent(lobbyName, lobbyEvent, "joined");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			session.setAttribute("lobby", lobbyService.getLobby(lobbyName));
			return "lobby";
		} else {
			model.addAttribute("errorLobby", lobbyName + " is full");
			model.addAttribute("lobbies", lobbyService.getLobbies());
		}
		return "lobby";
	}

	@RequestMapping({ "/leaveLobby" })
	public String leaveLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		Player player = (Player) session.getAttribute("user");
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		try {
			if (lobbyService.leaveLobby(name, player)) {
				eventService.addEvent(lobby.getName(), lobbyEvent, "removedLobby");
				lobbyService.removeLobby(lobby.getName());
			} else {
				eventService.addEvent(lobby.getName(), lobbyEvent, "leftLobby");
			}
			session.removeAttribute("lobby");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "listLobbies";
	}

	@RequestMapping("/refreshLists")
	public String refreshLobby(HttpSession session, Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "listLobbies";
	}

	@SuppressWarnings("unchecked")
	private JSONObject createLobbyJSON(HttpSession session) {

		JSONObject obj = new JSONObject();
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		Player cUser = (Player) session.getAttribute("user");
		List<Player> players = lobbyService.getPlayers(lobby.getName());
		obj.put("user", cUser.getUsername());
		obj.put("creator", lobby.getCreator().getUsername());
		obj.put("playerSize", lobby.getNumberOfPlayers());
		JSONArray jsonArray = new JSONArray();
		for (Player player : players) {
			JSONObject jsonPlayer = new JSONObject();
			jsonPlayer.put("player", player.getUsername());
			jsonArray.add(jsonPlayer);
		}
		obj.put("players", jsonArray);
		return obj;
	}

	@GetMapping("/sendMessage")
	@ResponseBody
	public String sendMessageController(HttpServletRequest request, HttpSession session,
			@RequestParam String idMessage) {
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		Player player = (Player) session.getAttribute("user");
		lobbyService.updateMessage(lobby.getName(), idMessage, player.getUsername());
		try {
			eventService.addEvent(lobby.getName(), lobbyEvent, "chat");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idMessage;
	}

	@PostMapping("/getMessage")
	@ResponseBody
	public String takeMessageController(HttpServletRequest request, HttpSession session) {
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		Player player = (Player) session.getAttribute("user");
		JSONObject obj = userChat(session);
		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	private JSONObject userChat(HttpSession session) {

		JSONObject obj = new JSONObject();
		Lobby lobby = (Lobby) session.getAttribute("lobby");
		obj.put("user", lobby.getMessage().getUser());
		obj.put("message", lobby.getMessage().getMessage());
		obj.put("creator", lobby.getCreator().getUsername());
		return obj;
	}

}
