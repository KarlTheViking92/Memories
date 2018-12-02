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
	
	private List<Lobby> lobbies;
	private Map<String, Lobby> lobby_map;

	@PostConstruct
	public void init() {
		lobby_map = new HashMap<String, Lobby>();
	/*	lobbies = new CopyOnWriteArrayList<>();
		lobbies.add(new Lobby("Saturn", new Player("alex"), 2 /* new Credentials("alex", "123",true) ));
		lobbies.add(new Lobby("Venus", new Player("ciccione"), 2));
		lobbies.add(new Lobby("Mars", new Player("giggiino"), 2));*/
		
		lobby_map.put("Saturn", new Lobby("Saturn", new Player("alex")));
		lobby_map.put("Venus", new Lobby("Venus", new Player("ciccione")));
		lobby_map.put("Mars", new Lobby("Mars", new Player("giggiino")));
		
	}

	public List<Lobby> getLobbies() {
		List<Lobby> list = new ArrayList<>();
		for (String lobby : lobby_map.keySet()) {
			list.add(lobby_map.get(lobby));
		}
		return list;
	}

	public Lobby getLobby(String name) {
	/*	for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name))
				return lobby;
		}
		// verificare il null.
		return null;*/
		
		return lobby_map.get(name);
	}

//	public void setLobbies(Set<Lobby> lobbies) {
//		this.lobbies = lobbies;
//	}

	public String createLobby(String name, Player creator) {
/*		boolean tmp = false;
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name))
				tmp = true;
		}
		if (!tmp) {
			lobbies.add(new Lobby(name, creator, maxNumPlayers));
			return "ok";
		} else
			return "Lobby already exists"; */
		
		if(!lobby_map.containsKey(name)) {
			lobby_map.put(name, new Lobby(name, creator));
			return "ok";
		}
		return "Lobby already exists";
	}

	public void joinLobby(String name, Player player) {
//		for (Lobby lobby : lobbies) {
//			if (lobby.getName().equals(name)) {
//				if (!lobby.full()) {
//					lobby.joinLobby(player);
//				}
//			}
//		}
		System.out.println("player " + player.getUsername() + " vuole unirsi alla lobby " + name);
		if(lobby_map.containsKey(name) && !lobby_map.get(name).full() ) {
			lobby_map.get(name).joinLobby(player);
		}
	}

	/* forse evitabile */

	public boolean fullLobby(String name) {
/*		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				if (lobby.full()) {
					return false;
				}
			}
		}
		// System.out.println(name + " NOT FULL");
		return true;*/
		return lobby_map.get(name).full();
	}

	public int getSize(String name) {
/*		Integer size = null;
		for (Lobby lobby : lobbies) {
			if (lobby.getName().equals(name)) {
				size = lobby.getNumberOfPlayers();
			}
		}
		return size;*/
		return lobby_map.get(name).getNumberOfPlayers();
	}

	public List<Player> getPlayers(String name) {
//		System.out.println(name + " OOOOOOo");
//		List<Player> players = new ArrayList<>();
//		for (Lobby lobby : lobbies) {
//			if (lobby.getName().equals(name)) {
//				players = lobby.getPlayers();
//			}
//		}
//		return players;
		
		return lobby_map.get(name).getPlayers();
	}

	public boolean leaveLobby(String name, Player player) {
/*		for (Lobby lobby : lobbies) {
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
		}*/
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

	private void removeLobby(String name) {
	/*	for (Lobby inserted : lobbies) {
			if (inserted.getName().equals(name)) {
				lobbies.remove(inserted);
			}
		}*/
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