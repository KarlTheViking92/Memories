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
@Table
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "player_id")
	private Long id;

	@ManyToMany(mappedBy = "players")
	private List<Game> games = new ArrayList<>();

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;

	public Player() {
		super();
	}

	public Player(String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Player(Long playerId, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.id = playerId;
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		Player p = (Player) obj;
		return this.username == p.getUsername() && this.getId() == p.getId();
	}
}
