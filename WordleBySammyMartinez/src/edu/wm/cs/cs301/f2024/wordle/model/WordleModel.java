package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;


/**
 * May not need to use this for the solution? Dont know
 */
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.util.List;
import java.util.Random;

import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;

public class WordleModel extends Model {
	
	//Dont change: For a WordList that is currently being processed
	//private final int  prelim_word_count = 0;
	
	
	
	private static final Color BLACK = Color.BLACK;
	private static final Color WHITE = Color.WHITE;

	/**
	 * WordleModel() constructor. Empty constructor that initializes several field members.
	 * Notably, initializes createWordList(), statistics, guess, and wordleGrid. 
	 */
	public WordleModel() {
		// Calls the constructor of Model.java. Setups the shared Fields
		//joins threads from super()
		super();
		generateCurrentWord();
	}
	/**
	 * The key logic to createWordList() is already covered in ReadWordsRunnable 
	 * documentation. This method explicitly calls that logic. 
	 */
	//protected void createWordList() {
	//	ReadWordsRunnable runnable = new ReadWordsRunnable(this);
	
	//	this.wordListThread = new Thread(runnable);
		//this.wordListThread.start();
	//}


	/**
	 * Initializes the fields of WordleModel
	 */
	public void initialize() {
		this.wordleGrid = initializeWordleGrid();
		this.currentColumn = -1;
		this.currentRow = 0;
		generateCurrentWord();
		this.guess = new char[columnCount];
	}
	/**
	 * Calls getCurrentWord(), sets the instance field member of currentWord,
	 * modifies the word to uppercase and to charArray(). 
	 */

	public void generateCurrentWord() {
		String word = getCurrentWord();
		System.out.println(word + " from WordleModel generateCurrentWord()");
		this.currentWord = word.toUpperCase().toCharArray();
	}
	/**This method randomly gets a word from the wordList dictionary for
	 * the Wordle game. 
	 * @return String that is the randomly chosen word from the wordList. 
	 */
	private String getCurrentWord() {
		return wordList.get(getRandomIndex());
	}
	/**
	 * For TESTING PURPOSES!
	 * Should NOT be visible outside of debugging practice!
	 */
	public void setCurrentWord(char[] word)
	{
		this.currentWord = word;
	}
	
	/**
	 * Set the current column guess character
	 * @param c a character for the current guess string
	 */
	public void setCurrentColumn(char c) {
		//0, 1, 2, 3, 4 for ArrayLogic, actual columnCount is 5
		currentColumn++;
		currentColumn = Math.min(currentColumn, (columnCount - 1));
		guess[currentColumn] = c;
		wordleGrid[currentRow][currentColumn] = new WordleResponse(c,
				WHITE, BLACK);
	}
	
	/**
	 * Sets the current row. 
	 * For each column, the background color is grey, foreground is white
	 * If the guess[column] is correct, make background green.
	 * If the guess word and currentWord contain the same character, but different
	 * location, make background yellow.
	 * 
	 * Resets guess and currentColumn (For a new guess)
	 * 
	 * @return a boolean: true if the currentRow is < maximumRows
	 * 						False if the currentRow is > maximumRows.
	 */
	public boolean setCurrentRow() {	
		
		
		for (int column = 0; column < guess.length; column++) {
			Color backgroundColor = AppColors.GRAY;
			Color foregroundColor = WHITE;
			if (guess[column] == currentWord[column]) {
				backgroundColor = AppColors.GREEN;
			} else if (contains(currentWord, guess, column)) {
				
				backgroundColor = AppColors.YELLOW;
			}
			
			wordleGrid[currentRow][column] = new WordleResponse(guess[column],
					backgroundColor, foregroundColor);
		}
		
		currentColumn = -1;
		currentRow++;
		guess = new char[columnCount];
		
		return currentRow < maximumRows;
	}
	public char[] returnCurrentWord()
	{
		return this.currentWord;
	}
	public char[] getModelType() {
	    return "wordle".toCharArray();
	}
}
