package com.martin.indexy.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import com.martin.indexy.types.Entry;
import com.martin.indexy.types.Tags;

public class IndexManager {

	private Io io;

	public IndexManager(Io io) {
		super();
		this.io = io;
	}

	public void generate(int size) {
		io.out("Filling list of size " + size);
		long millis = System.currentTimeMillis();
		Random r = new Random();
		RandomStrings rs = new RandomStrings();
		IndexHolder.entries = new ArrayList<Entry>(size);
		String title;
		int folder;
		int index;
		int page;
		Tags tags;
		Entry newOne;
		for (int i = 0; i < size; i++) {
			title = rs.getRandomString();
			folder = r.nextInt(20);
			index = r.nextInt(20);
			page = r.nextInt(20);
			tags = new Tags();
			for (int z = 0; z < 5; z++) {
				tags.addTag(rs.getRandomString());
			}
			newOne = new Entry(title, folder, index, page, tags);
			IndexHolder.entries.add(newOne);
		}
		io.out("List filled in " + (System.currentTimeMillis() - millis) + "ms");

		showMem();
	}

	public void write(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			long millis = System.currentTimeMillis();
			io.out("Writing to disk...");
			oos.writeObject(IndexHolder.entries);
			oos.close();
			io.out("Data has been written successfully to file '" + filename + "' in "
					+ (System.currentTimeMillis() - millis) + "ms");
		} catch (FileNotFoundException e) {
			io.out("File not found");
		} catch (IOException e) {
			io.out("Some IOException just happened");
		}
	}
	
	public void backup() {
		File dir = new File("backup");
		if (!dir.exists()) {
			dir.mkdir();
		}
		write("backup" + File.separator + Dater.getDateAndTimeFilename());
	}

	@SuppressWarnings("unchecked")
	public void read(String filename) {
		try {
			File f = new File(filename);
			if (!f.exists()) {
				f = new File("backup" + File.separator + filename);
			}
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);

			long millis = System.currentTimeMillis();
			io.out("Reading from disk...");
			IndexHolder.entries = (ArrayList<Entry>) ois.readObject();
			ois.close();
			io.out("Data has been read successfully in "
					+ (System.currentTimeMillis() - millis) + "ms");
		} catch (FileNotFoundException e) {
			io.out("File not found");
		} catch (IOException e) {
			io.out("Some IOException just happened");
		} catch (ClassNotFoundException e) {
			io.out("The file is not compatible with this version");
		}
	}

	public void add(String title) {
		String input;
		Tags tags = new Tags();

		try {
			io.out("What folder is this entry in?");
			int folder = Integer.parseInt(io.in());
			io.out("What index is this entry in?");
			int index = Integer.parseInt(io.in());
			io.out("Which page is this entry?");
			int page = Integer.parseInt(io.in());

			io.out("You can add a tag if you want. If not, type 'end'");
			while (!(input = io.in()).contentEquals("end")) {
				if (!input.contentEquals("")) {
					tags.addTag(input);
					io.out("You can add another tag if you want. Once you're done, type 'end'");
				}
			}

			Entry entry = new Entry(title, folder, index, page, tags);
			IndexHolder.entries.add(entry);

			io.out("Your entry has been added successfully");
		} catch (NumberFormatException e) {
			io.out("You failed to enter a simple number, entry has not been added");
		}
	}

	public void search(String term) {
		ArrayList<Entry> results = new ArrayList<Entry>();
		for (Entry current : IndexHolder.entries) {
			if (current.getTitle().toLowerCase().contains(term.toLowerCase())) {
				results.add(current);
			} else if (current.getTags().searchTags(term)) {
				results.add(current);
			}
		}

		if (results.size() > 0) {
			io.out(results.size() + " results:\n");
			for (int i = 0; i < results.size(); i++) {
				if (!results.get(i).getTitle().toLowerCase()
						.contains(term.toLowerCase())) {
					io.out("["
							+ (IndexHolder.entries.indexOf(results.get(i)) + 1)
							+ "]   " + results.get(i).getTitle()
							+ " (occurrence in tags)");
				} else {
					io.out("["
							+ (IndexHolder.entries.indexOf(results.get(i)) + 1)
							+ "]   " + results.get(i).getTitle());
				}
			}
		} else {
			io.out("No results");
		}
	}

	public void list(String data) {
		String[] parts = data.split(" ");
		int start = 0;
		int end = 0;
		try {
			if (Integer.parseInt(parts[0]) > IndexHolder.entries.size()) {
				throw new IndexOutOfBoundsException();
			}
			start = Integer.parseInt(parts[0]) - 1;
			if (parts.length > 1) {
				if (Integer.parseInt(parts[1]) > IndexHolder.entries.size()) {
					throw new IndexOutOfBoundsException();
				}
				end = Integer.parseInt(parts[1]) - 1;
			} else {
				end = IndexHolder.entries.size() - 1;
			}

			for (int i = start; i < end + 1; i++) {
				io.out("[" + (i + 1) + "]   "
						+ IndexHolder.entries.get(i).getTitle());
			}
		} catch (NumberFormatException e) {
			io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			io.out("Input is out of bounds");
		}
	}

	public void delete(String data) {
		try {
			int index = Integer.parseInt(data.split(" ")[0]) - 1;
			IndexHolder.entries.remove(index);
			io.out("Entry " + (index + 1) + " has been removed");
		} catch (NumberFormatException e) {
			io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			io.out("Input is out of bounds");
		}
	}

	public void display(String data) {
		try {
			int index = Integer.parseInt(data.split(" ")[0]) - 1;
			showEntry(index);
		} catch (NumberFormatException e) {
			io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			io.out("Input is out of bounds");
		}
	}

	private void showMem() {
		Runtime rt = Runtime.getRuntime();
		long memory = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
		io.out("Using " + memory + "MB of memory");
	}

	private void showEntry(int index) {
		Entry result = IndexHolder.entries.get(index);

		io.out("\nTitle:  " + result.getTitle());
		if (result.getTags().getList().size() > 0) {
			io.outSingle("Tags:   ");
			for (String tag : result.getTags().getList()) {
				if (!tag.equals(result.getTags().getList()
						.get(result.getTags().getList().size() - 1))) {
					io.outSingle(tag + ", ");
				} else {
					io.out(tag);
				}
			}
		}
		io.out("Folder: " + result.getFolder());
		io.out("Index:  " + result.getIndex());
		io.out("Page:   " + result.getPage());
	}
}
