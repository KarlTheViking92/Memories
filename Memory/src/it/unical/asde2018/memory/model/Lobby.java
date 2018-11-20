package it.unical.asde2018.memory.model;

public class Lobby {

	private String name;
	private int size;

	public Lobby(String name, int size) {
		super();
		this.setName(name);
		this.setSize(size);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean full() {
		if (this.size >= 2)
			return true;
		return false;
	}

}
