package edu.wm.cs.cs301.f2024.wordle.model;



import java.util.List;



import java.util.Random;
import edu.wm.cs.cs301.f2024.wordle.model.AcceptanceRule;
import edu.wm.cs.cs301.f2024.wordle.model.RuleBasic;
import edu.wm.cs.cs301.f2024.wordle.model.RuleHard;
import edu.wm.cs.cs301.f2024.wordle.model.RuleLegitimateWordsOnly;
import edu.wm.cs.cs301.f2024.wordle.model.RuleSet;

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;

public abstract class Model {
	
    protected char[] currentWord, guess;
    protected List<String> wordList;
    protected final Statistics statistics;
    protected final Random random;
    protected final int columnCount, maximumRows;
	protected int currentColumn, currentRow;
	protected Thread wordListThread;
	protected int ruleSet;
	protected WordleResponse[][] wordleGrid;
	protected EventListenerList listenerList = new EventListenerList();


    
    
    public Model() {
    	this.currentColumn = -1;
		this.currentRow = 0;
		this.columnCount = 5;
		this.maximumRows = 6;
		this.random = new Random();
		setRuleSet();
		createWordList();
		joinThreads();
		this.wordleGrid = initializeWordleGrid();
		this.guess = new char[columnCount];
		this.statistics = new Statistics();
		//int x = this.getTotalGamesWon();
		//int y = this.getLastWin();
	//	int[] z = this.calculateArrayOfWins(0);
    }

    // Abstract method to initialize the game grid, to be implemented by subclasses
    public abstract void initialize();

    public abstract boolean setCurrentRow();
    
    public abstract void setCurrentColumn(char c);
    
    public abstract char[] getModelType();
    
    //protected abstract void createWordList();
    
    protected void createWordList() {
		ReadWordsRunnable runnable = new ReadWordsRunnable(this);
		//new Thread(runnable).start();//makes a thread to create the word list, 
		this.wordListThread = new Thread(runnable);
		this.wordListThread.start();
	}

    
    public boolean isWordListThreadAlive() {
	    return wordListThread != null && wordListThread.isAlive();
	}
   
    // Method for getting the word list, common to both subclasses
    public List<String> getWordList() {
        return this.wordList;
    }

    // Method to validate if the guess is in the word list
    public boolean isValidWord() {
        String guessWord = new String(this.guess);
        return wordList.contains(guessWord.toLowerCase());
    }

    // Common method to set the current word
    public void setCurrentWord() {
        this.currentWord = getRandomWord().toCharArray();
    }
    public WordleResponse[][] getWordleGrid() {
		return wordleGrid;
	}
    public int getMaximumRows() {
		return maximumRows;
	}
    public int getColumnCount() {
		return columnCount;
	}
    public int getCurrentColumn() {
		return currentColumn;
	}
    public int getTotalWordCount() {
	    //return wordList != null ? wordList.size() : 0;
		    // Check if the word list thread is alive
		    // Return the total word count
		    return wordList != null ? wordList.size() : 0;
	}
    public void joinThreads() {
		  if (wordListThread != null && wordListThread.isAlive()) {
		        try {
		            // Wait for the thread to complete
		        	System.out.println("Waiting for thread to complete.");
		            wordListThread.join();
		        } catch (InterruptedException e) {
		            // Handle the interruption
		        	System.out.println("Interrupted Exception");
		            Thread.currentThread().interrupt(); // Restore the interrupted status
		        }
		    }
	}
    // Helper method to get a random word from the word list
    protected String getRandomWord() {
        return wordList.get(random.nextInt(wordList.size()));
    }

