package it.unical.asde2018.memory.model;

public class User {

	private String username;
	private boolean creator;

	public User(String username, boolean creator) {
		super();
		this.username = username;
		this.creator = creator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isCreator() {
		return creator;
	}

	public void setCreator(boolean creator) {
		this.creator = creator;
	}
}
