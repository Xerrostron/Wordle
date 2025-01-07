package edu.wm.cs.cs301.f2024.wordle.controller;

import javax.swing.SwingUtilities;

import edu.wm.cs.cs301.f2024.wordle.model.RuleSet;
import edu.wm.cs.cs301.f2024.wordle.model.AbsurdleModel;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.view.WordleFrame;

public class Wordle implements Runnable {
	/** Wordle class implements the Runnable interface.
	 * Class as a whole: makes a Wordle object, makes a new WordleFrame with a 
	 * WordleModel as the parameter. For a Swing application, attempts to resolve
	 * concurrency issues with .invokeLater
	 */
	
	private String mode;
	public Wordle(String mode)
	{
		this.mode = mode;
	}
	public void chooseStrategy(String[] args)
	{
		
	}
	public static void main(String[] args) {
        String mode = "random"; // Default mode
        RuleSet gameRules = new RuleSet();
        gameRules.setRuleSet(0);//Default Rule Set
        // Check command line arguments for the mode
        if (args.length > 0) {
        //    if ("absurdle".equalsIgnoreCase(args[0])) {
         //       mode = "absurdle";
          //  }
          //  if("wordle".equalsIgnoreCase(args[0]))
         //   {
         //   	mode = "wordle";
         //   }
        	/**
        	 * (Strategy) (HardMode) (ProperWords)
        	 * Order of derivation for mode/ruleset parser
        	 * 
        	 * 1 - hard,  2 - proper words, 3 - both hard and proper
        	 */
        	
            if("-s".equalsIgnoreCase(args[0]))
            {
            	 if ("absurdle".equalsIgnoreCase(args[1])) {
                     mode = "absurdle";
                 }
                 if("random".equalsIgnoreCase(args[1]))
                 {
                 	mode = "random";
                 }
                 if("-h".equalsIgnoreCase(args[2]))
                 {
                	// mode = "hard";
                	 gameRules.setRuleSet(1);
                	 if(args.length>3&&"-wh".equalsIgnoreCase(args[3]))
                		 {
                		 //	mode = "hardProperWordsOnly";
                		 	gameRules.setRuleSet(3);
                		 }
                 }
                 if("-wh".equalsIgnoreCase(args[2]))
                 {
                	// mode = "ProperWordsOnly";
                	 gameRules.setRuleSet(2);
                 }
            }
            if("-h".equalsIgnoreCase(args[0]))
            {
            	//mode = "hard";
            	gameRules.setRuleSet(1);
            	if(args.length>1&&"-wh".equalsIgnoreCase(args[1]))
            	{
            		//mode = "hardProperWordsOnly";
            		gameRules.setRuleSet(3);
            	}
            }
            if("-wh".equalsIgnoreCase(args[0]))
            {
            	//mode = "ProperWordsOnly";
            	gameRules.setRuleSet(2);
            }
            
        }
        System.out.println("Chosen Ruleset: " + gameRules.getRuleSet());
        System.out.println("Chosen Mode: " + mode);
		SwingUtilities.invokeLater(new Wordle(mode));
	}
	/**
	 * main class creates an instance of Wordle, and it uses SwingUtilities.invokeLater
	 * to resolve concurrency issues. 
	 * @param args - used for command line arguments. Usually put in main
	 */

	@Override
	public void run()
	{
		  //if (mode.equalsIgnoreCase("absurdle"))
		 // {
		//	  	System.out.println("I am here at absurdle");
	      //      new WordleFrame(new AbsurdleModel());
	     // }
		//  if(mode.equalsIgnoreCase("wordle"))
		//  {
		//	  	System.out.println("I am here at wordle");

		//	  	new WordleFrame(new WordleModel());
		//  }
		 
		   // random case
			if(mode == "random") {
	            if (Math.random() < 0.5)
	            {
				  	System.out.println("PLAYING Wordle");
	                new WordleFrame(new WordleModel());
	                
	            }
	            else
	            {
	             	System.out.println("PLAYING Absurdle");
		            new WordleFrame(new AbsurdleModel());
	            }
			}		
			if(mode == "absurdle")
			{
	           {
				  	System.out.println("PLAYING Absurdle");
	               new WordleFrame(new AbsurdleModel());
	                
	            }
			}
	      
	    }
	}
	/** Overrides the run class, this overwritten method will: create a new WordleFrame
	 * and with a new WordleModel as the parameter. This method is setup for the GUI 
	 * and logic for the Wordle Game. 
	 * 
	 * @param WordleModel() is a new instance of the WordleModel. The GUI uses the 
	 * WordleModel as a dependency. 
	 * 
	 */



//Current ChangeLog: added a wait to thread to process the wordList() before any other code runs on it 

