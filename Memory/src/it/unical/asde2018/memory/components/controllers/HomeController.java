package it.unical.asde2018.memory.components.controllers;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

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

import it.unical.asde2018.memory.components.services.EventsService;
import it.unical.asde2018.memory.components.services.LobbyService;
import it.unical.asde2018.memory.components.services.LoginService;
import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.User;

@Controller
public class HomeController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LobbyService lobbyService;

	@Autowired
	private EventsService eventsService;

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
			User user = new User(username, password);
			session.setAttribute("user", user);
			session.setAttribute("userName1", username);
			try {
				eventsService.addEvent(username);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// model.addAttribute("lobbies", lobbyService.getLobbies());
		} else
			model.addAttribute("errorLogin", "Wrong credentials login!");
		return "index";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		return "index";
	}

	@RequestMapping("/createLobby")
	public String createLobby() {
		return "createLobby";
	}

	@RequestMapping("/createNewLobby")
	public String createNewLobby(@RequestParam String name, HttpSession session, Model model) {
		// YOU HAVE TO SET THAT USER IS THE CREATOR
		User user = (User) session.getAttribute("user");
		user.setCreator(true);
		session.setAttribute("user", user);

		System.out.println(user.getUsername() + " ha creato la lobby " + name);
		// model.addAttribute("nn", name);
		lobbyService.createLobby(name, user);
		model.addAttribute("createdLobby", "Lobby " + name + " has been created by " + user.getUsername());
		try {
			// JSON
			eventsService.addEvent("a");
			eventsService.addEvent(lobbyService.getLobbyName(name));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// model.addAttribute("lobbies", lobbyService.getLobbies()); //NO NEED ANYMORE
		return "createLobby";
	}

	@RequestMapping({ "/joinLobby" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		User user = (User) session.getAttribute("user");
		System.out.println("JOIN " + name + " FROM " + user.getUsername());
		lobbyService.joinLobby(name, user);

		/*
		 * if (lobbyService.notFullLoby(name)) { User user = (User)
		 * session.getAttribute("user"); lobbyService.joinLobby(name, user); //
		 * model.addAttribute("lobbies", lobbyService.getLobbies()); // session
		 * JSONArray lob = createJsonLobby(); return "lobby"; } else {
		 * model.addAttribute("errorLobby", name + " is full");
		 * model.addAttribute("lobbies", lobbyService.getLobbies()); }
		 */
		return "lobby";
	}

	@PostMapping("/lobbyList")
	@ResponseBody
	public String getLobbyList(HttpSession session, Model model) {
		// System.out.println("get Lobby list");
		if (session.getAttribute("user") != null) {
//			User user = (User) session.getAttribute("user");
			JSONArray lob = createJsonLobby(session);
			System.out.println(lob.toString());
			return lob.toJSONString();
		}
		return null;
	}

	private JSONArray createJsonLobby(HttpSession session) {

		JSONArray jsonArray = new JSONArray();
//

		User cUser = (User) session.getAttribute("user");
		String currentUser = cUser.getUsername();
		// System.out.println(currentUser);
//		JSONObject jsonCurrentUser = new JSONObject();
//		jsonCurrentUser.put("currentUser", currentUser);

		List<Lobby> lobbies = lobbyService.getLobbies();
		for (Lobby lobby : lobbies) {
			JSONObject jsonLobby = new JSONObject();
			jsonLobby.put("name", lobby.getName());
			jsonLobby.put("playerSize", lobby.getLobbySize());
			jsonLobby.put("currentUser", currentUser);
			JSONArray jsonPlayers = new JSONArray();
			for (User user : lobby.getPlayers()) {
				JSONObject jsonPlayer = new JSONObject();
				jsonPlayer.put("player", user.getUsername());
				jsonPlayer.put("creator", user.isCreator());
				jsonPlayers.add(jsonPlayer);

			}
			jsonLobby.put("userList", jsonPlayers);

			jsonArray.add(jsonLobby);
			// jsonArray.add(jsonCurrentUser);

		}

		return jsonArray;
	}

	@RequestMapping({ "/leaveLobby" })
	public String leaveLobby(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value = "lobby", defaultValue = "") String name) {
		User user = (User) session.getAttribute("user");
		System.out.println("REMOVE " + name + " FROM " + user.getUsername());

		lobbyService.leaveLobby(name, user);
		System.out.println("USER " + user.isCreator());

		model.addAttribute("lobbies", lobbyService.getLobbies());

		return "lobby";
	}

	@RequestMapping("/refreshLobby")
	public String refreshLobby(HttpSession session, Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "lobby";
	}

	@RequestMapping({ "/startGame" })
	public String joinLobby(HttpServletRequest request, HttpSession session, Model model) {
		return "game";
	}

	@RequestMapping("/rules")
	public String rules(HttpSession session, Model model) {
		return "rules";
	}

	@RequestMapping("/matchHistory")
	public String matchHistory(HttpSession session, Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		// model.addAttribute("nn", "WW");
		return "matchHistory";
	}

	@GetMapping("/getEvents")
	@ResponseBody
	public DeferredResult<String> getEvents(HttpSession session, Model model) {

		String n = (String) session.getAttribute("userName1");
		DeferredResult<String> output = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {

			try {
				output.setResult(eventsService.nextEvent(n));
				String nameUser = (String) session.getAttribute(output.toString());
				session.setAttribute("userName1", nameUser);

				// model.addAttribute("lobbies", lobbyService.getLobbies());

			} catch (InterruptedException e) {
				output.setResult("An error occurred during event retrieval");
			}
		});

		return output;
	}

}
