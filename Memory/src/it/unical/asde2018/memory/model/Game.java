package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.unical.asde2018.memory.game.GameMatch;
import it.unical.asde2018.memory.game.MemoryLogic;

@Entity
@Table(name = "Game")
public class Game {

	@Id
	@Column(name = "game_id")
	private String gameID;

	@ManyToMany(cascade= {CascadeType.ALL})
	@JoinTable(name = "PLAYER_GAME", joinColumns = {@JoinColumn(name = "game_id")}, inverseJoinColumns = {@JoinColumn(name = "player_id")} )
	private List<Player> players;
	
	@Transient
	private List<GameMatch> gamelist;
	
	@Column(nullable = true)
	private long winner;
	
	@Transient
	private boolean finished = false;
	
	@Transient
	private Date init_time;
	

	public static enum Difficulty {
		EASY(4), NORMAL(7), HARD(10);
		private final int difficultyValue;

		public int getDifficultyValue() {
			return difficultyValue;
		}

		private Difficulty(int val) {
			difficultyValue = val;
		}
	}
	
	public Game() {
		super();
	}
	
	public Game(String gameID, List<Player> players, Difficulty d) {
		gamelist = new ArrayList<>();
		this.gameID = gameID;
		this.players = players;
		init_time = new Date();
		System.out.println(init_time);
		MemoryLogic logic = new MemoryLogic(d.getDifficultyValue());
		for (Player p : players) {
			gamelist.add(new GameMatch(logic, p));
		}
	}
	
	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<GameMatch> getGamelist() {
		return gamelist;
	}

	public void setGamelist(List<GameMatch> gamelist) {
		this.gamelist = gamelist;
	}

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Date getInit_time() {
		return init_time;
	}

	public void setInit_time(Date init_time) {
		this.init_time = init_time;
	}

	public List<MyImage> getCards(){
		return gamelist.get(0).getImages();
	}
	
	public String pickCard(Player p, int imageId, int position) {
		GameMatch match = null;
		for (GameMatch gameMatch : gamelist) {
			if(gameMatch.getPlayer().equals(p)) {
				match = gameMatch;
			}
		}
		String pick = match.pick(imageId, position);
		System.out.println("pick returns " + pick);
		if(pick.equals("win")) {
			winner = p.getId();
			Date end_time = new Date();
			System.out.println("l'utente " + p.getUsername() + " ha concluso in " + TimeUnit.MILLISECONDS.toSeconds(( end_time.getTime() - init_time.getTime())) + " secondi");
		}
		
		return pick;
	}
}
