package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;


/**
 * May not need to use this for the solution? Dont know
 */
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;

public class RuleHard implements AcceptanceRule{

	@Override
	public boolean isAcceptableGuess(Model model) {
	    RuleBasic legitWord = new RuleBasic();
	    if (!legitWord.isAcceptableGuess(model)) {
	        System.out.println("For Hard Rule: Fails because the word is not a valid guess.");
	        System.out.flush();
	        return false;
	    }

	    int numberRow = model.getCurrentRowNumberTest();
	    if (numberRow == 0) {
	        System.out.println("Trivial case: No row processed yet.");
	        return true; // No previous guess to observe.
	    }
	    
	    List<String> greyLetters = new ArrayList<>();
	    List<String> greenLetters = new ArrayList<>();
	    List<String> yellowLetters = new ArrayList<>();
	    /*
	     * Only look at processed Rows
	     */
	   for(int x =0; x<model.getCurrentRowNumberTest();++x)
	   {
	    for(int i = 0; i<5;++i)
	    {
	    	WordleResponse[] currentRow = model.getProcessedRow(x);
	    	if(currentRow[i].getBackgroundColor()==AppColors.GRAY)
	    	{
	    		System.out.println("Grey Letter: " + currentRow[i].getChar());

	    		 greyLetters.add(String.valueOf(currentRow[i].getChar()));
	    	}
	    	if(currentRow[i].getBackgroundColor()==AppColors.GREEN)
	    	{
	    		System.out.println("Green Letter: " + currentRow[i].getChar());
	    		greenLetters.add(String.valueOf(currentRow[i].getChar()));
	    	}
	    	if(currentRow[i].getBackgroundColor()==AppColors.YELLOW)
	    	{
	    		System.out.println("Yellow Letter: " + currentRow[i].getChar());

	    		yellowLetters.add(String.valueOf(currentRow[i].getChar()));
	    	}
	    }
	   }
	    // Print out the letters found in each category
	   // System.out.println("Grey Letters: " + greyLetters);
	   // System.out.println("Green Letters: " + greenLetters);
	   // System.out.println("Yellow Letters: " + yellowLetters);
	    	 // Get the current row to process
	        WordleResponse[] currentRow = model.getCurrentRowTest();

	        // Check the current guess against the rules
	      //  boolean greenGrey = false;
	        for (int i = 0; i < currentRow.length; ++i) {
	            char currentChar = currentRow[i].getChar();
	            String currentCharStr = String.valueOf(currentChar);
	            int counterGreenGray = 0;
	            // Rule 1: No grey letters should appear
	            if (greyLetters.contains(currentCharStr)) {
	                if(greenLetters.contains(currentCharStr))
	                {
	                	
	                	for(int z =0; z<5;++z)
	                	{
	                		//iterate currentChar over all chars to count the frequency
	                		if(currentChar == currentRow[z].getChar())
	                		{
	                			++counterGreenGray;
	                		}
	                	}
	                	//one more means there is a grey!
	                	if(counterGreenGray > Collections.frequency(greenLetters, currentCharStr))
	                	{
	                		System.out.println("Counter Green Gray: " + counterGreenGray);
	                		System.out.println("Frequency: " + Collections.frequency(greenLetters, currentCharStr));
	                		System.out.println("For Hard Rule: Fails because you have more grey than green!");
	                		System.out.flush();
	                		return false;
	                	}
	                	counterGreenGray = 0;
	                }
	             else {
		                System.out.println("For Hard Rule: Fails because your guess contains a greyed-out letter: " + currentChar);
		                System.out.flush();
	                	return false;
	                }
	               // return false;
	            }

	            // Rule 2: Green letters must be in the correct positions
	            if (greenLetters.contains(currentCharStr) && currentRow[i].getChar()!=currentChar) {
	                System.out.println("For Hard Rule: Fails because a green letter is not in the correct position: " + currentChar);
	                System.out.flush();
	                return false;
	            }

	            // Rule 3: Yellow letters must be included but not in the same positions
	        //    if (yellowLetters.contains(currentCharStr) && currentRow[i].getBackgroundColor() == AppColors.GREEN) {
	           //     System.out.println("For Hard Rule: Fails because a yellow letter is in the wrong position: " + currentChar);
	           //     return false;
	          //  }
	        }

	        // Ensure all discovered yellow letters are included in the guess
	        for (String yellowLetter : yellowLetters) {
	            boolean found = false;
	            for (WordleResponse response : currentRow) {
	                if (String.valueOf(response.getChar()).equals(yellowLetter)) {
	                    found = true;
	                    break;
	                }
	            }
	            if (!found) {
	                System.out.println("For Hard Rule: Fails because your guess does not include all discovered yellow letters: " + yellowLetter);
	                return false;
	            }
	        }
	        for(int i =0; i<5;++i)
	        {
	        	
	        }
	        System.out.println("Hard Rule: You passed! True!");
	        return true;
	    }


	
}