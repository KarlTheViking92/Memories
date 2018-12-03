package it.unical.asde2018.memory.model;

public class Message {
	private String message="";
	private String user="";

	public Message(String message, String user) {
		this.setMessage(message);
		this.setUser(user);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
