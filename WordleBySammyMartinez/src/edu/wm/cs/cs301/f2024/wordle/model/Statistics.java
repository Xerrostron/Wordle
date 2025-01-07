package edu.wm.cs.cs301.f2024.wordle.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Statistics implements Runnable {
    private int currentStreak, longestStreak, totalGamesPlayed;
    private List<Integer> wordsGuessed;
    private final String path, log;
    private static final Logger logger = Logger.getLogger(Statistics.class.getName());
    /*
     * generic lock to use for read/write operations
     */
    private final Object lock = new Object();
    /*
     * Volatile - used for multithreading (want the current, recently updated value)
     * As read or write methods may both acquire the lock, this logic helps alleviate concurrency issues
     */
    private volatile boolean dataLoaded = false;

    public Statistics() {
        this.wordsGuessed = new ArrayList<>();
        String fileSeparator = System.getProperty("file.separator");
        this.path = System.getProperty("user.home") + fileSeparator + "Wordle";
        this.log = fileSeparator + "statistics.log";
        
        // Start background thread to read stats
        Thread statisticsThread = new Thread(this);
        /*
         * Start initiates the run function
         * The run function will read and load the Statistics for the object in use of the Game
         */
        statisticsThread.start();
    }
    /**
     * run() will read and load the statistics file. Other methods are blocked until this is done
     */
    @Override
    public void run() {
        synchronized (lock) {
            readStatistics();
            dataLoaded = true;
            lock.notifyAll();  // Notify waiting threads that data is loaded
        }
    }

    private void readStatistics() {
        synchronized (lock) {
            if (!dataLoaded) {
                dataLoaded = true;
                try (BufferedReader br = new BufferedReader(new FileReader(path + log))) {
                    this.currentStreak = Integer.parseInt(br.readLine().trim());
                    this.longestStreak = Integer.parseInt(br.readLine().trim());
                    this.totalGamesPlayed = Integer.parseInt(br.readLine().trim());
                    int totalWordsGuessed = Integer.parseInt(br.readLine().trim());

                    for (int index = 0; index < totalWordsGuessed; index++) {
                        wordsGuessed.add(Integer.parseInt(br.readLine().trim()));
                    }
                    logger.info("Statistics loaded successfully.");
                } catch (FileNotFoundException e) {
                    logger.warning("Statistics file not found. Initializing default values.");
                    this.currentStreak = 0;
                    this.longestStreak = 0;
                    this.totalGamesPlayed = 0;
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error reading statistics file", e);
                }
            }
        }
    }


    public void writeStatistics() {
        synchronized (lock) {
            // Wait until data is fully loaded by the background thread
            while (!dataLoaded) {
                try {
                	logger.log(Level.INFO, "Writing functionality WAITING for readStatistics()");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.warning("Thread interrupted while waiting for data to load.");
                }
            }
            
            try {
                File file = new File(path);
                file.mkdir();
                file = new File(path + log);
                file.createNewFile();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(Integer.toString(currentStreak));
                    bw.newLine();
                    bw.write(Integer.toString(longestStreak));
                    bw.newLine();
                    bw.write(Integer.toString(totalGamesPlayed));
                    bw.newLine();
                    bw.write(Integer.toString(wordsGuessed.size()));
                    bw.newLine();

                    for (Integer value : wordsGuessed) {
                        bw.write(Integer.toString(value));
                        bw.newLine();
                    }
                }
                logger.info("Statistics written successfully.");
               // dataLoaded = false; After a write, any use of an old read should be considered useless
                
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error writing statistics file", e);
            }
        }
    }

    // Additional getter and setter methods, similar to your original code.
    public int getCurrentStreak() {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	System.out.println("current streak Class: " + currentStreak);
            return currentStreak;
        }
    }

    public void setCurrentStreak(int currentStreak) {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        //	logger.info("WRITING the current streak to: " + currentStreak);
            this.currentStreak = currentStreak;
            if (currentStreak > longestStreak) {
                this.longestStreak = currentStreak;
            }
        	logger.info("WRITING the current streak to: " + currentStreak);

        }
        
    }

    public int getLongestStreak() {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	logger.info("READING the longestStreak: " + longestStreak);
            return longestStreak;
        }
    }

    public int getTotalGamesPlayed() {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	 logger.info("READING getTotalGamesPlayed(): " + totalGamesPlayed);
            return totalGamesPlayed;
        }
    }

    public void incrementTotalGamesPlayed() {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	logger.info("WRITNG the totalGamesPlayed: " + totalGamesPlayed);
            this.totalGamesPlayed++;
        }
    }

    private List<Integer> getWordsGuessed() {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	logger.info("READING the wordsGuessed: " + wordsGuessed);
            return new ArrayList<>(wordsGuessed);
        }
    }

    public void addWordsGuessed(int wordCount) {
        synchronized (lock) {
        	  while (!dataLoaded) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      logger.warning("Trying to access/change data before load.");
                  }
              }
        	logger.info("WRITING addWordsGuessed: " + wordCount);
            this.wordsGuessed.add(wordCount);
        }
    }
    public int getTotalGamesWon()
    {
    	List<Integer> wonWords = getWordsGuessed();
    	return wonWords.size();
    }
    public int getLastWin()
    {
    	List<Integer> wonWords = getWordsGuessed();
    	int x = wonWords.get(wonWords.size()-1);
    	return x;
    }
    /**
     * Calculate for parameter maximum Tries the array for that number of tries!
     * @param maximumTries
     * @return
     */
    public int[] calculateArrayOfWins(int maximumTries)
    {
    	List<Integer> wonWordsTries = new ArrayList<>();
    	List<Integer> wonWords = getWordsGuessed();
    	for(int i = 0; i< wonWords.size(); ++i)
    	{
    		if(wonWords.get(i)==maximumTries)
    		{
    			wonWordsTries.add(wonWords.get(i));
    		}
    	}
    	int[] wonWordsArray = new int[wonWordsTries.size()];
    	int counter = 0;
    	for(int i =0; i < wonWordsTries.size();++i)
    	{
    		wonWordsArray[i] = wonWordsTries.get(i);
    		++counter;
    	}
    	System.out.println("Array of Wins for: " + maximumTries + " tries: " + counter);
    	return wonWordsArray;
    }
}

