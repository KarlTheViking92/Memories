package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "game_id")
	private long id;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name="PLAYER_GAME",
			joinColumns = {@JoinColumn(name="game_id")},
			inverseJoinColumns = {@JoinColumn(name="player_id")}
	)
	private List<Player> players;

	@Column(nullable = false)
	private int seconds;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable=false)
	private String winner;

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Game() {
		super();
	}
	
	public Game(String name) {
		players = new ArrayList<Player>();
		this.name = name;
		winner=null;
	}

	public long getId() {
		return id;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

}
