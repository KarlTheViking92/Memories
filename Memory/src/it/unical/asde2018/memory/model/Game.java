package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.unical.asde2018.memory.game.GameMatch;
import it.unical.asde2018.memory.game.MemoryLogic;

@Entity
@Table
public class Game {
	
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

	@Id
	@Column(name = "game_id")
	private String gameID;

	@ManyToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinTable(name = "PLAYER_GAME",
	joinColumns = { @JoinColumn(name = "game_id") },
	inverseJoinColumns = {@JoinColumn(name = "player_id")})
	private List<Player> players;

	@Column(nullable = false)
	private Long winner;

	@Column(nullable = false)
	private Long time;
	
	@Transient
	private List<GameMatch> gamelist;

	@Transient
	private boolean finished = false;

	@Transient
	private Date init_time;

	@Transient
	private Date end_time;

	public Game() {
		super();
	}

	public Game(String gameID, List<Player> players, Difficulty d) {
		super();
		this.gameID = gameID;
		this.gamelist = new ArrayList<>();
		this.players = players;
		init_time = new Date();
		MemoryLogic logic = new MemoryLogic(d.getDifficultyValue());
		for (Player p : this.players) {
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

	public Long getWinner() {
		return winner;
	}

	public void setWinner(Long winner) {
		this.winner = winner;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public List<GameMatch> getGamelist() {
		return gamelist;
	}

	public void setGamelist(List<GameMatch> gamelist) {
		this.gamelist = gamelist;
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

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public List<MyImage> getCards() {
		return gamelist.get(0).getImages();
	}

	public String pickCard(Player p, int imageId, int position) {
		GameMatch match = null;
		for (GameMatch gameMatch : gamelist) {
			if (gameMatch.getPlayer().equals(p)) {
				match = gameMatch;
			}
		}
		String pick = match.pick(imageId, position);
		if (pick.equals("win")) {
			winner = p.getId();
			end_time = new Date();
		}

		return pick;
	}

	public long getResultTime() {
		this.time = TimeUnit.MILLISECONDS.toSeconds((end_time.getTime() - init_time.getTime()));
		return time;
	}
}
