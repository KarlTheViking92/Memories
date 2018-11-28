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

//	private Map<Player, GameMatch> games;
//	private Map<String, List<GameMatch>> games;
	private Map<String, Game> games;
//	private String gameId;

	@PostConstruct
	public void init() {
		System.out.println("LOAD GAME SERVICE");
		games = new HashMap<>();
	}

	public String createGame(List<Player> players, Difficulty d) {
		System.out.println("CREATING GAME DIFFICULTY " + d);
		System.out.println("CREATING GAME DIFFICULTY TOS " + d.toString());
		String gameID = UUID.randomUUID().toString();

		/*
		 * MemoryLogic logic = new MemoryLogic(d.getDifficultyValue()); List<GameMatch>
		 * gamelist = new ArrayList<>();
		 * 
		 * for (Player p : players) { gamelist.add(new GameMatch(logic, p)); }
		 */

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

}
