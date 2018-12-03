package it.unical.asde2018.memory.components.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.Player;

@Service
public class LobbyService {
	
//	private List<Lobby> lobbies;
	private Map<String, Lobby> lobby_map;

	@PostConstruct
	public void init() {
		lobby_map = new HashMap<String, Lobby>();
	}

	public List<Lobby> getLobbies() {
		List<Lobby> list = new ArrayList<>();
		for (String lobby : lobby_map.keySet()) {
			list.add(lobby_map.get(lobby));
		}
		return list;
	}

	public Lobby getLobby(String name) {
		return lobby_map.get(name);
	}

	public String createLobby(String name, Player creator) {
		if(!lobby_map.containsKey(name)) {
			lobby_map.put(name, new Lobby(name, creator));
			return "ok";
		}
		return "Lobby already exists";
	}

	public void joinLobby(String name, Player player) {
		System.out.println("player " + player.getUsername() + " vuole unirsi alla lobby " + name);
		if(lobby_map.containsKey(name) && !lobby_map.get(name).full() ) {
			lobby_map.get(name).joinLobby(player);
		}
	}

	public boolean fullLobby(String name) {
		return lobby_map.get(name).full();
	}

	public int getSize(String name) {
		return lobby_map.get(name).getNumberOfPlayers();
	}

	public List<Player> getPlayers(String name) {
		return lobby_map.get(name).getPlayers();
	}

	public boolean leaveLobby(String name, Player player) {
		Lobby lobby = lobby_map.get(name);
		if(lobby.getPlayers().contains(player)) {
			lobby.getPlayers().remove(player);
			if(lobby.getCreator().equals(player)) {
				removeLobby(name);
				return true;
			}
		}
		return false;
	}

	public void removeLobby(String name) {
		lobby_map.remove(name);
	}
	
	public Lobby getLobbyPlayer(Player p) {
		for (Lobby lobby : lobby_map.values()) {
			if(lobby.getPlayers().contains(p)) {
				return lobby;
			}
		}
		return null;
	}

}