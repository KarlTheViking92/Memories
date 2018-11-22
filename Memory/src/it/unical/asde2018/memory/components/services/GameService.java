package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.components.persistence.GameDAO;
import it.unical.asde2018.memory.components.persistence.PlayerDAO;
import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {

	@Autowired
	private GameDAO gameDAO;
	
	@Autowired
	private PlayerDAO playerDAO;
	
	private List<Game> games;

	public GameService(){
		super();
	}
	
	public List<Game> init() {
		Player p1= new Player("ciccio");
		Player p2= new Player("pippo");
		Player p3= new Player("pluto");
		
		playerDAO.savePlayer(p1);
		playerDAO.savePlayer(p2);
		playerDAO.savePlayer(p3);
		
		Game g1 = createGame("Partita Uno");
		Game g2 = createGame("Partita Due");
		Game g3 = createGame("Partita Tre");
				
		g1.addPlayer(p1);
		g1.addPlayer(p2);
		g1.setWinner(p1.getUsername());
		
		g2.addPlayer(p2);
		g2.addPlayer(p3);
		g2.setWinner(p2.getUsername());
		
		g3.addPlayer(p1);
		g3.addPlayer(p3);
		g3.setWinner(p1.getUsername());
				
		gameDAO.saveGame(g1);
		gameDAO.saveGame(g2);
		gameDAO.saveGame(g3);
		
		return gameDAO.getGamesOfAUser(p2);
	}
	
	public Game createGame(String name) {
		games= new ArrayList<Game>();
		Game game = new Game(name);
		games.add(game);
		return game;
	}
	
	public void joinGame(Game game,Player player) {
		game.addPlayer(player);
	}
	

}
