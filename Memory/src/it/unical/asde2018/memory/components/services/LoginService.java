package it.unical.asde2018.memory.components.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.components.persistence.CredentialsDAO;
import it.unical.asde2018.memory.model.Credentials;

@Service
public class LoginService {

	@Autowired
	private CredentialsDAO credentialsDAO;
	
	public boolean yetAnUser(String username) {
		return credentialsDAO.yetAnUser(username);
	}
	
	public boolean login(String username, String password) {
		return credentialsDAO.exists(new Credentials(username, password));
	}

	public void setCredentials(String username, String password) {
		credentialsDAO.save(new Credentials(username, password));
	}
	
	
}
