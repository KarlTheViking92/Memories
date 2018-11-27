package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Player")
public class Player {
	
	//Aggiungere oggetto credentials qui.
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @Column(name = "player_id")
	private long id;

	@ManyToMany(mappedBy = "players")
	private List<Game> games = new ArrayList<>();

	@Column
	private String username;

	public Player() {
		super();
	}

	public Player(String username) {
		super();
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public boolean equals(Object obj) {
		Player p = (Player) obj;
		return this.username == p.getUsername() &&  this.getId() == p.getId();
	}
}
