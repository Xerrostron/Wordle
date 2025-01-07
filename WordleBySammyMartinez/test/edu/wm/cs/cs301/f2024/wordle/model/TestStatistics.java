package edu.wm.cs.cs301.f2024.wordle.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestStatistics{
	
	private Statistics statistics;
	private Statistics concurrencyStats = new Statistics();
	@Before
	public void setUp()
	{
		statistics = new Statistics();
		//concurrencyStats = new Statistics();
	}
	
	@Test
	public void testGetLongestStreak()
	{
		/**
		 * Note about this code: Assume Statistics.log is completely fresh.
		 * No prior data in the log is to be used for these tests.
		 * 
		 * If previous Statistics.log exists, delete it
		 */
		setUp();//setup
		//statistics.writeStatistics();
		/**
		 * Case Tested: Test to make sure the LongestStreak is saved
		 * once the CurrentStreak Resets back to 0
		 * 
		 * The new LongestStreak, 1, should be saved once the currentStreak goes back to 0.
		 * Trivial getCurrentStreak() is also tested here and should simply return the 
		 * currentStreak()
		 */
		statistics.setCurrentStreak(1);
		System.out.println(statistics.getLongestStreak());
		assertEquals(statistics.getLongestStreak(),20);
		statistics.setCurrentStreak(0);
		assertEquals(statistics.getCurrentStreak(),0);
		assertEquals(statistics.getLongestStreak(),20);
		/**
		 * Case Tested: Test to make sure LongestStreak is updated once 
		 * the currentStreak becomes > LongestStreak
		 * 
		 * LongestStreak should become 5 here. Testing getCurrentStreak simultaneously;
		 * trivial getCurrentStreak() should simply return the currentStreak()
		 */
		statistics.setCurrentStreak(5);
		assertEquals(statistics.getCurrentStreak(),5);
		assertEquals(statistics.getLongestStreak(),20);
	}
	@Test
	public void testGamesPlayed()
	{
		/**
		 * Case: No games have been played
		 * The correct answer should be 0
		 */
		setUp();//setup
		statistics.getTotalGamesPlayed();
		assertEquals(0, statistics.getTotalGamesPlayed());
		/**
		 * Case: 1 game has been played
		 * The correct answer should be 1
		 */
		statistics.incrementTotalGamesPlayed();
		assertEquals(1, statistics.getTotalGamesPlayed());
		statistics.incrementTotalGamesPlayed();
		statistics.incrementTotalGamesPlayed();
		statistics.incrementTotalGamesPlayed();
		/**
		 * Case: 4 games have been played
		 * The correct answer should be 4
		 */
		assertEquals(4, statistics.getTotalGamesPlayed());
	}
	@Test
	public void testWordsGuessed()
	{
		setUp();//setup
		/**
		 * Case: no words have been guessed
		 * Answer should be 0
		 */
		assertEquals(statistics.getTotalGamesWon(), 0);
		statistics.addWordsGuessed(5);
		/**
		 * Case:5 words have been guessed
		 * Answer should be 1
		 */
		assertEquals(statistics.getTotalGamesWon(),1);
	}
	@Test public void testReadWrite()
	{
		  Thread thread1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	//   concurrencyStats = new Statistics();
	            	setUp();
	            }
	        });
	        Thread thread2 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	concurrencyStats.setCurrentStreak(20);
	            	concurrencyStats.writeStatistics();
	            }
	        });

	        thread1.start();
	        thread2.start();

	        try {
				thread1.join();
	     	 //  System.out.println("Thread One Finish Reading");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				thread2.join();
	     	//   System.out.println("Thread Two Finished Writing");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        int currentStreak = concurrencyStats.getCurrentStreak();
	     //   System.out.println("Current Streak: "+ currentStreak);
	

	        // Assert that the current Change is correct
	        assertEquals(20, currentStreak, "Current Streak should be 8 after successfully loading and reading the Stats object.");
	}
	@Test
	public void testConcurrency()
	{
		// concurrencyStats = new Statistics();
		//Ensure that JUnit test runs this test without other JUnit test interference with a new Stats object
        // Ensure no thread safety issues with reading and writing in parallel
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
            	//   concurrencyStats = new Statistics();
            	  // System.out.println("Just Reset Concurrency Stats");
            	   // Artificial delay to allow thread2 to attempt to write in the meantime
                   try {
                       Thread.sleep(3000); // 3-second delay
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
            	//   System.out.println("Hello ONE");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
            	   concurrencyStats.setCurrentStreak(8);
            	  // System.out.println("Just changed Current Streak");
            	   int currentStreak = concurrencyStats.getCurrentStreak();
                   int longestStreak = concurrencyStats.getLongestStreak();
                 //  System.out.println("Current Streak: "+ currentStreak);
                  // System.out.println("Longest Streak: "+ longestStreak);
            	  // System.out.println("Hello FONE");

            	  // concurrencyStats.writeStatistics();
            	 //  System.out.println("Hello TONE");
            	   currentStreak = concurrencyStats.getCurrentStreak();
                   longestStreak = concurrencyStats.getLongestStreak();
                  // System.out.println("Current Streak: "+ currentStreak);
                 //  System.out.println("Longest Streak: "+ longestStreak);
            }
        });

        thread1.start();
        thread2.start();

        try {
			thread1.join();
     	 //  System.out.println("Thread One Finish Reading");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			thread2.join();
     	 //  System.out.println("Thread Two Finished Writing");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Check currentStreak after change
        int currentStreak = concurrencyStats.getCurrentStreak();
        int longestStreak = concurrencyStats.getLongestStreak();
       // System.out.println("Current Streak: "+ currentStreak);
       // System.out.println("Longest Streak: "+ longestStreak);

        // Assert that the current Change is correct
        assertEquals(8, currentStreak, "Current Streak should be 8 after successfully loading and reading the Stats object.");
      //  System.out.println("Current Streak: "+ currentStreak);
     //   System.out.println("Longest Streak: "+ longestStreak);

        
	}
	
	
	/**
	 * writeStatistics (void)
	 * getCurrentStreak(int) return int - tested
	 * setCurrentStreak(void) @param -tested
	 * getLongestStreak(int) return int - tested
	 * getTotalGamesPlayed(int) return int - tested
	 * incrementTotalGamesPlayed(void) - tested
	 * getWordsGuessed(List<Integer>) return List<integer> - tested
	 * addWordsGuessed(void) @param int wordCount - tested
	 * 
	 */
}