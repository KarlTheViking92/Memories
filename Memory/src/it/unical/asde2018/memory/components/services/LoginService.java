package it.unical.asde2018.memory.components.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.components.persistence.CredentialsDAO;
import it.unical.asde2018.memory.components.persistence.PlayerDAO;
import it.unical.asde2018.memory.model.Credentials;
import it.unical.asde2018.memory.model.Player;

@Service
public class LoginService {

	@Autowired
	private PlayerDAO playerDAO;

	public boolean playerExists(String username, String password) {
		return playerDAO.exists(username,password);
	}

	public void savePlayer(String username, String password) {
		playerDAO.savePlayer(new Player(username,password));
	}

	public List<Player> getAllUsers() {
		return playerDAO.getAllExistentUsers();
	}

	public boolean yetAnUser(String username) {
		return playerDAO.yetAnUser(username);
	}
	
	public Long getPlayerId(String username) {
		return playerDAO.getPlayerIdDAO(username);
	}
}