    // Methods to get statistics, shared by both subclasses
    protected Statistics getStatistics() {
        return statistics;
    }
    protected int getRandomIndex() {
		int size = wordList.size();
		return random.nextInt(size);
	}
    protected WordleResponse[][] initializeWordleGrid() {
		WordleResponse[][] wordleGrid = new WordleResponse[maximumRows][columnCount];

		for (int row = 0; row < wordleGrid.length; row++) {
			for (int column = 0; column < wordleGrid[row].length; column++) {
				wordleGrid[row][column] = null;
			}
		}

		return wordleGrid;
	}
    public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
    public void backspace() {
		//empty out the current column
		if(this.currentColumn==-1)
		{
			return;
		}
		wordleGrid[currentRow][currentColumn] = null;
		guess[currentColumn] = ' ';
		//logic for the next column
		
		this.currentColumn--;
		System.out.println(this.currentColumn);
		//if(currentColumn == -1)
	//	{
		//	this.currentColumn = Math.max(currentColumn, 0);
		//}
		
	}
    /**
	 * Simple get method. Get the current row of wordleGrid. 
	 * @return Returns the value at wordleGrid[currentRowNum]
	 */
	public WordleResponse[] getCurrentRow() {
		return wordleGrid[getCurrentRowNumber()];
	}
	public WordleResponse[] getCurrentRowTest()
	{
		return wordleGrid[getCurrentRowNumberTest()];
	}
	public WordleResponse[] getProcessedRow(int x)
	{
		return wordleGrid[x];
	}
	public int getCurrentRowNumberTest()
	{
		return currentRow;
	}
	public int getCurrentRowNumber() {
		return currentRow - 1;
		//try return currentRow?
		//Why -1? Think on this
	}
	protected boolean contains(char[] currentWord, char[] guess, int column) {
		int currentCount = 0;//how many times actual guessed letter appears in word
		int guessCount = 0;//amount of Gussed Letter UPTO column
		int greenCount = 0;//amount of green in the guess
		int totalCount = 0;//total amount of Guessed Letter 
		//current - green is available space to put a YELLOW
		for(int index =0; index<currentWord.length; index++)
		{
			//How many times does the guessed letter actually appear in the word?
			if(currentWord[index]==guess[column])
			{
				currentCount++;
				
			}
			//Error:for the correct column, already handled by first if statement. Need new way to detect green
			//Can detect green, but ANY green! need green for guess[column
			if(currentWord[index] == guess[index] && guess[index] == guess[column])
			{
				System.out.println("Augment Green");
				greenCount++;
			}
		}
		for (int index = 0; index < currentWord.length; index++) {
			
			
			//How many times did you guess the same letter up to a column?
			
			if(guess[index] == guess[column])
			{
				totalCount++;
				//don't get total count! End here
				if(index <=column)
				{
					guessCount++;
				}
				
			}
		}
		
		currentCount = currentCount - greenCount;
		
		currentCount = currentCount - guessCount;
		if(currentCount > 0)
		{
			return true;
		}
		if(currentCount ==0)//perfect amount of guessing
		{
			return true;
		}
		if(currentCount<0)//too much guessing
		{
			return false;
		}
		
		return false;
	}
	public void incrementTotalGamesPlayed()
	{
		this.statistics.incrementTotalGamesPlayed();
	}
	public void setCurrentStreak(int x)
	{
		this.statistics.setCurrentStreak(x);
	}
	public int getCurrentStreak()
	{
		return this.statistics.getCurrentStreak();
	}
	public int getLongestStreak()
	{
		return this.statistics.getLongestStreak();
	}
	public int getTotalGamesPlayed()
	{
		return this.statistics.getTotalGamesPlayed();
	}
	public void addWordsGuessed(int x)
	{
		this.statistics.addWordsGuessed(x);
	}
	public boolean ApplyRuleSet()
	{
		/**
		 * Basic Rule ALWAYS Applied!
		 * Basic Mode: 0
		 * Hard Mode: 1
		 * Proper Words: 2
		 * Both Hard and Proper: 3
		 */
		
		RuleBasic ruleBasic = new RuleBasic();
		if(ruleBasic.isAcceptableGuess(this)==false)
		{
	        //JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);
	    	backspace();
			backspace();
			backspace();
			backspace();
			backspace();
			return false;
		}
		RuleLegitimateWordsOnly ruleLegit = new RuleLegitimateWordsOnly();
		RuleHard ruleHard = new RuleHard();
		if(this.ruleSet == 1)
		{
			if(ruleHard.isAcceptableGuess(this)==false)
			{
		       // JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);

				backspace();
				backspace();
				backspace();
				backspace();
				backspace();
				return false;
			}
			return true;
		}
		if(this.ruleSet == 2)
		{
			if(ruleLegit.isAcceptableGuess(this)==false)
			{
		      //  JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);

				backspace();
				backspace();
				backspace();
				backspace();
				backspace();
				return false;
			}
			return true;
		}
		if(this.ruleSet == 3)
		{
			if(ruleHard.isAcceptableGuess(this)==false)
			{
		     //   JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);

				backspace();
				backspace();
				backspace();
				backspace();
				backspace();
				
				return false;
			}
			if(ruleLegit.isAcceptableGuess(this)==false)
			{
		      //  JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);

				backspace();
				backspace();
				backspace();
				backspace();
				backspace();
				return false;
			}
			return true;
		}
		return true;
	}
	public void setRuleSet()
	{
		RuleSet gameRules = new RuleSet();
		this.ruleSet = gameRules.getRuleSet();
		System.out.println("Model ruleset: " + gameRules.getRuleSet());
	}
	public int getTotalGamesWon()
	{
		System.out.println(statistics.getTotalGamesWon() + " TOTAL GAMES WON");
		return statistics.getTotalGamesWon();
	}
	public int getLastWin()
	{
		System.out.println("Last Win took: " + statistics.getLastWin()+ " guess(es)!");
		return statistics.getLastWin();
	}
	public int[] calculateArrayOfWins(int maximumTries)
	{
		//System.out.println(statistics.calculateArrayOfWins(1));
		return statistics.calculateArrayOfWins(maximumTries);
	}
	public void saveDataToFile()
	{
		statistics.writeStatistics();
		
	}
	public char[] returnCurrentWord()
	{
		return this.currentWord;
	}
	public int getCurrentColTest()
	{
		return this.currentColumn;
	}
}
