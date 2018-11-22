package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.User;

@Service
public class LobbyService {

	public Set<Lobby> lobbies = new HashSet<>();

	@PostConstruct
	public void init() {
		lobbies.add(new Lobby("Saturn", new User("alex", true)));
		lobbies.add(new Lobby("Venus", new User("ciccio", true)));
		lobbies.add(new Lobby("Mars", new User("giggino", true)));
	}

	public Set<Lobby> getLobbies() {
		return lobbies;
	}

	public void setLobbies(Set<Lobby> lobbies) {
		this.lobbies = lobbies;
	}

	public void createLobby(String name, User user) {
		lobbies.add(new Lobby(name, user));
	}

	public void joinLobby(String name, User user) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				if (!inserted.full())
					inserted.joinLobby(user);
			}
		}
	}

	public boolean notFullLoby(String name) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				if (inserted.full()) {
					return false;
				}
			}
		}
		// System.out.println(name + " NOT FULL");
		return true;
	}

	public int getSize(String name) {
		Integer size = null;
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				size = inserted.getLobbySize();
			}
		}
		return size;
	}

	public List<User> getPlayers(String name) {
		System.out.println(name + " OOOOOOo");
		List<User> players = new ArrayList<>();
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				players = inserted.getPlayers();
			}
		}
		return players;
	}

	public void leaveLobby(String name, User user) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				inserted.leaveLobby(user);
				// if (inserted.creatorLobby(user))
				// removeLobby(name);
			}
		}
	}

	private void removeLobby(String name) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				lobbies.remove(inserted);
				return;
			}
		}
	}

}