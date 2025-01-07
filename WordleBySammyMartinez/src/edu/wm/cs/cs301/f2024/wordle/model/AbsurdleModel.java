package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;

/**
 * May not need to use this for the solution? Dont know
 */
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wm.cs.cs301.f2024.wordle.view.*;
import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;
import edu.wm.cs.cs301.f2024.wordle.view.KeyboardPanel;

public class AbsurdleModel extends Model{
	private static final Color BLACK = Color.BLACK;
	private static final Color WHITE = Color.WHITE;
	//private final int  prelim_word_count = 0;
	Logger logger;
	public AbsurdleModel() {
		super();
	      // Accessing the logger from ReadWordsRunnable
        this.logger = ReadWordsRunnable.getLogger();
	}
	 
	/**
	 * The key logic to createWordList() is already covered in ReadWordsRunnable 
	 * documentation. This method explicitly calls that logic. 
	 */
	//protected void createWordList() {
		//ReadWordsRunnable runnable = new ReadWordsRunnable(this);
		//new Thread(runnable).start();//makes a thread to create the word list, 
		//this.wordListThread = new Thread(runnable);
		//this.wordListThread.start();
	//}
	public void initialize() {
		/**  1. Initialize the constructor fields.
		 * 	 2. Initialize the WordleGrid
		 */
		//System.out.println("We reinitialized");
		createWordList();
		joinThreads();
		this.wordleGrid = initializeWordleGrid();
		this.currentColumn = -1;
		this.currentRow = 0;
		this.guess = new char[columnCount];
	}

	
	/**
	 * Set the current column guess character
	 * @param c a character for the current guess string
	 */
	public void setCurrentColumn(char c) {
		//0, 1, 2, 3, 4 for ArrayLogic, actual columnCount is 5\
		/**1. Increment the currentCol, use Math.min to determine current Col.
		 * 2. Make the guess word at the column index equal to @param char c for the current column
		 * 3. Make the wordleGrid reflect this change at the currentRow currentCol position with a new WordleResponse
		 * 			of c, WHITE, BLACK as passed in parameters
		 * 
		 */
		//0, 1, 2, 3, 4 for ArrayLogic, actual columnCount is 5
		currentColumn++;
		currentColumn = Math.min(currentColumn, (columnCount - 1));
		guess[currentColumn] = c;
		wordleGrid[currentRow][currentColumn] = new WordleResponse(c,
				WHITE, BLACK);
	}
	
	/**1. Process the current row into the WordleGrid
	 * 	  For each column in the current row, color code the guessed word when compared to every other word in the wordList
	 * 	  For each color pattern, create a bucket of words with the same color patterns. Keep track of the wordCount for each bucket
	 * 	  Once all the processing has been completed, choose the bucket with the most amount of words to be the new wordList from which color
	 * 			patterns can be derived. 
	 * 	 Color code the current Row with the pattern that contained the most buckets
	 * 	 
	 * 2. Increment current Row, set the currentCol back to -1. 
	 * 
	 */
		
		
	public boolean setCurrentRow() {	
	    // <STRING, LIST<STRING>
		// colorString, LIST<STRING> that matches that color
	    Map<String, List<String>> colorBuckets = new HashMap<>();
	  //  System.out.println("Guess : " + guess.toString());
	    for (int i = 0; i < guess.length; i++) {
	        guess[i] = Character.toLowerCase(guess[i]);
	    }
	   // System.out.println("Guess : " + new String (guess));
	    // For each word in wordList: generate a color pattern, match it to the bucket
	    for (String word : wordList) {
	    	//System.out.println("Word size: " + word.length());
	        char[] wordArray = word.toCharArray();
	       // System.out.println("wordArray size: " + wordArray.length);
	        StringBuilder colorPattern = new StringBuilder();

	        // Generate color patterns by comparing each character in guess to wordArray
	        for (int column = 0; column < guess.length; column++) {
	            Color backgroundColor = AppColors.GRAY; // default color
	            
	            if (guess[column] == wordArray[column]) {
	                backgroundColor = AppColors.GREEN;
	            } else if (contains(wordArray, guess, column)) {
	                backgroundColor = AppColors.YELLOW;
	            }
	            
	            //Build the color pattern as you go through each column in the word
	            if (backgroundColor == AppColors.GREEN) colorPattern.append("G");
	            else if (backgroundColor == AppColors.YELLOW) colorPattern.append("Y");
	            else colorPattern.append("X");
	        }

	        // Convert pattern to a string
	        String pattern = colorPattern.toString();
	        
	        // Add the word to the corresponding pattern bucket
	        colorBuckets.computeIfAbsent(pattern, key -> new ArrayList<>()).add(word);
	        
	     //   System.out.println("Word: " + word + " | Pattern: " + pattern);
	    }
	    
	    // Debug print for pattern buckets after processing
	   // System.out.println("\nGenerated Pattern Buckets:");
	    for (Map.Entry<String, List<String>> entry : colorBuckets.entrySet()) {
	      //  System.out.println("Pattern: " + entry.getKey() + " | Words: " + entry.getValue());
	        logger.info("Pattern: " + entry.getKey() + " | Words: " + entry.getValue() + " | Bucket Size: " + entry.getValue().size());
	    }

	    
	    String mostCommonPattern = "";
	    int maxSize = 0;

	    for (Map.Entry<String, List<String>> entry : colorBuckets.entrySet()) {
	        if (entry.getValue().size() > maxSize) {
	            mostCommonPattern = entry.getKey();
	            maxSize = entry.getValue().size();
	        }
	    }

	    // Update wordList to be the list of words in the largest bucket
	    wordList = colorBuckets.getOrDefault(mostCommonPattern, new ArrayList<>());
	    //wordlist -> wordlist.size
	  //  System.out.println("\nMost Common Pattern: " + mostCommonPattern + " | Updated wordList: " + wordList.size());
	   // System.out.println(wordList.toString());
	    
	    // Apply the most common pattern to the current row in wordleGrid
	 //   System.out.println("\nApplying pattern to wordleGrid for current row:");
	    for (int column = 0; column < guess.length; column++) {
	        Color backgroundColor;
	        char guessCharUpper = Character.toUpperCase(guess[column]);
	        switch (mostCommonPattern.charAt(column)) {
	            case 'G':
	                backgroundColor = AppColors.GREEN;
	                break;
	            case 'Y':
	                backgroundColor = AppColors.YELLOW;
	                break;
	            default:
	                backgroundColor = AppColors.GRAY;
	                break;
	        }
	      //  wordleGrid[currentRow][column] = new WordleResponse(guess[column], backgroundColor, Color.WHITE);
	        //wordleGrid[currentRow][column] = new WordleResponse(Character.toLowerCase(guessCharUpper), backgroundColor, WHITE);
	        wordleGrid[currentRow][column] = new WordleResponse(guessCharUpper, backgroundColor, WHITE);
	      //  System.out.println("Column " + column + ": Char " + guess[column] + " | BackgroundColor: " + backgroundColor);
	    }

	    // Update the row and reset the column and guess for the next turn
	    currentColumn = -1;
	    currentRow++;
	   // System.out.println("Guess: " + guess.toString());
	    guess = new char[columnCount];
	    
	    // Continue the game if there is room for more rows
	    
	    return currentRow < maximumRows;
	}
	public char[] getModelType() {
	    return "absurdle".toCharArray();
	}
	
	

	
	
	
}