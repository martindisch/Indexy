package com.martin.indexy.types;

import java.io.Serializable;
import java.util.ArrayList;

public class Tags implements Serializable {

	private static final long serialVersionUID = -1132753958868939343L;
	private ArrayList<String> tags;
	
	public Tags() {
		super();
		tags = new ArrayList<String>();
	}

	public boolean searchTags(String term) {
		boolean contains = false;
		for (String act : tags) {
			if (act.toLowerCase().contains(term.toLowerCase())) {
				contains = true;
			}
		}
		return contains;
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public ArrayList<String> getList() {
		return tags;
	}
}
