package it.unical.asde2018.memory.components.services;

//se abbiamo tempo transformiamo tutto in una mappa del malandrino
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.Player;
import it.unical.asde2018.memory.model.Credentials;

@Service
public class LobbyService {

	public List<Lobby> lobbies;

	@PostConstruct
	public void init() {
		lobbies = new CopyOnWriteArrayList<>();
		lobbies.add(new Lobby("Saturn", new Player("alex"), 2 /* new Credentials("alex", "123",true) */));
		lobbies.add(new Lobby("Venus", new Player("ciccione"), 2));
		lobbies.add(new Lobby("Mars", new Player("giggiino"), 2));
	}

	public List<Lobby> getLobbies() {
		return lobbies;
	}

	public Lobby getLobby(String name) {
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name))
				return lobby;
		}
		// verificare il null
		return null;
	}

	public void createGame() {
		for (Lobby lobby : lobbies) {
			lobby.createGameLobby(lobby.getName());
		}
	}

//	public void setLobbies(Set<Lobby> lobbies) {
//		this.lobbies = lobbies;
//	}

	public String createLobby(String name, Player creator, int maxNumPlayers) {
		boolean tmp = false;
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name))
				tmp = true;
		}
		if (!tmp) {
			lobbies.add(new Lobby(name, creator, maxNumPlayers));
			return "ok";
		} else
			return "Lobby already exists";
	}

	public void joinLobby(String name, Player player) {
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				if (!lobby.full()) {
					lobby.joinLobby(player);
				}
			}
		}
	}

	/* forse evitabile */

	public boolean notFullLoby(String name) {
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				if (lobby.full()) {
					return false;
				}
			}
		}
		// System.out.println(name + " NOT FULL");
		return true;
	}

	public int getSize(String name) {
		Integer size = null;
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				size = lobby.getNumberOfPlayers();
			}
		}
		return size;
	}

	public List<Player> getPlayers(String name) {
		System.out.println(name + " OOOOOOo");
		List<Player> players = new ArrayList<>();
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				players = lobby.getPlayers();
			}
		}
		return players;
	}

	public void leaveLobby(String name, Player player) {
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				System.out.println("size lista prima dell'if " + lobby.getNumberOfPlayers());
				lobby.leaveLobbyPlayer(player);
				System.out.println("size lista dopo dell'if " + lobby.getNumberOfPlayers());
				if (lobby.getCreator().equals(player))
					removeLobby(name);
//				if (inserted.getLobbySize() < 1) {
//					removeLobby(name);
//				}
			}
		}
	}

	private void removeLobby(String name) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				lobbies.remove(inserted);
			}
		}
	}

}