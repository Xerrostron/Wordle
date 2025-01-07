package edu.wm.cs.cs301.f2024.wordle.controller;
import java.util.concurrent.Executors;



import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import edu.wm.cs.cs301.f2024.wordle.model.Statistics;

public class StatisticsRunnable implements Runnable {
	
	
	private final static Logger LOGGER =
			Logger.getLogger(ReadWordsRunnable.class.getName());
	
	//private final Model model;
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
	private final Statistics stats;
	public StatisticsRunnable(Statistics stats) {
		
		LOGGER.setLevel(Level.INFO);

				try {
					FileHandler fileTxt = new FileHandler("./logging.txt");
					LOGGER.addHandler(fileTxt);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.stats = stats;
	}

	/**
	 *  This method calls createWordList() to attempt to create a list of words
	 *  the Wordle game can use. If this fails, wordList is reinitialized to a new
	 *  ArrayList. 
	 *  
	 *  Once a wordlist is eventually created, the model will set the word list and
	 *  generate a word for the Wordle game using methods written in class WordleModel. 
	 */
	

	
	
	   public static Logger getLogger() {
	        return LOGGER;
	    }
	
}