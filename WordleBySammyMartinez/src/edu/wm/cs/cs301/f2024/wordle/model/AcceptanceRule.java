package edu.wm.cs.cs301.f2024.wordle.model;



import java.util.List;


import java.util.Random;

import javax.swing.event.EventListenerList;

import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;

public interface AcceptanceRule {
    /**
     * Determines whether a guess is acceptable based on the provided model.
     *
     * @param model The model used to evaluate the guess.
     * @return true if the guess is acceptable, false otherwise.
     */
    boolean isAcceptableGuess(Model model);
}