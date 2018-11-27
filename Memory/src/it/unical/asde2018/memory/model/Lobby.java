package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private String name;
	private List<Player> players;
	// Rivedere il creator
	private Player creator;
	private int maxNumPlayer;

	public Lobby(String name, Player creator) {
		super();
		this.name = name;
		this.maxNumPlayer = 2;
		players = new ArrayList<>();
		this.creator = creator;
		players.add(creator);
	}

	public String getName() {
		return name;
	}

	public boolean full() {
		return players.size() == maxNumPlayer;
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
