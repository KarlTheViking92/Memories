package it.unical.asde2018.memory.game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import it.unical.asde2018.memory.model.MyImage;


public class MemoryLogic {

	private Integer[][] matrix;

	private List<MyImage> images;
	private List<MyImage> selected_images;
	
	private Map<Integer, MyImage> image_map;

	// matrix dimension
	private int dimension;
	private int rows;
	private int columns;

	public MemoryLogic(int d) {
		dimension = d;
		rows = d;
		columns = 2;
		images = new ArrayList<>();
		matrix = new Integer[rows][columns];
		this.image_map = new HashMap<>();
		this.selected_images = new ArrayList<>();
		loadImages();

		int count = 0;
		Random generator = new Random();
		List<Integer> image_picked = new ArrayList<>();
		
		while (count < dimension) {
			int random = generator.nextInt(images.size());
			if(random == 0) random++;
			if (!image_picked.contains(random)) {
				image_picked.add(random);
				count++;
			}
		}
		image_picked.addAll(image_picked);

		Collections.shuffle(image_picked);
		int pos_counter = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix[i][j] = image_picked.get(pos_counter);
				selected_images.add(image_map.get(image_picked.get(pos_counter)));
				pos_counter++;
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getValue(int row, int column) {
		return matrix[row][column];
	}

	public Integer[][] getMatrix() {
		return matrix;
	}

	public boolean checkPair(int i, int j, int w, int z) {
		return matrix[i][j] == matrix[w][z];
	}

	private void loadImages() {
		JSONParser parser = new JSONParser();
		try {
			File file = new ClassPathResource("cards.json").getFile();
			Object obj = parser.parse(new FileReader(file));
			JSONArray array = (JSONArray) obj;

			for (int i = 0; i < array.size(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				
				MyImage tmp = new MyImage(Math.toIntExact((long) object.get("id")), object.get("name").toString(),
						object.get("img").toString());
				images.add(tmp);
				image_map.put(Math.toIntExact((long) object.get("id")), tmp);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}

	}

	public MyImage getImage(int x, int y) {
		return image_map.get(getValue(x, y));
	}
	
	public List<MyImage> getSelected(){
		return selected_images;
	}
}
