package edu.wm.cs.cs301.f2024.wordle.view;


public class AppStrings {

	// AboutDialog
    public static final String ABOUT_TITLE = "About";
    public static final String ABOUT_LABEL_TEXT = "About Wordle";
    public static final String ABOUT_DESCRIPTION = 
            "Wordle was created by software engineer and former Reddit employee, "
            + "Josh Wardle, and was launched in October 2021.";
    public static final String AUTHOR_LABEL = "Author:";
    public static final String AUTHOR_NAME = "Gilbert G. Le Blanc";
    public static final String DATE_CREATED_LABEL = "Date Created:";
    public static final String DATE_CREATED = "31 March 2022";
    public static final String VERSION_LABEL = "Version:";
    public static final String CANCEL_ACTION_LABEL = "cancelAction";
    public static final String VERSION = "1.0";
    /**
     * AboutDialog, InstructionsDialog, SettingsDialog
     */
    public static final String CANCEL_BUTTON_TEXT = "Cancel";

    // InstructionsDialog
    public static final String INSTRUCTIONS_TITLE = "Instructions";
    public static final String INSTRUCTIONS_HTML_PATH = "/resources/instructions.htm";
    // SettingsDialog Strings
    public static final String SETTINGS_TITLE = "Settings";
    public static final String SETTINGS_HTML_PATH = "/resources/settings.htm";
    
    
    // StatisticsDialog Strings
    public static final String STATISTICS_LABEL = "Statistics";
    public static final String GUESS_DISTRIBUTION_LABEL = "Guess Distribution";
    public static final String PLAYED_LABEL = "Played";
    public static final String WIN_PERCENT_LABEL = "Win %";
    public static final String CURRENT_STREAK_LABEL = "Current";
    public static final String LONGEST_STREAK_LABEL = "Longest";
    public static final String STREAK_LABEL = "Streak";
    public static final String NEXT_BUTTON_TEXT = "Next Word";
    public static final String EXIT_BUTTON_TEXT = "Exit Wordle";
    public static final String NEXT_ACTION = "nextAction";
    public static final String EXIT_ACTION = "exitAction";
    
    // WordleFrame
    public static final String WORDLE_LABEL = "Wordle";
    public static final String HELP_MENU_TITLE = "Help";
    public static final String INSTRUCTIONS_ITEM_TITLE = "Instructions...";
    public static final String SETTINGS_ITEM_TITLE = "Settings...";
    public static final String ABOUT_ITEM_TITLE = "About...";
    public static final String FRAME_SIZE_LABEL = "Frame Size: ";
    
    
    private AppStrings() {
        //constructor
    }
}