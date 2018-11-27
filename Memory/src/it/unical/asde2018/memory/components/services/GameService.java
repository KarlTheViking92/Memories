package it.unical.asde2018.memory.components.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {

	private Map<String, Game> games;
	private String gameId;

	@PostConstruct
	public void init() {
		games = new HashMap<>();
		System.out.println("LOAD GAME SERVICE");
	}

	public String createGame(List<Player> players, Difficulty d) {
		gameId=UUID.randomUUID().toString();
		games.put(gameId, new Game(gameId, players, d));
		return gameId;
	}

	public List<MyImage> getCards(String gameId) {
		System.out.println("GET CARDS");
		return games.get(gameId).getGameCards();
	}

	public String pickCard(String gameId,int imageId, int position) {
		return games.get(gameId).pick(imageId, position);
	}

}
