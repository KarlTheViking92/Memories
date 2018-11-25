package it.unical.asde2018.memory.model;

public class MyImage {

	private String img;
	private String name;
	private int id;

	public MyImage(int id, String n, String i) {
		this.id = id;
		this.name = n;
		this.img = i;
	}

	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		MyImage img = (MyImage) obj;
		if(this == img)
			return true;
		return this.name == img.name && this.id == img.id;
	}

}
