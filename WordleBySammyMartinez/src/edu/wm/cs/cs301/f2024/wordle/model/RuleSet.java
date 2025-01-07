package edu.wm.cs.cs301.f2024.wordle.model;


import edu.wm.cs.cs301.f2024.wordle.model.AcceptanceRule;
import edu.wm.cs.cs301.f2024.wordle.model.RuleBasic;
import edu.wm.cs.cs301.f2024.wordle.model.RuleHard;
import edu.wm.cs.cs301.f2024.wordle.model.RuleLegitimateWordsOnly;
import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.util.List;
import java.util.Random;

public class RuleSet {
    // Static field to store the rule set (shared across all instances)
    private static int ruleSet;

    // Sets the rule set
    public void setRuleSet(int x) {
        RuleSet.ruleSet = x; // Set the static field
    }

    // Gets the rule set
    public int getRuleSet() {
        return RuleSet.ruleSet; // Access the static field
    }
}