package it.unical.asde2018.memory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;

import it.unical.asde2018.memory.game.MemoryLogic;

@Entity
@Table(name = "Game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "PLAYER_GAME", joinColumns = { @JoinColumn(name = "game_id") }, inverseJoinColumns = {
			@JoinColumn(name = "player_id") })
	private List<Player> players;
//	private Map<Player,Integer> players_map;
	
	@Column(nullable = false)
	private int seconds;
	
//	Non serve
	@Column(nullable = false)
	private String winner;

	@Transient
	private MemoryLogic memory;
	@Transient
	private List<MyImage> picked_card;

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
	@Transient
	private String gameId;
	@Transient
	private int matrix_dimension;
	@Transient
	private int win_count = 0;

	public Game(String gameId, List<Player> players, Difficulty d) {
		this.gameId = gameId;
		this.players = players;
		winner = "";
		// forse da cacciare
		matrix_dimension = d.difficultyValue;
		memory = new MemoryLogic(matrix_dimension);
		picked_card = new ArrayList<>();
	}

	public List<MyImage> getGameCards() {
		return memory.getSelected();
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
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

	public String pick(int id, int count) {
		List<MyImage> selected = memory.getSelected();
		MyImage img = selected.get(count);
		picked_card.add(img);

		if (picked_card.size() == 2) {
			System.out.println("confronto " + picked_card.get(0).getName());
			System.out.println("con " + picked_card.get(1).getName());
			System.out.println("bool " + picked_card.get(0).equals(picked_card.get(1)));

			if (picked_card.get(0).equals(picked_card.get(1))) {
				picked_card.clear();
				win_count += 1;
				if (win_count == matrix_dimension) {
					return "win";
				}
				return "found-pair";
			} else {
				picked_card.clear();
				return "wrong-pair";
			}
		} else
			return "selected";
	}

	public String getGameId() {
		return gameId;
	}


}
