package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private String name;
	private List<User> players = new ArrayList<>();

	public Lobby(String name, User user) {
		super();
		this.setName(name);
		players.add(user);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean full() {
		if (players.size() >= 2)
			return true;
		return false;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public int getLobbySize() {
		return players.size();
	}

	public void joinLobby(User user) {
		if (!user.isCreator()) {
			this.players.add(user);
		}
	}

	public void leaveLobby(User user) {
		// controllo che nn ci sonoi già
		players.remove(user);

	}

	public boolean creatorLobby(User user) {
		return user.isCreator();
	}

}
