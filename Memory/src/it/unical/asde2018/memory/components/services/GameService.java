package it.unical.asde2018.memory.components.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {

	private Map<Integer, Game> games;

	@PostConstruct
	public void init() {
		games = new HashMap<>();
		System.out.println("LOAD GAME SERVICE");
	}

	public void createGame(int id, List<Player> players, Difficulty d) {
		games.put(id, new Game(id, players, d));
	}

	public List<MyImage> getCards(int gameId) {
		System.out.println("GET CARDS");
		return games.get(gameId).getGameCards();
	}

	public String pickCard(int gameId,int imageId, int position) {
		return games.get(gameId).pick(imageId, position);
	}

}
