package edu.wm.cs.cs301.f2024.wordle.controller;
import java.util.concurrent.Executors;



import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wm.cs.cs301.f2024.wordle.model.AbsurdleModel;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.model.Model;

public class ReadWordsRunnable implements Runnable {
	
	
	private final static Logger LOGGER =
			Logger.getLogger(ReadWordsRunnable.class.getName());
	
	private final Model model;
	//private final WordleModel model;
	/** model is an instance of class WordleModel. 
	 * This class primarily serves to create the wordList and fulfill that parameter
	 * specified by the WordleModel
	 */
	/**
	 * constructor for ReadWordsRunnable. @param WordleModel allows usage of
	 * WordleModel functionality, and to createWordList(). Logger will only relay
	 * messages of Info classification. Writes logs to logging.txt. 
	 * 
	 * Stores @param model to this.model of the class. 
	 */
	
	public ReadWordsRunnable(Model model) {
		
		LOGGER.setLevel(Level.INFO);

				try {
					FileHandler fileTxt = new FileHandler("./logging.txt");
					LOGGER.addHandler(fileTxt);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.model = model;
	}

	/**
	 *  This method calls createWordList() to attempt to create a list of words
	 *  the Wordle game can use. If this fails, wordList is reinitialized to a new
	 *  ArrayList. 
	 *  
	 *  Once a wordlist is eventually created, the model will set the word list and
	 *  generate a word for the Wordle game using methods written in class WordleModel. 
	 */
	@Override
	public void run() {
		List<String> wordlist;
	
		try {
			wordlist = createWordList();
			System.out.print("We were supposed to return by this point");
					LOGGER.info("Created word list of " + wordlist.size() + " words.");
		} catch (IOException e) {
			System.out.print("Morbid Race Condition");
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			wordlist = new ArrayList<>();
		}

		model.setWordList(wordlist);
		//may not be required? can just do later i guess
		//model.generateCurrentWord();
	}
	

	/**
	 * This method lives up to its namesake: it attempts to deliver an input stream.
	 * The input stream for this method specifically is usa.txt, which contains the 
	 * dictionary for the Wordle game to use. 
	 * @return InputStream - stream is sent to createWordList(), as that method
	 *  calls this method. 
	 */
	private InputStream deliverInputStream() {
		String text = "/resources/usa.txt";
		// Original code
		/*
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream(text);
		*/
		// https://stackoverflow.com/questions/68314700/why-java-cannot-read-same-resource-file-when-module-info-is-added
		
		InputStream stream = Wordle.class.getResourceAsStream(text);
		
		if (null == stream) {
			System.out.println("Failed to open stream with " + text);
			System.exit(0);
		}
		if(stream == null)
		{
			System.out.println("Failed to open stream with " + text);
			System.exit(0);
		}
		else 
			System.out.println("Successfully opened inputstream for " + text);
		
		return stream;
	
	}
	/**
	 * This method reads in the input stream from deliverInputStream(), reads
	 * the lines from the stream and formats them to fit the model specifications, 
	 * and populates the List<String> wordList. 
	 * @returns the populated List<String> wordList
	 */
	private List<String> createWordList() throws IOException {
		int minimum = model.getColumnCount();

		List<String> wordlist = new ArrayList<>();

		
		InputStream stream = deliverInputStream();
		if(stream == null)
		{
			System.out.println("Failed to open stream with ");
			System.exit(0);
		}

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		String line = reader.readLine();
		while (line != null) {
			line = line.trim();
			if (line.length() == minimum) {
				wordlist.add(line);
			}
			line = reader.readLine();
			//System.out.print(line);
		}
		reader.close();
	    
		System.out.println("Returning wordList");
	
		return wordlist;
		
	}
	   public static Logger getLogger() {
	        return LOGGER;
	    }
	
}
