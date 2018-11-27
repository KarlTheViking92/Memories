package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.game.GameMatch;
import it.unical.asde2018.memory.game.MemoryLogic;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Game.Difficulty;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {

//	private Map<Player, GameMatch> games;
	private Map<String, List<GameMatch>> games;
	private String gameId;

	@PostConstruct
	public void init() {
		System.out.println("LOAD GAME SERVICE");
		games = new HashMap<>();
	}

	public String createGame(List<Player> players, Difficulty d) {
		System.out.println("CREATING GAME DIFFICULTY " + d);
		System.out.println("CREATING GAME DIFFICULTY TOS " + d.toString());
		gameId = UUID.randomUUID().toString();

		MemoryLogic logic = new MemoryLogic(d.getDifficultyValue());
		List<GameMatch> gamelist = new ArrayList<>();

		for (Player p : players) {
			gamelist.add(new GameMatch(logic, p));
		}
		games.put(gameId, gamelist);

		return gameId;
	}

	public List<MyImage> getCards(String gameId) {
		System.out.println("GET CARDS");

		GameMatch gameMatch = games.get(gameId).get(0);

		return gameMatch.getImages();
	}

	public String pickCard(String gameId, Player p, int imageId, int position) {

		List<GameMatch> list = games.get(gameId);
		GameMatch match = null;
		System.out.println("list " + list);
		for (GameMatch gameMatch : list) {
			if(gameMatch.getPlayer().equals(p)) {
				match = gameMatch;
			}
		}
		return match.pick(imageId, position);
	}

}
