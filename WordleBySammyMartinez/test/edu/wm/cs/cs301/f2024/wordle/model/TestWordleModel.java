package edu.wm.cs.cs301.f2024.wordle.model;

import org.junit.Before;


import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.wm.cs.cs301.f2024.wordle.view.WordleFrame;
import edu.wm.cs.cs301.f2024.wordle.model.AppColors;

public class TestWordleModel {
    private WordleModel wordleModel;
    /**
     * set up method. Call to set up the object for use. 
     */
    @Before
    public void setUp() {
        wordleModel = new WordleModel();
        
    }

    @Test
    public void testBug1ThreadSynchronization() {
        // New instance of wordleModel
    	setUp();
    	wordleModel.initialize();
    	//Test fails if the wordleModel cannot fully initialize before null content is being used
    	//for a 5 letter word limitation, there should only be 5 columns.
    	assertEquals(5, wordleModel.getColumnCount());
    	//For 6 guesses, there should only be 6 rows MAX
    	assertEquals(6, wordleModel.getMaximumRows());
    	assertNotEquals(wordleModel.getTotalWordCount(), 0);
    }
    @Test
    public void test_set_Current_Column()
    {
    	setUp();
    	char c = 'a';
    	/**
    	 * Test to make sure the column iterator works correctly as it is used
    	 * throughout a word. Repeated cases at the end to make sure setCurrentColumn()
    	 * never goes pass columnCount
    	 * 
    	 * correct behavior: value of getCurrentColumn() should equal the values listed.
    	 */
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 0);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 1);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 2);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 3);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 4);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 4);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 4);
    	wordleModel.setCurrentColumn(c);
    	assertEquals(wordleModel.getCurrentColumn(), 4);
    }
    /**
     * Test to see if the word count is correctly calculated in the code base.
     */
    @Test
    public void test_getTotalWordCount()
    {
    	/**
    	 * setup code is calling setUp(), making a wordlist or adding to a wordlist
    	 * 
    	 * Correct behavior is portrayed in the assertEquals statements
    	 */
    	setUp();
    	List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, "apple", "banana", "cherry");
        wordleModel.setWordList(wordList);
        assertEquals(wordleModel.getTotalWordCount(),3);
        Collections.addAll(wordList, "apples", "bananas", "cherries");
        wordleModel.setWordList(wordList);
        assertEquals(wordleModel.getTotalWordCount(),6);
        List<String> secondList = new ArrayList<>();
        Collections.addAll(secondList, "apple");
        wordleModel.setWordList(secondList);
        assertEquals(wordleModel.getTotalWordCount(),1);
        List<String> thirdList = new ArrayList<>();
        wordleModel.setWordList(thirdList);
        assertEquals(wordleModel.getTotalWordCount(),0);
        
    }
    /**Test that the amount of guesses may only be 6. No more guessing should be allowed
     * 
     * 
     * 
     */
    @Test
    public void test_setCurrentRow_guesses()
    {
    	/**
    	 * setUp(), wordList initialization setWordList, generateCurrentWord() all
    	 * used for setup
    	 */
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "apple");
    	wordleModel.setWordList(wordList);
    	wordleModel.generateCurrentWord();
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	//
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	/***
    	 * Correct behavior: true, because currentRow<maximmumRows
    	 */
    	assertEquals(true,wordleModel.setCurrentRow());
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	assertEquals(true, wordleModel.setCurrentRow());
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	assertEquals(true, wordleModel.setCurrentRow());
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	assertEquals(true, wordleModel.setCurrentRow());
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	assertEquals(true, wordleModel.setCurrentRow());
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	assertEquals(false, wordleModel.setCurrentRow());//where currentRow = Max.Rows.
    	System.out.println("The current row: "+ wordleModel.getCurrentRowNumber());
    	print_wordle_grid(wordleModel.getWordleGrid());
    }
    /**
     * Test the case of a correct letter being in the word, but that correct letter
     * being guessed a repeated amount of times. 
     * 
     * Such that the amount of guesses of the correct letter is > the amount of times
     * that letter actually appears in the word. 
     * 
     * Whether the letter is green or yellow, the yellow or green cells should match 
     * exactly the amount of times that letter actually appears in the word. 
     */
    @Test
    public void testBug2Coloring()
    {
    	/**
    	 * Set up code: setUp(), wordList init., setWordList, generateCurWord, 
    	 */
    	setUp();
    	System.out.println("Test: Repeated Letters");
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "apple");
    	wordleModel.setWordList(wordList);
    	wordleModel.generateCurrentWord();
    	int row_one = wordleModel.getCurrentRowNumber();//value is -1
    	System.out.println("Row One: " + row_one);
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('E');
    	wordleModel.setCurrentRow();
    	row_one = wordleModel.getCurrentRowNumber(); //value is 0
    	print_wordle_grid_row(wordleModel.getWordleGrid(),row_one);//not set, not modified
    	//green and yellow are background colors
    	//Default background color is GRAY
    	//For This test: 3rd p should be grey, as there are only 2 P's in APPLE
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[0][0].getBackgroundColor());
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[0][1].getBackgroundColor());
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[0][2].getBackgroundColor());
    	assertEquals(AppColors.GRAY, wordleModel.getWordleGrid()[0][3].getBackgroundColor());
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[0][4].getBackgroundColor());
    	//Next Test: Ambiguous repeated Letters. Which repeated letter turns Yellow?
    	//the first instance should be yellow. Next non green instance is GRAY.
    	List<String> wordList_two = new ArrayList<>();
    	Collections.addAll(wordList_two, "belly");
    	wordleModel.setWordList(wordList_two);
    	wordleModel.generateCurrentWord();
    	int row_one_dup = wordleModel.getCurrentRowNumber();//value is -1
    	System.out.println("Row One: " + row_one_dup);
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('E');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentColumn('O');
    	wordleModel.setCurrentColumn('L');
    	wordleModel.setCurrentRow();
    	row_one_dup = wordleModel.getCurrentRowNumber(); //value is 0
    	print_wordle_grid_row(wordleModel.getWordleGrid(),row_one_dup);//not set, not modified
    	assertEquals(AppColors.YELLOW, wordleModel.getWordleGrid()[1][0].getBackgroundColor());
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[1][1].getBackgroundColor());
    	assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[1][2].getBackgroundColor());
    	assertEquals(AppColors.GRAY, wordleModel.getWordleGrid()[1][3].getBackgroundColor());
    	assertEquals(AppColors.GRAY, wordleModel.getWordleGrid()[1][4].getBackgroundColor());
    	//belly
    	//lelol
    	//yellow, green, green, gray, gray,
    }
    //
    @Test
    public void testbug4Backspace()
    {
    	/**
    	 * Set up code: setUp, wordList init, setWordList, generateCurWord, 
    	 * setCurrentColumn(repeated)
    	 * 
    	 * Desired behavior for this case is to just see if backspace works in a
    	 * general sense. Checking if the values are cleared and whatnot. 
    	 * 
    	 * Case: Put a green (correct) letter in, backspace, and then put in a GRAY 
    	 * value letter. Correct backspace() implementation will make the color GRAY
    	 */
    	setUp();
    	System.out.println("Test: Backspace");
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "apple");
    	wordleModel.setWordList(wordList);
    	wordleModel.generateCurrentWord();
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('E');
    	int row_one = wordleModel.getCurrentRowNumber(); //value is 0
    	wordleModel.backspace();
    	wordleModel.setCurrentColumn('Z');
    	wordleModel.setCurrentRow();
    	
    	assertEquals(AppColors.GRAY, wordleModel.getWordleGrid()[0][4].getBackgroundColor());
    	//assertEquals(AppColors.GREEN, wordleModel.getWordleGrid()[0][4].getForegroundColor());
    	setUp();
    	List<String> wordList_two = new ArrayList<>();
    	Collections.addAll(wordList_two, "apple");
    	wordleModel.setWordList(wordList_two);
    	wordleModel.generateCurrentWord();
    	System.out.println("Current Column: " + wordleModel.getCurrentColumn());
    	wordleModel.setCurrentColumn('A');
    	System.out.println("Current Column: " + wordleModel.getCurrentColumn());

    	//0 index test case. Backspacing back to the first index to see if it is possible
    	//Correct behavior would put 'B' in 0 column, but puts it in 1 column
    	wordleModel.backspace();
    	System.out.println("Current Column: " + wordleModel.getCurrentColumn());
    	wordleModel.setCurrentColumn('B');
    	System.out.println("Current Column: " + wordleModel.getCurrentColumn());
    	assertEquals(wordleModel.getCurrentColumn(), 0);
    	//test if the Model can avoid index out of bounds errors
    	wordleModel.backspace();
    	wordleModel.backspace();
    	wordleModel.backspace();
    	assertEquals(wordleModel.getCurrentColumn(),-1);
    	
    	
    	
    }
    /**
     * the only viable guesses in Wordle are actual words.
     * Gibberish or unallowed words should not be allowed to be used for a guess
     */
    @Test
    public void testbug3MustGuessRealWords()
    {
    	setUp();
    	System.out.println("Test: Repeated Letters");
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "apple");
    	wordleModel.setWordList(wordList);
    	wordleModel.generateCurrentWord();
    	int row_one = wordleModel.getCurrentRowNumber();//value is -1
    	System.out.println("Row One: " + row_one);
    	wordleModel.setCurrentColumn('A');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('P');
    	wordleModel.setCurrentColumn('E');
    	//set current Row just processed a gibberish word
    	assertEquals(wordleModel.isValidWord(),false);
    	
    	//Other than not processing a fake word, no information should be given to user with
    	//a Fake Word
    	//Can't use this, the setCurrentRow() logic deals with color, but keyBoard Press checks valid word
    	//assertEquals(wordleModel.getWordleGrid()[0][0].getBackgroundColor(),AppColors.GRAY );
    }
    @Test
    public void testBug5KeyboardColors()
    {
    	setUp();
    	System.out.println("Test: KeyboardColors");
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "apple");
    	wordleModel.setWordList(wordList);
    	wordleModel.generateCurrentWord();

    	WordleFrame testFrame = new WordleFrame(wordleModel);
    	
    	testFrame.setColor("S",AppColors.GREEN , Color.WHITE);
    	

        // Retrieve the button for key 'S'
        JButton buttonS = testFrame.getButtonForKey("S"); // Assuming this method exists

        // Check the background color
        assertEquals(AppColors.GREEN, buttonS.getBackground());

        // Check the foreground color
        assertEquals(Color.WHITE, buttonS.getForeground());
    	
    }
    
    @Test
    public void testWordleBasicRule()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList,"apple", "melon", "cooks", "rain", "lambs", "poker");
    	wordleModel.setWordList(wordList);
    	wordleModel.setCurrentColumn('m');
    	RuleBasic ruleBasic = new RuleBasic();
    	//ruleBasic.isAcceptableGuess(wordleModel);
    	assertEquals(ruleBasic.isAcceptableGuess(wordleModel),false);
    	wordleModel.setCurrentColumn('e');
    	wordleModel.setCurrentColumn('l');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('n');
    	assertEquals(ruleBasic.isAcceptableGuess(wordleModel),true);
    }
    @Test
    public void testWordleLegitWords()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList,"apple", "melon", "cooks", "rain", "lambs", "poker");
    	wordleModel.setWordList(wordList);
    	wordleModel.setCurrentColumn('m');
    	wordleModel.setCurrentColumn('e');
    	wordleModel.setCurrentColumn('l');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('n');
    	RuleLegitimateWordsOnly ruleLegit = new RuleLegitimateWordsOnly();
    	ruleLegit.isAcceptableGuess(wordleModel);
    	assertEquals(ruleLegit.isAcceptableGuess(wordleModel),true);
    	wordleModel.backspace();
    	wordleModel.setCurrentColumn('z');
    	assertEquals(ruleLegit.isAcceptableGuess(wordleModel),false);

    }
    @Test
    public void testWordleHardWords()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "melon");
    	wordleModel.setWordList(wordList);
    	wordleModel.setCurrentColumn('m');
    	wordleModel.setCurrentColumn('e');
    	wordleModel.setCurrentColumn('l');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('z');
    	
    	wordleModel.setCurrentRow();
    	wordleModel.setCurrentColumn('n');
    	wordleModel.setCurrentColumn('e');
    	wordleModel.setCurrentColumn('l');
    	wordleModel.setCurrentColumn('o');                                            
    	wordleModel.setCurrentColumn('n');
    	 // Debugging print statements
        System.out.println("Word List: " + wordleModel.getWordList());
       // System.out.println("First Row: " + Arrays.toString(wordleModel.getProcessedRow(0)));
       // System.out.println("Second Row: " + Arrays.toString(wordleModel.getProcessedRow(1)));
        for(int i =0; i< wordleModel.getProcessedRow(0).length;++i)
        {
        	System.out.println("First Row: " + wordleModel.getProcessedRow(0)[i].getChar());
        }
        for(int i =0; i< wordleModel.getProcessedRow(1).length;++i)
        {
        	System.out.println("Second Row " + wordleModel.getProcessedRow(1)[i].getChar());
        }   
    	RuleHard ruleHard = new RuleHard();
    	//n needed to be tested as a grey letter first, DONT ASSUME WRONG
    	assertEquals(ruleHard.isAcceptableGuess(wordleModel),false);
    }
    
    //helper methods down below
    //System.out.flush();
    /**
     * Requires a full Grid to use
     * @param matrix - WordleGrid parameter used to print elements
     */
    private void print_wordle_grid(WordleResponse[][] matrix)
    {
    	for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 5; column++) {
				System.out.println("Row "+ row + " Column " + column + " char " + matrix[row][column].getChar());
				System.out.println("Color Background: " + matrix[row][column].getBackgroundColor());
				System.out.println("Color Foreground: " + matrix[row][column].getForegroundColor());
			}
		}

    }
    /**
     * Will print only 1 row
     * from the WordleGrid
     * @param currentMatrix - matrix which contains the data
     * @param row - the row in which the columns will be iterated over
     */
    private void print_wordle_grid_row(WordleResponse[][] currentMatrix, int row)
    {
    	WordleResponse[] currentRow = currentMatrix[row];
    	for(int column = 0; column< 5; ++column)
    	{
    		System.out.println("Row "+ row + " Column " + column + " char " + currentMatrix[row][column].getChar());
			System.out.println("Color Background: " + currentMatrix[row][column].getBackgroundColor());
			System.out.println("Color Foreground: " + currentMatrix[row][column].getForegroundColor());
		
    	}
    }
    /**
     * 
     * @param word, word to be iterated over
     * @return, return the amount of chars in the word
     */
    private int print_char_array(char[] word)
    {
    	int counter =0;
    	for (int i = 0; i < word.length; i++) { 
    			counter++;
    		}
    	return counter;
    }
    /** WordleModel Methods
     * initialize() - public - somewhat tested
     * generateCurrentWord() - public - tested
     * setWordList() - public - currently testing
     * setCurrentWord() - public - tested
     * setCurrentColumn() - public - tested
     * backspace() - public
     * setCurrentRow - populates the WordleGrid[][]Response  - 
     * getCurrentRow() - public - wordleResponseType - tested
     * getCurrentRowNumber() - public - tested
     * getStatistics() - public
     * getTotalWordCount() - public - tested
     * getCurrentColumn() - public - tested
     * getColumnCount() - public - tested
     * getMaximumRows() - public - tested
     * getWordleGrid() - public -tested
     * Still need: test backspace, test if gibberish is an allowed word
     * Remember: set, THEN getRowNumberCount(return int)
     */
    
}