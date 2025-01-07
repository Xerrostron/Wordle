package edu.wm.cs.cs301.f2024.wordle.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestAbsurdleModel {
    private AbsurdleModel wordleModel;
    /**
     * set up method. Call to set up the object for use. 
     */
    @Before
    public void setUp() {
        wordleModel = new AbsurdleModel();
        
    }

    @Test
    public void testInitialize() {
        // New instance of wordleModel
    	setUp();
    	//for a 5 letter word limitation, there should only be 5 columns.
    	assertEquals(5, wordleModel.getColumnCount());
    	//For 6 guesses, there should only be 6 rows MAX
    	assertEquals(6, wordleModel.getMaximumRows());
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
    //	wordleModel.generateCurrentWord();
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
     * This test is to make sure the bucket selection process works for Absurdle
     * Tie edge case
     */
    @Test
    public void testBucketSelectionTie()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "nooks","doggy", "flown", "index");
    	wordleModel.setWordList(wordList);
    	//user guesses "beta"
    	wordleModel.setCurrentColumn('g');
    	wordleModel.setCurrentColumn('u');
    	wordleModel.setCurrentColumn('e');
    	wordleModel.setCurrentColumn('s');
    	wordleModel.setCurrentColumn('s');
    	wordleModel.setCurrentRow();//in my implementation, will set the new word list
    	List<String> checkList = new ArrayList<>();
    	Collections.addAll(checkList, "doggy");
    	assertEquals(wordleModel.getWordList(), checkList);
    	
    }
    @Test
    public void testBucketSelection()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "nooks","doggy", "nlood", "index");
    	wordleModel.setWordList(wordList);
    	//user guesses "beta"
    	wordleModel.setCurrentColumn('c');
    	wordleModel.setCurrentColumn('r');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('w');
    	wordleModel.setCurrentColumn('n');
    	wordleModel.setCurrentRow();//in my implementation, will set the new word list
    	List<String> checkList = new ArrayList<>();
    	Collections.addAll(checkList, "nooks","nlood");
    	assertEquals(wordleModel.getWordList(), checkList);
    	
    }
    @Test
    public void testBucketSelectionOne()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "cooks");
    	wordleModel.setWordList(wordList);
    	wordleModel.setCurrentColumn('c');
    	wordleModel.setCurrentColumn('r');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('w');
    	wordleModel.setCurrentColumn('n');
    	wordleModel.setCurrentRow();
     	List<String> checkList = new ArrayList<>();
    	Collections.addAll(checkList, "cooks");
    	assertEquals(wordleModel.getWordList(), checkList);
    	
    	
    }
    /**
     * think: what actually changes with Absurdle hard mode?
     */
    @Test 
    public void testWordleModelHard()
    {
    	setUp();
    	List<String> wordList = new ArrayList<>();
    	Collections.addAll(wordList, "nooks","doggy", "nlood", "index");
    	wordleModel.setWordList(wordList);
    	//user guesses "beta"
    	wordleModel.setCurrentColumn('c');
    	wordleModel.setCurrentColumn('r');
    	wordleModel.setCurrentColumn('o');
    	wordleModel.setCurrentColumn('w');
    	wordleModel.setCurrentColumn('n');
    	wordleModel.setCurrentRow();//in my implementation, will set the new word list
    	List<String> checkList = new ArrayList<>();
    	Collections.addAll(checkList, "nooks","nlood");
    	assertEquals(wordleModel.getWordList(), checkList);
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
    public void testWordleHardAgain()
    {
    		setUp();
    		List<String> wordList = new ArrayList<>();
    		Collections.addAll(wordList, "melon");
    		RuleHard ruleHard = new RuleHard();
    		wordleModel.setWordList(wordList);
        	wordleModel.setCurrentColumn('m');
        	wordleModel.setCurrentColumn('e');
        	wordleModel.setCurrentColumn('l');
        	wordleModel.setCurrentColumn('o');
        	wordleModel.setCurrentColumn('o');
        	assertEquals(ruleHard.isAcceptableGuess(wordleModel),true);
        	wordleModel.setCurrentRow();
        	List<String> checkList = new ArrayList<>();
        	Collections.addAll(checkList, "melon");
        	assertEquals(wordleModel.getWordList(), checkList);
        	wordleModel.setCurrentColumn('m');
        	wordleModel.setCurrentColumn('e');
        	wordleModel.setCurrentColumn('l');
        	wordleModel.setCurrentColumn('o');
        	wordleModel.setCurrentColumn('n');
        	
        	assertEquals(ruleHard.isAcceptableGuess(wordleModel),true);
    }
    //helper methods down below
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
}