package com.martin.indexy.types;

import java.io.Serializable;

public class Entry implements Serializable {

	private static final long serialVersionUID = 706686267751133431L;

	private String title;
	private int folder;
	private int index;
	private int page;
	private Tags tags;

	public Entry(String title, int folder, int index, int page, Tags tags) {
		super();
		this.title = title;
		this.folder = folder;
		this.index = index;
		this.page = page;
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFolder() {
		return folder;
	}

	public void setFolder(int folder) {
		this.folder = folder;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

}
