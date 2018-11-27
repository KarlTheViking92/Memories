package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.User;

@Service
public class LobbyService {

	public List<Lobby> lobbies;

	@PostConstruct
	public void init() {
		lobbies = new CopyOnWriteArrayList<>();
		Lobby saturn = new Lobby("Saturn", new User("a", "a", true));
		saturn.joinLobby(new User("ciccio", "ff"));
		lobbies.add(saturn);
		// lobbies.add(new Lobby("Venus", new User("ciccio", "ff")));
		// lobbies.add(new Lobby("Mars", new User("giggino", "M")));
	}

	public List<Lobby> getLobbies() {
		return lobbies;
	}

	public void setLobbies(List<Lobby> lobbies) {
		this.lobbies = lobbies;
	}

	public void createLobby(String name, User user) {
		lobbies.add(new Lobby(name, user));
	}

	public void joinLobby(String name, User user) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				if (!inserted.full()) {
					inserted.joinLobby(user);
				}
			}
		}
	}

	public String getLobbyName(String name) {
		String n = "";
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				n = inserted.getName();
			}
		}
		return n;
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
				System.out.println("REMOVE" + name + user.getUsername());
				inserted.leaveLobby(user);
				if (inserted.creatorLobby(user))
					removeLobby(name);
			}
		}
	}

	private void removeLobby(String name) {
		for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				System.out.println("Remove " + inserted.getName());
				lobbies.remove(inserted);
			}
		}
	}

}