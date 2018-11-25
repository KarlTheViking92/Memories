package it.unical.asde2018.memory.game;

import java.util.ArrayList;
import java.util.List;

import it.unical.asde2018.memory.model.Game;
import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

public class GameManager {

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

	private List<Player> players;

	private MemoryLogic memory;
	private List<MyImage> picked_card;
	private int matrix_dimension;
	private int win_count = 0;

	public GameManager(List<Player> p, GameManager.Difficulty d) {
		matrix_dimension = d.getDifficultyValue();
		players = p;
		picked_card = new ArrayList<>();
		memory = new MemoryLogic(matrix_dimension);
	}

	public Integer[][] getMatrix() {
		return memory.getMatrix();
	}

	public List<MyImage> getImages() {
		return memory.getSelected();
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

//		return "";
	}
}
