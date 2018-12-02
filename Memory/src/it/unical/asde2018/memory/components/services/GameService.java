package it.unical.asde2018.memory.components.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.components.persistence.GameDAO;
import it.unical.asde2018.memory.components.persistence.PlayerDAO;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {

	private Map<String, Game> games;

	@Autowired
	private GameDAO gameDAO;
	
	@PostConstruct
	public void init() {
		System.out.println("LOAD GAME SERVICE");
		games = new HashMap<>();
	}

	public String createGame(List<Player> players, Difficulty d) {
//		System.out.println("CREATING GAME DIFFICULTY " + d);
//		System.out.println("CREATING GAME DIFFICULTY TOS " + d.toString());
		String gameID = UUID.randomUUID().toString();

		Game game = new Game(gameID, players, d);
		games.put(gameID, game);
		return gameID;
	}

	public List<MyImage> getCards(String gameID) {
		System.out.println("GET CARDS");
		return games.get(gameID).getCards();
	}

	public String pickCard(String gameID, Player p, int imageId, int position) {
		return games.get(gameID).pickCard(p, imageId, position);
	}
	
	public Game getMyGame(String user) {
		for (String key : games.keySet()) {
			Game game = games.get(key);
			for (Player player : game.getPlayers()) {
				if (user.equals(player.getUsername()))
					return game;
			}
		}
		return null;
		
	}
	
	public Game getGame(String gameID) {
		return games.get(gameID);
	}

	public String gameReady(String user) {
		for (String key : games.keySet()) {
			Game game = games.get(key);
			for (Player player : game.getPlayers()) {
				if (user.equals(player.getUsername()))
					return "true";
			}
		}
		return "false";
	}

}
