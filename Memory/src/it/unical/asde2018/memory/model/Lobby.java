package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.unical.asde2018.memory.components.services.GameService;
import it.unical.asde2018.memory.model.Game.Difficulty;

public class Lobby {
	
	private String name;
	private List<Player> players;
	//Rivedere il creator
	private Player creator;
	private int maxNumPlayer;
	
	public Lobby(String name, Player creator, int maxNumPlayer) {
		super();
		this.name = name;
		this.maxNumPlayer = maxNumPlayer;
		players = new ArrayList<>();
		this.creator = creator;
		players.add(creator);
	}
	
	public String getName() {
		return name;
	}

	public boolean full() {
		if (players.size() >= maxNumPlayer)
			return true;
		return false;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public void joinLobby(Player player) {
		this.players.add(player);
	}

	public void leaveLobbyPlayer(Player player) {
		players.remove(player);
	}

	public Player getCreator() {
		return creator;
	}

}
