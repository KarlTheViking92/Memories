package it.unical.asde2018.memory.components.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unical.asde2018.memory.components.persistence.CredentialsDAO;
import it.unical.asde2018.memory.model.User;

@Service
public class LoginService {

	@Autowired
	private CredentialsDAO credentialsDAO;

	@PostConstruct
	public void init() {
		credentialsDAO.save(new User("a", "a"));
		credentialsDAO.save(new User("b", "b"));
	}

	public boolean login(String username, String password) {
		return credentialsDAO.exists(new User(username, password));
	}

	public void setCredentials(String username, String password) {
		credentialsDAO.save(new User(username, password));
	}
}
