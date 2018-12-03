package it.unical.asde2018.memory.game;

import java.util.ArrayList;
import java.util.List;

import it.unical.asde2018.memory.model.MyImage;
import it.unical.asde2018.memory.model.Player;

public class GameMatch {

	private Player player;
	private MemoryLogic memory;
	private int matrix_dimension;

	private List<MyImage> picked_card;
	private int win_count = 0;

	public GameMatch(MemoryLogic m, Player p) {
		memory = m;
		matrix_dimension = m.getRows();
		player = p;
		picked_card = new ArrayList<>();
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
		boolean test = false;
		if (picked_card.size() < 2) {
			picked_card.add(img);
			test = true;
		}

		if (picked_card.size() == 2) {
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
		} else if(test)
			return "selected";
		else
			return "not-permitted";
	}

	public Player getPlayer() {
		return player;
	}
}
