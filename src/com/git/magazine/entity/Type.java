package com.git.magazine.entity;

public class Type {
	private String name;
	private String url;
	private String image;

	public Type() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Type [name=" + name + ", url=" + url + ", image=" + image + "]";
	}

	
}
