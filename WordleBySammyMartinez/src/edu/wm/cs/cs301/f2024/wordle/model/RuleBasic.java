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

public class RuleBasic implements AcceptanceRule{

	@Override
	/**
	 * Current Row is completely Filled out(has 5 letters
	 */
	//check for null value not space value
	public boolean isAcceptableGuess(Model model) {
		//getCurrentRow subtracts currentRow-1. Cant be used here
		//WordleResponse[] currentRow = model.getCurrentRowTest();
		int currentCol = model.getCurrentColumn();
		if(currentCol == 4)
		{
			System.out.println("By Basic rules: This IS an acceptable guess!");
			return true;
		}
		else
		{
			System.out.println("By Basic Rules: This is NOT an acceptable guess!");
			return false;
		}
	//	System.out.println("By Basic rules: This IS an acceptable guess!");
	//	return true;
	}
	
	
}