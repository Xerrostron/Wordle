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

public class RuleLegitimateWordsOnly implements AcceptanceRule{

	@Override
	public boolean isAcceptableGuess(Model model) {
		RuleBasic basicRule = new RuleBasic();
		if(basicRule.isAcceptableGuess(model)==true)
		{
			WordleResponse[] currentRow = model.getCurrentRowTest();
			//recreate guess since private
			char[] guess = new char[currentRow.length];
			for(int i = 0; i<currentRow.length; ++i)
			{
				
				char tester = currentRow[i].getChar();
				guess[i] = tester;
			}
			model.getWordList();
			String guessWord = new String(guess);
			if(model.getWordList().contains(guessWord.toLowerCase()))
			{
				System.out.println("According to Legitimate Word Rules, this guess is VALID");
				return true;
			}
		}
		System.out.println("According to Legitimate Word Rules, this guess is INVALID");

		return false;
	}
	
}