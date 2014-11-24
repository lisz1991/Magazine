package com.git.magazine.entity;

public class Column {
	private String name;
	private String url;

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

	public Column(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public Column() {
		super();
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", url=" + url + "]";
	}

}
