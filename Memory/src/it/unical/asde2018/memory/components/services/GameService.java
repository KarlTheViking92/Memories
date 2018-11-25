package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import it.unical.asde2018.memory.game.GameManager;
import it.unical.asde2018.memory.game.GameManager.Difficulty;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

@Service
public class GameService {
	
	private GameManager manager;
	
	public void init() {
		System.out.println("LOAD GAME SERVICE");
		List<Player> l = new ArrayList<>();
		l.add(new Player("ciccio"));
		l.add(new Player("asd"));
		manager = new GameManager(l, Difficulty.EASY);
		this.cards = manager.getImages(); 
	}
	
	public List<MyImage> getCards() {
		System.out.println("GET CARDS");
		return cards;
	}

	private List<MyImage> cards;
	
	public String pickCard(int id, int count) {
		return manager.pick(id, count);
	}
	
}
