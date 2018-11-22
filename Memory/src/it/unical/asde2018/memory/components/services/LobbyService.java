package it.unical.asde2018.memory.components.services;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.model.Lobby;

@Service
public class LobbyService {
		
	public Set<Lobby> lobbies = new HashSet<>();
	
	@PostConstruct
	public void init() {
		lobbies.add(new Lobby("Saturn",0));	
		lobbies.add(new Lobby("Venus",1));	
		lobbies.add(new Lobby("Mars",2));	
	}
	
	public Set<Lobby> getLobbies() {
		return lobbies;
	}

	public void setLobbies(Set<Lobby> lobbies) {
		this.lobbies = lobbies;
	}
	
	public void createLobby(String name) {
		lobbies.add(new Lobby(name,1));	
	}
	
	public void joinLobby(String name) {
		for (Lobby inserted : lobbies) {
			if(inserted.getName().equals(name)) {
				if(!inserted.full())
					inserted.setSize(inserted.getSize()+1);
			}
		}		
	}
	
	//Cambio Nome funzione 
	public boolean notFullLoby(String name) {
		for (Lobby inserted : lobbies) {
			if(inserted.getName().equals(name)) {
				if(inserted.full()) {
			    	return false;
				}					
			}		
		}	
		//System.out.println(name + " NOT FULL");
		return true;
	}

	public int getSize(String name) {
		Integer size = null;
		for (Lobby inserted : lobbies) {
			if(inserted.getName().equals(name)) {
				size = inserted.getSize();
			}
		}
		return size;		
	}

}