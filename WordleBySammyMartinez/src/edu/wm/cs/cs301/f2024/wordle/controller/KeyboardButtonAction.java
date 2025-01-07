package edu.wm.cs.cs301.f2024.wordle.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import edu.wm.cs.cs301.f2024.wordle.model.AppColors;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.model.Model;
import edu.wm.cs.cs301.f2024.wordle.model.WordleResponse;
import edu.wm.cs.cs301.f2024.wordle.view.StatisticsDialog;
import edu.wm.cs.cs301.f2024.wordle.view.WordleFrame;
import edu.wm.cs.cs301.f2024.wordle.model.AbsurdleModel;
import edu.wm.cs.cs301.f2024.wordle.model.AcceptanceRule;
import edu.wm.cs.cs301.f2024.wordle.model.RuleBasic;
import edu.wm.cs.cs301.f2024.wordle.model.RuleHard;
import edu.wm.cs.cs301.f2024.wordle.model.RuleLegitimateWordsOnly;

public class KeyboardButtonAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/**
	 * Serializable interface allows objects to be stored or save state of an object
	 * Checks class and object serivalVersionUID to ensure a match. 
	 */
	
	private final WordleFrame view;
	/**
	 * WordleFrame instance named view. Field member of KeyboardButtonAction. 
	 * Used to store an instance of WordleFrame. 
	 */
	//private final WordleModel model;
	private final Model model;
	/**
	 * 
	 * WordleModel instance named model. Field member of KeyboardButtonAction. 
	 * Used to store an instance of WordleModel. 
	 */
	//WordleModel model -> Model model
	public KeyboardButtonAction(WordleFrame view, Model model) {
		this.view = view;
		this.model = model;
		/**
		 *  view and model fields are initialized with @param WordleFrame view and
		 *  @param WordleModel model respectively. 
		 */
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		/**
		 * Overwritten method. @param event is a keyboard button press. 
		 */
		JButton button = (JButton) event.getSource();
		String text = button.getActionCommand();
		/**
		 * test is a high-level parse of the keyboard button pressed. Used for
		 * switch statements and conditionals in deciding what the game
		 *  should do for a keyboard press. 
		 *  SwitchDensityViolation no longer happens by putting code into methods
		 *  CognitiveComplexityViolation - already fixed with SwitchDensityViolation fix
		 */
		switch (text) {
		case "Enter":
			/**
			 * Enter action will ONLY perform an action if all the columns are filled.
			 */
		//	RuleBasic ruleBasic = new RuleBasic();
		//	RuleLegitimateWordsOnly ruleLegit = new RuleLegitimateWordsOnly();
		//	RuleHard ruleHard = new RuleHard();
		//	System.out.println(ruleBasic.isAcceptableGuess(model) + " Basic");
			
		//	System.out.println(ruleLegit.isAcceptableGuess(model) + " Legit");
			

		//	System.out.println(ruleHard.isAcceptableGuess(model) + " Hard");
		

		//	System.out.println(model.isValidWord()+ " Is valid");
			

			if(model.ApplyRuleSet()==false)
			{
		        JOptionPane.showMessageDialog(null, "MUST enter a valid, 5-letter, English word!", "Rules", JOptionPane.INFORMATION_MESSAGE);
		        
				break;
			}
			if (model.getCurrentColumn() >= (model.getColumnCount() - 1)) {
				boolean moreRows = model.setCurrentRow();
				
				/**
				 * If enter is pressed with a correct guess of a word, update
				 * game statistics, setColor from setting row,
				 */
				int greenCount = countGreenColumns();
				if (greenCount >= model.getColumnCount()) {
					updateStatistics();
				}
				
				/**
				 * If there are no more rows to use a guess, update the game statistics
				 */
				  else if (!moreRows) {
					view.updateTotalPanel();
					view.repaintWordleGridPanel();
					model.incrementTotalGamesPlayed();
					//model.getStatistics().incrementTotalGamesPlayed();
					model.setCurrentStreak(0);
					//model.getStatistics().setCurrentStreak(0);
					new StatisticsDialog(view, model);
				} else 
				{
					view.updateTotalPanel();
					view.repaintWordleGridPanel();
				}
				/**
				 * A normal guess. Repaint the panel and continue playing. 
				 */
			}
			
			break;
		  case "Once":
	            // Logic for the "Once" button
			  if(model.getCurrentRowNumber()!=-1)
	        	{
				  
				  	if(view.useOnceButton()==true)
				  	{
				  		JOptionPane.showMessageDialog(
		    	                null,
		    	                "You selected 'Once'. Perform your custom action here.",
		    	                "Help",
		    	                JOptionPane.INFORMATION_MESSAGE
		    	            );
	        			if(new String(model.getModelType()).equals("wordle"))
	        			{
	        				char[] currentWord = model.returnCurrentWord();
	        				System.out.println("current word: " + String.valueOf(currentWord));
	        				int currentCol = model.getCurrentColumn();
	        				currentCol++;
	        				if(currentCol ==5)
	        				{
	        					currentCol--;
	        				}
	        				System.out.println("current col: " + currentCol);
	        				for(int i =0; i< currentWord.length;++i)
	        				{
	        					if(i==currentCol)
	        					{
	        						System.out.println("Current word at index: " + i + " char value: " + currentWord[i]);
	        						model.setCurrentColumn(currentWord[i]);
	        						String currentChar = String.valueOf(currentWord[i]);
	        						view.setColor(currentChar, AppColors.GREEN, AppColors.OUTLINE);
	        						view.repaintWordleGridPanel();

	        					}
	        				}
	        			}
	        			if(new String(model.getModelType()).equals("absurdle"))
	        			{
	        				 System.out.println("Entered absurdle logic");

	        				    List<String> wordList = model.getWordList(); // Get the word list
	        				    int currentCol = model.getCurrentColumn(); // Get the current column
	        				    currentCol++;
	        				    if(currentCol==5)
	        				    {
	        				    	currentCol--;
	        				    }
	        				    System.out.println("Current column: " + currentCol);

	        				    // Randomly pick a word from the wordList
	        				    Random rand = new Random();
	        				    String randomWord = wordList.get(rand.nextInt(wordList.size()));
	        				    System.out.println("Randomly picked word from word list: " + randomWord);

	        				    // Get the letter at the current column position in the randomly picked word
	        				    char letterAtCurrentCol = randomWord.charAt(currentCol);
	        				    System.out.println("Letter at current column: " + letterAtCurrentCol);

	        				    // Update the model and view with this letter
	        				    model.setCurrentColumn(letterAtCurrentCol); // Update column in model
	        				    String currentChar = String.valueOf(letterAtCurrentCol);
	        				    view.setColor(currentChar, AppColors.GREEN, AppColors.OUTLINE);
	        					view.repaintWordleGridPanel();

	        			}

	        			

				  	}
				  	else
				  	{
				  		JOptionPane.showMessageDialog(
		    	                null,
		    	                "You selected 'Once' More than once. Can't use again!",
		    	                "Help",
		    	                JOptionPane.INFORMATION_MESSAGE
		    	            );
				  	}
	        	}
			  else
	        	{
	        		JOptionPane.showMessageDialog(
	    	                null,
	    	                "Help buttons dont work for first row.",
	    	                "Help",
	    	                JOptionPane.INFORMATION_MESSAGE
	    	            );
	        	}
	           
	            break;

	        case "Twice":
	            // Logic for the "Twice" button
	        	if(model.getCurrentRowNumber()!=-1)
	        	{
	        		if(view.useTwiceButton()==true)
	        		{
	        			JOptionPane.showMessageDialog(
		    	                null,
		    	                "You selected 'Twice'. Perform your custom action here.",
		    	                "Help",
		    	                JOptionPane.INFORMATION_MESSAGE
		    	            );
	        			if(new String(model.getModelType()).equals("wordle"))
	        			{
	        				System.out.println("Entered main logic");
		        			List<String> alphabet = new ArrayList<>();
		        		        for (char c = 'A'; c <= 'Z'; c++) {
		        		            alphabet.add(String.valueOf(c));
		        		        }

		        		
		        			List<String> lettersUsed = new ArrayList<>();
		        			for(int i =0; i< model.getCurrentRowNumberTest();++i)
		        			{
		        				WordleResponse[] currentRow = model.getProcessedRow(i);
		        				for(int x =0; x< 5; ++x)
		        				{
		        					lettersUsed.add(String.valueOf(currentRow[x].getChar()));
		        				}
		        			}
		        			List<String> currentWord = new ArrayList<>();
		        			for(int i =0; i< 5; ++i)
		        			{
		        				char[] curWord = model.returnCurrentWord();
		        				currentWord.add(String.valueOf(curWord[i]));
		        			}
		        			 List<String> unusedLetters = new ArrayList<>();
		        			    for (String letter : currentWord) {
		        			        if (!lettersUsed.contains(letter)) {
		        			            unusedLetters.add(letter);
		        			        }
		        			    }
		        			    if (!unusedLetters.isEmpty()) {
		        			        Random rand = new Random();
		        			        String randomLetter = unusedLetters.get(rand.nextInt(unusedLetters.size()));
		        			        System.out.println("Randomly picked letter from currentWord not used: " + randomLetter);
		        			        view.setColor(randomLetter, AppColors.YELLOW, AppColors.OUTLINE);
		        					view.repaintWordleGridPanel();

		        			    } else {
		        			        System.out.println("No unused letters available in currentWord.");
		        			    }
	        			}
	        			if(new String(model.getModelType()).equals("absurdle"))
	        			{
	        				  System.out.println("Entered absurdle logic");

	        				    // Step 1: Create a list of all alphabet letters
	        				    List<String> alphabet = new ArrayList<>();
	        				    for (char c = 'A'; c <= 'Z'; c++) {
	        				        alphabet.add(String.valueOf(c));
	        				    }

	        				    // Step 2: Create a set of all letters appearing in any word in the wordList
	        				    Set<String> lettersInWordList = new HashSet<>();
	        				    List<String> wordList = model.getWordList(); 
	        				    for (String word : wordList) {
	        				        for (char c : word.toCharArray()) {
	        				            lettersInWordList.add(String.valueOf(c));
	        				        }
	        				    }

	        				    // Step 3: Create a list of letters used by the current guess
	        				    List<String> lettersUsed = new ArrayList<>();
	        				    for (int i = 0; i < model.getCurrentRowNumberTest(); ++i) {
	        				        WordleResponse[] currentRow = model.getProcessedRow(i);
	        				        for (int x = 0; x < 5; ++x) {
	        				            lettersUsed.add(String.valueOf(currentRow[x].getChar()));
	        				        }
	        				    }

	        				    // Step 4: Create a list of available letters from the alphabet that are in the word list
	        				    List<String> availableLetters = new ArrayList<>(lettersInWordList);
	        				    availableLetters.removeAll(lettersUsed);

	        				    // Step 5: Randomly pick a letter from availableLetters
	        				    if (!availableLetters.isEmpty()) {
	        				        Random rand = new Random();
	        				        String randomLetter = availableLetters.get(rand.nextInt(availableLetters.size()));
	        				        System.out.println("Randomly picked letter from the word list: " + randomLetter);
	        				        view.setColor(randomLetter, AppColors.YELLOW, AppColors.OUTLINE);
	        						view.repaintWordleGridPanel();

	        				    } else {
	        				        System.out.println("No available letters to pick from.");
	        				    }
	        			}
	        		}
	        		else
	        		{
	        			JOptionPane.showMessageDialog(
		    	                null,
		    	                "You selected 'Twice' More than two times. Button unusable after 2X presses!",
		    	                "Help",
		    	                JOptionPane.INFORMATION_MESSAGE
		    	            );
	        		}
	        		
	        	}
	        	else
	        	{
	        		JOptionPane.showMessageDialog(
	    	                null,
	    	                "Help buttons dont work for first row.",
	    	                "Help",
	    	                JOptionPane.INFORMATION_MESSAGE
	    	            );
	        	}
	            break;

	        case "Thrice":
	            // Logic for the "Thrice" button
	        	if(model.getCurrentRowNumber()!=-1)
	        	{
	        		if(view.useThriceButton()==true)
	        	  {
	        			System.out.println("Model Type: " + String.valueOf(model.getModelType()));

	        			JOptionPane.showMessageDialog(
		    	                null,
		    	                "You selected 'Thrice'. Perform your custom action here.",
		    	                "Help",
		    	                JOptionPane.INFORMATION_MESSAGE
		    	            );
	        		if(new String(model.getModelType()).equals("wordle"))
	        		{
	        			System.out.println("Entered main logic");
	        			List<String> alphabet = new ArrayList<>();
	        		        for (char c = 'A'; c <= 'Z'; c++) {
	        		            alphabet.add(String.valueOf(c));
	        		        }

	        		
	        			List<String> lettersUsed = new ArrayList<>();
	        			for(int i =0; i< model.getCurrentRowNumberTest();++i)
	        			{
	        				WordleResponse[] currentRow = model.getProcessedRow(i);
	        				for(int x =0; x< 5; ++x)
	        				{
	        					lettersUsed.add(String.valueOf(currentRow[x].getChar()));
	        				}
	        			}
	        			List<String> currentWord = new ArrayList<>();
	        			for(int i =0; i< 5; ++i)
	        			{
	        				char[] curWord = model.returnCurrentWord();
	        				currentWord.add(String.valueOf(curWord[i]));
	        			}
	        			List<String> availableLetters = new ArrayList<>(alphabet);
	        	        availableLetters.removeAll(lettersUsed);
	        	        availableLetters.removeAll(currentWord);

	        	        // Step 4: Randomly pick a letter from the available letters
	        	        if (!availableLetters.isEmpty()) {
	        	            Random rand = new Random();
	        	            String randomLetter = availableLetters.get(rand.nextInt(availableLetters.size()));
	        	            System.out.println("Randomly picked letter: " + randomLetter);
	        	            view.setColor(randomLetter, AppColors.GRAY, AppColors.OUTLINE);
	        				view.repaintWordleGridPanel();

	        	        } else {
	        	            System.out.println("No unused letters available.");
	        	        }
	        		}
	        		if (new String(model.getModelType()).equals("absurdle")) {
	        		    System.out.println("Entered absurdle logic");

	        		    // Step 1: Create a list of all alphabet letters
	        		    List<String> alphabet = new ArrayList<>();
	        		    for (char c = 'A'; c <= 'Z'; c++) {
	        		        alphabet.add(String.valueOf(c));
	        		    }

	        		    
	        		    Set<String> lettersInWordList = new HashSet<>();
	        		    List<String> wordList = model.getWordList(); 
	        		    for (String word : wordList) {
	        		        for (char c : word.toCharArray()) {
	        		            lettersInWordList.add(String.valueOf(c));
	        		        }
	        		    }

	        		    // Step 3: Create a list of letters used by the current guess
	        		    List<String> lettersUsed = new ArrayList<>();
	        		    for (int i = 0; i < model.getCurrentRowNumberTest(); ++i) {
	        		        WordleResponse[] currentRow = model.getProcessedRow(i);
	        		        for (int x = 0; x < 5; ++x) {
	        		            lettersUsed.add(String.valueOf(currentRow[x].getChar()));
	        		        }
	        		    }

	        		    // Step 4: Create a list of available letters that are not used and not in the word list
	        		    List<String> availableLetters = new ArrayList<>(alphabet);
	        		    availableLetters.removeAll(lettersUsed);
	        		    availableLetters.removeAll(lettersInWordList);

	        		    // Step 5: Randomly pick a letter from the available letters
	        		    if (!availableLetters.isEmpty()) {
	        		        Random rand = new Random();
	        		        String randomLetter = availableLetters.get(rand.nextInt(availableLetters.size()));
	        		        System.out.println("Randomly picked letter: " + randomLetter);
	        		        view.setColor(randomLetter, AppColors.GRAY, AppColors.OUTLINE);
	        				view.repaintWordleGridPanel();

	        		    } else {
	        		        System.out.println("No unused letters available.");
	        		    }
	        		}
	        	
	        	  }
	        		else
	        		{
	        			System.out.println("Can't use thrice button more than 3x times");
	        		}
	        	}
	        	else
	        	{
	        		JOptionPane.showMessageDialog(
	    	                null,
	    	                "Help buttons dont work for first row.",
	    	                "Help",
	    	                JOptionPane.INFORMATION_MESSAGE
	    	            );
	        	}
	        
	            break;

		case "Backspace":
			/**
			 * Invokes the backspace() method from WordleModel class, and then
			 * repaints the Wordle game to reflect the backspace keyboard press. 
			 */
			model.backspace();
			view.repaintWordleGridPanel();
			break;
		default:
			/**
			 * the default case would, ideally, be an alphabetical letter. The 
			 * game board will be reflected with the answer guess, and the logic of the 
			 * game will be adjusted. 
			 */
			model.setCurrentColumn(text.charAt(0));
			view.repaintWordleGridPanel();
			break;
		}
		/**
		 * 
		 */
	}
	private int countGreenColumns()
	{
		int greenCount = 0;
		WordleResponse[] currentRow = model.getCurrentRow();
		for (WordleResponse wordleResponse : currentRow) {
			view.setColor(Character.toString(wordleResponse.getChar()),
					wordleResponse.getBackgroundColor(), 
					wordleResponse.getForegroundColor());
			if (wordleResponse.getBackgroundColor().equals(AppColors.GREEN)) {
				greenCount++;
			} 
		}
		return greenCount;
	}
	private void updateStatistics()
	{
		view.repaintWordleGridPanel();
		model.incrementTotalGamesPlayed();
		//model.getStatistics().incrementTotalGamesPlayed();
		int currentRowNumber = model.getCurrentRowNumber();
		model.addWordsGuessed(currentRowNumber);
		//model.getStatistics().addWordsGuessed(currentRowNumber);
		int currentStreak = model.getCurrentStreak();
		//int currentStreak = model.getStatistics().getCurrentStreak();
		model.setCurrentStreak(++currentStreak);
		//model.getStatistics().setCurrentStreak(++currentStreak);
		new StatisticsDialog(view, model);
	}

}
