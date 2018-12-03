package it.unical.asde2018.memory.components.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		// YOU HAVE TO SET THAT USER IS THE CREATOR
		Player player = (Player) session.getAttribute("user");
		String test = lobbyService.createLobby(name, player);
		System.out.println("test  ----->>>>> " + test);
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
			Player user = (Player) session.getAttribute("user");
			JSONObject lob = createLobbyJSON(session);
			return lob.toJSONString();
		}
		return null;
	}

	@RequestMapping({ "/joinLobby" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String lobbyName) {
		System.out.println("JOIN LOBBY FUNCT " + lobbyName);
		if (!lobbyService.fullLobby(lobbyName)) {
			Player player = (Player) session.getAttribute("user");
			lobbyService.joinLobby(lobbyName, player);
			try {
				eventService.addEvent(lobbyName, lobbyEvent, "joined");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			session.setAttribute("lobby", lobbyService.getLobby(lobbyName));
			// session
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
				System.out.println("EVENT " + lobbyEvent + " removed lobby " + session.getId());
				eventService.addEvent(lobby.getName(), lobbyEvent, "removedLobby");
				lobbyService.removeLobby(lobby.getName());
//				eventService.addEvent(id, eventSource, type);
			} else {
				System.out.println("EVENT " + lobbyEvent + " left lobby " + session.getId());
				eventService.addEvent(lobby.getName(), lobbyEvent, "leftLobby");
			}
			
			session.removeAttribute("lobby");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		model.addAttribute("lobbies", lobbyService.getLobbies());
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
}
