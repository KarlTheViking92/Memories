package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private String name;
	private List<Player> players;
	private Player creator;
	private int maxNumPlayer;
	private Game game;

	public Lobby(String name, Player creator, int maxNumPlayer) {
		super();
		this.name = name;
		this.maxNumPlayer = maxNumPlayer;
		players = new ArrayList<>();
		this.creator = creator;
		players.add(creator);
	}

	public void createGameLobby(String name) {
		game = new Game(name);
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public boolean full() {
		if (players.size() >= maxNumPlayer)
			return true;
		return false;
	}

	public List<Player> getPlayers() {
		return players;
	}

//	public void setPlayers(List<Player> players) {
//		this.players = players;
//	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public void joinLobby(Player player) {
//		if (!user.isCreator()) {
		this.players.add(player);
//		}
	}

	public void leaveLobbyPlayer(Player player) {
		players.remove(player);

	}

//	public boolean creatorLobby(Credentials user) {
//		System.out.println(user + " " + user.getUsername() + " " + user.isCreator());
//		return user.isCreator();
//	}

//	public boolean alreadyInLobby(Credentials user) {
//		for (Credentials inserted : players) {
//			if (inserted.equals(user)) {
//				return true;
//			}
//		}
//		return false;
//	}

	public Player getCreator() {
		return creator;
	}

}
