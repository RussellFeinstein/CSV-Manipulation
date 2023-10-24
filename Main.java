package indiegogo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * CS 622 - Assignment 3
 * @author Russell Feinstein
 */
public class Main {
	static List<String> lines = new ArrayList<>();
	static Map<String, ArrayList<String>> searchHistory = new HashMap<String, ArrayList<String>>();
	static Map<String, Integer> searchFrequency = new HashMap<String, Integer>();
	
	/**
	 * Takes a file path as input and reads the file
	 * line by line into the ArrayList lines
	 * 
	 * @param filepath - an absolute filepath to be read
	 */
	public static void readfile(String filepath) {
		
		String line = null;
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filepath));
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception raised.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception raised.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches to see if a substring is present in a given string
	 * Increments a counter to count the number of matches of the given tag
	 * 
	 * @param input   - String to be searched within by the tag
	 * @param tag     - Substring to search in the input
	 * @param counter - Counts the number of times the tag has been matched in the input
	 */
	private static void searchForSubstringCSV(String input, String tag, int counter) {
	    if (input.contains(tag)) {
	        System.out.println("match on line " + Integer.toString(counter));
	        //scan for the specific fields requested - “fund_raised_percent” and “close_date”
	        String[] input_parsed = input.split(",");
	        System.out.println("Fund Raised Percent - " + input_parsed[7]);
	        System.out.println("Close Date - " + input_parsed[4]);
	    }
	}
	
	/**
	 * Adds the timestamp of the searched term to Search History HashMap
	 * 
	 * @param tag - word being searched
	 */
	public static void addToSearchHistory(String tag) {
		ArrayList<String> temp = null;
		if (searchHistory.get(tag) == null) {
			temp = new ArrayList<>();
		} else {
			temp = searchHistory.get(tag);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String timeStamp = dateFormat.format(now);
		temp.add(timeStamp);
		searchHistory.put(tag, temp);
	}
	
	/**
	 * Updates the Frequency of the search term in Search Frequency HashMap
	 * 
	 * @param tag - word being searched
	 */
	public static void addToSearchFrequency(String tag) {
		Integer count = 1;
		if (searchFrequency.get(tag) != null) {
			count = searchFrequency.get(tag) + 1;
		}
		searchFrequency.put(tag, count);
	}
	
	/**
	 * Prints the Search Frequency and History
	 */
	public static void printSearchHistoryFrequency() {
		Set<String> terms = searchHistory.keySet();
		System.out.println("Terms Searched:");
		for (String term : terms) {
			System.out.println(term + "- Searched " + searchFrequency.get(term) + " times on:" + searchHistory.get(term).toString());
		}
	}

	public static void main(String[] args) {
		
		// Read CSV into lines ArrayList, line by line
		readfile("D:\\eclipse-workspace\\Indiegogo.csv");
		
		// Create a new file and write from lines into the file
		FileWriter writer = null;
		try {
			writer = new FileWriter("mergedCSV.csv");
			for (int i = 0; i < lines.size(); i++) {
				writer.append(lines.get(i));
				writer.append("\n");
			}
		} catch (IOException e) {
			System.out.println("IO Exception raised.");
			e.printStackTrace();
		}
		
		// Read second CSV into lines ArrayList
		readfile("D:\\eclipse-workspace\\Indiegogo001.csv");
		
		try {
			for (int i = 1; i < lines.size(); i++) { // Starts at 1 instead of 0 because we do not need the headers
				writer.append(lines.get(i));
				writer.append("\n");
			}
			// Close the FileWriter
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("IO Exception raised.");
			e.printStackTrace();
		}
		
		// Load merged file into lines ArrayList
		readfile("D:\\eclipse-workspace\\Indiegogo\\mergedCSV.csv");
		
		// Search for tag 'robot' in ArrayList
		String tag = "robot ";
		int counter = 1;
		for (int i = 1; i < lines.size(); i++) {
			searchForSubstringCSV(lines.get(i), tag, counter);
			counter++;
		}
		// Add to Search History
		addToSearchHistory(tag);
		// Add to Search Frequency
		addToSearchFrequency(tag);
			
		// Search for tag 'wearable' in ArrayList
		tag = "wearable ";
		counter = 1;
		for (int i = 1; i < lines.size(); i++) {
			searchForSubstringCSV(lines.get(i), tag, counter);
			counter++;
		}
		// Add to Search History
		addToSearchHistory(tag);	
		// Add to Search Frequency
		addToSearchFrequency(tag);
		
		// Search for tag 'robot' in ArrayList
		tag = "robot ";
		counter = 1;
		for (int i = 1; i < lines.size(); i++) {
			searchForSubstringCSV(lines.get(i), tag, counter);
			counter++;
		}
		// Add to Search History
		addToSearchHistory(tag);
		// Add to Search Frequency
		addToSearchFrequency(tag);
		
		// Prints The Search History and frequency
		printSearchHistoryFrequency();
		
	}
}

