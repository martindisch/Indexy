package com.martin.indexy.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.martin.indexy.types.Entry;
import com.martin.indexy.types.Tags;

public class IndexManager {
	private static ArrayList<Entry> entries;

	public IndexManager(Scanner sc) {
		super();
		entries = new ArrayList<Entry>();
	}

	public static void generate(int size) {
		Io.out("Filling list of size " + size);
		long millis = System.currentTimeMillis();
		Random r = new Random();
		RandomStrings rs = new RandomStrings();
		entries = new ArrayList<Entry>(size);
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
			entries.add(newOne);
		}
		Io.out("List filled in "
				+ (System.currentTimeMillis() - millis) + "ms");

		showMem();
	}

	public static void write(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			long millis = System.currentTimeMillis();
			Io.out("Writing to disk...");
			oos.writeObject(entries);
			oos.close();
			Io.out("Data has been written successfully in "
					+ (System.currentTimeMillis() - millis) + "ms");
		} catch (FileNotFoundException e) {
			Io.out("File not found");
		} catch (IOException e) {
			Io.out("Some IOException just happened");
		}
	}

	@SuppressWarnings("unchecked")
	public static void read(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			long millis = System.currentTimeMillis();
			Io.out("Reading from disk...");
			entries = (ArrayList<Entry>) ois.readObject();
			ois.close();
			Io.out("Data has been read successfully in "
					+ (System.currentTimeMillis() - millis) + "ms");
		} catch (FileNotFoundException e) {
			Io.out("File not found");
		} catch (IOException e) {
			Io.out("Some IOException just happened");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void add(String title) {
		String input;
		Tags tags = new Tags();

		try {
			Io.out("What folder is this entry in?");
			int folder = Integer.parseInt(Io.in());
			Io.out("What index is this entry in?");
			int index = Integer.parseInt(Io.in());
			Io.out("Which page is this entry?");
			int page = Integer.parseInt(Io.in());

			System.out
					.println("You can add a tag if you want. If not, type 'end'");
			while (!(input = Io.in()).contentEquals("end")) {
				if (!input.contentEquals("")) {
					tags.addTag(input);
					System.out
							.println("You can add another tag if you want. Once you're done, type 'end'");
				}
			}

			Entry entry = new Entry(title, folder, index, page, tags);
			entries.add(entry);

			Io.out("Your entry has been added successfully");
		} catch (NumberFormatException e) {
			System.out
					.println("You failed to enter a simple number, entry has not been added");
		}
	}

	public static void search(String term) {
		ArrayList<Entry> results = new ArrayList<Entry>();
		for (Entry current : entries) {
			if (current.getTitle().toLowerCase().contains(term.toLowerCase())) {
				results.add(current);
			} else if (current.getTags().searchTags(term)) {
				results.add(current);
			}
		}

		if (results.size() > 0) {
			Io.out(results.size() + " results:\n");
			for (int i = 0; i < results.size(); i++) {
				if (!results.get(i).getTitle().toLowerCase()
						.contains(term.toLowerCase())) {
					Io.out("["
							+ (entries.indexOf(results.get(i)) + 1) + "]   "
							+ results.get(i).getTitle()
							+ " (occurrence in tags)");
				} else {
					Io.out("["
							+ (entries.indexOf(results.get(i)) + 1) + "]   "
							+ results.get(i).getTitle());
				}
			}
		} else {
			Io.out("No results");
		}
	}

	public static void list(String data) {
		String[] parts = data.split(" ");
		int start = 0;
		int end = 0;
		try {
			if (Integer.parseInt(parts[0]) > entries.size()) {
				throw new IndexOutOfBoundsException();
			}
			start = Integer.parseInt(parts[0]) - 1;
			if (parts.length > 1) {
				if (Integer.parseInt(parts[1]) > entries.size()) {
					throw new IndexOutOfBoundsException();
				}
				end = Integer.parseInt(parts[1]) - 1;
			} else {
				end = entries.size() - 1;
			}

			for (int i = start; i < end + 1; i++) {
				Io.out("[" + (i + 1) + "]   "
						+ entries.get(i).getTitle());
			}
		} catch (NumberFormatException e) {
			Io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			Io.out("Input is out of bounds");
		}
	}

	public static void delete(String data) {
		try {
			int index = Integer.parseInt(data.split(" ")[0]) - 1;
			entries.remove(index);
			Io.out("Entry " + (index + 1) + " has been removed");
		} catch (NumberFormatException e) {
			Io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			Io.out("Input is out of bounds");
		}
	}
	
	public static void display(String data) {
		try {
			int index = Integer.parseInt(data.split(" ")[0]) - 1;
			showEntry(index);
		} catch (NumberFormatException e) {
			Io.out("Input not a number, listing aborted");
		} catch (IndexOutOfBoundsException e) {
			Io.out("Input is out of bounds");
		}
	}

	private static void showMem() {
		Runtime rt = Runtime.getRuntime();
		long memory = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
		Io.out("Using " + memory + "MB of memory");
	}
	
	private static void showEntry(int index) {
		Entry result = entries.get(index);

		Io.out("\nTitle:  " + result.getTitle());
		if (result.getTags().getList().size() > 0) {
			System.out.print("Tags:   ");
			for (String tag : result.getTags().getList()) {
				if (!tag.equals(result.getTags().getList()
						.get(result.getTags().getList().size() - 1))) {
					System.out.print(tag + ", ");
				} else {
					Io.out(tag);
				}
			}
		}
		Io.out("Folder: " + result.getFolder());
		Io.out("Index:  " + result.getIndex());
		Io.out("Page:   " + result.getPage());
	}
}
