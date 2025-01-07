package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.Color;


import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.f2024.wordle.controller.KeyboardButtonAction;
import edu.wm.cs.cs301.f2024.wordle.model.AppColors;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.model.Model;
import edu.wm.cs.cs301.f2024.wordle.model.AbsurdleModel;
public class KeyboardPanel {

	private int buttonIndex, buttonCount;
	private int once,twice,thrice;
	private final JButton[] buttons;

	private final JPanel panel;

	private final KeyboardButtonAction action;
	
	//private final WordleModel model;
	private final Model model;
	private JPanel totalPanel;
	private JLabel totalLabel;
	//private JLabel label;
	//(WordleModel model -> Model model)
	public KeyboardPanel(WordleFrame view, WordleModel model) {
		this.model = model;
		this.once = 0;
		this.twice = 0;
		this.thrice = 0;
		this.buttonIndex = 0;
		//double check Label
		//this.label = new JLabel();
		this.buttonCount = firstRow().length + secondRow().length
				+ thirdRow().length + helpRow().length;
		//this.buttonCount = 31;
		this.buttons = new JButton[buttonCount];
		this.action = new KeyboardButtonAction(view, model);
		this.totalPanel = createTotalPanel();
		this.panel = createMainPanel();
		helpPanelColor();
		
	    //model.addChangeListener(e -> updateLabel());
        //updateLabel(); // Initialize the label with current model data
	}
	public KeyboardPanel(WordleFrame view, AbsurdleModel model) {
		this.model = model;
		this.buttonIndex = 0;
		//double check Label
		//this.label = new JLabel();
		this.buttonCount = firstRow().length + secondRow().length
				+ thirdRow().length + helpRow().length;
		//this.buttonCount =31;
		this.buttons = new JButton[buttonCount];
		this.action = new KeyboardButtonAction(view, model);
		//this.totalPanel = createTotalPanel();
		this.panel = createMainPanel();
		this.totalPanel = createTotalPanel();
		helpPanelColor();
	    //model.addChangeListener(e -> updateLabel());
        //updateLabel(); // Initialize the label with current model data
	}
	public void updateTotalLabel() {
	    String text = String.format("%,d", model.getTotalWordCount());
	    text += " possible " + model.getColumnCount() + "-letter words!";
	    totalLabel.setText(text);
		
	}
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 0, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

		panel.add(createQPanel());
		panel.add(createAPanel());
		panel.add(createZPanel());
		//panel.add(totalPanel);
		panel.add(createHelpPanel());
		return panel;
	}

	private JPanel createQPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		Font textfont = AppFonts.getTextFont();

		String[] letters = firstRow();

		for (int index = 0; index < letters.length; index++) {
			JButton button = new JButton(letters[index]);
			setKeyBinding(button, letters[index]);
			button.addActionListener(action);
			button.setFont(textfont);
			buttons[buttonIndex++] = button;
			panel.add(button);
		}

		return panel;
	}

	private String[] firstRow() {
		String[] letters = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
				"Backspace" };
		return letters;
	}

	private JPanel createAPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		Font textfont = AppFonts.getTextFont();

		String[] letters = secondRow();

		for (int index = 0; index < letters.length; index++) {
			JButton button = new JButton(letters[index]);
			setKeyBinding(button, letters[index]);
			button.addActionListener(action);
			button.setFont(textfont);
			buttons[buttonIndex++] = button;
			panel.add(button);
		}

		return panel;
	}

	private String[] secondRow() {
		String[] letters = { "A", "S", "D", "F", "G", "H", "J", "K", "L",
				"Enter" };
		return letters;
	}

	private JPanel createZPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		Font textfont = AppFonts.getTextFont();

		String[] letters = thirdRow();

		for (int index = 0; index < letters.length; index++) {
			JButton button = new JButton(letters[index]);
			setKeyBinding(button, letters[index]);
			button.addActionListener(action);
			button.setFont(textfont);
			buttons[buttonIndex++] = button;
			panel.add(button);
		}

		return panel;
	}
	private JPanel createHelpPanel()
	{
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0,5));
		Font textfont = AppFonts.getTextFont();
		System.out.println("Buttons count: " + this.getButtonCount());
		String[] helpWords = helpRow();
		
		for (int index = 0; index<helpWords.length; index++)
		{
			JButton button = new JButton(helpWords[index]);
			
			button.addActionListener(action);
			button.setFont(textfont);
			
			buttons[buttonIndex++] = button;
			
			panel.add(button);
			
		}
		
		return panel;
		
	}
	private int getButtonCount()
	{
		return this.buttonCount;
	}
	private void helpPanelColor()
	{
		
			setColor("Once",AppColors.GREEN, AppColors.WHITE);
			setColor("Twice",AppColors.YELLOW, AppColors.WHITE);
			setColor("Thrice",AppColors.GRAY, AppColors.WHITE);

		
	}
	private String[] helpRow()
	{
		String[] helpWords = { "Once", "Twice", "Thrice"};
		return helpWords; 
	}
	
	private String[] thirdRow() {
		String[] letters = { "Z", "X", "C", "V", "B", "N", "M" };
		return letters;
	}

	private void setKeyBinding(JButton button, String text) {
		InputMap inputMap = button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		if (text.equalsIgnoreCase("Backspace")) {
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
					"action");
		} else {
			inputMap.put(KeyStroke.getKeyStroke(text.toUpperCase()), "action");
		}
		ActionMap actionMap = button.getActionMap();
		actionMap.put("action", action);
	}

	private JPanel createTotalPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		Font footerFont = AppFonts.getFooterFont();

		String text = String.format("%,d", model.getTotalWordCount());
		text += " possible " + model.getColumnCount() + "-letter words!";
		totalLabel = new JLabel(text);
		totalLabel.setFont(footerFont);
		panel.add(totalLabel);
		
		return panel;
	}

	public void setColor(String letter, Color backgroundColor,
			Color foregroundColor) {
		for (JButton button : buttons) {
			if (button.getActionCommand().equals(letter)) {
				button.setOpaque(true);
				button.setBorderPainted(false);
				Color color = button.getBackground();
				if (color.equals(AppColors.GREEN)) {
					// Do nothing
				} else if (color.equals(AppColors.YELLOW)
						&& backgroundColor.equals(AppColors.GREEN)) {
					button.setBackground(backgroundColor);
					button.setForeground(foregroundColor);
				} else {
					button.setBackground(backgroundColor);
					button.setForeground(foregroundColor);
				}
				break;
			}
		}
	}
	public boolean useOnceButton()
	{
		if(this.once==1)
		{
			return false;
		}
		this.once++;
		System.out.println("Once value: " + this.once);
		return true;
	}
	public boolean useTwiceButton()
	{
		if(this.twice == 2)
		{
			return false;
		}
		this.twice++;
		System.out.println("Twice value: " + this.twice);
		return true;
	}
	public boolean useThriceButton()
	{
		if(this.thrice==3)
		{
			return false;
		}
		this.thrice++;
		System.out.println("Thrice value: " + this.thrice);

		return true;
	}
	public void resetDefaultColors() {
		for (JButton button : buttons) {
			button.setOpaque(true);
			button.setBorderPainted(false);
			button.setBackground(null);
			button.setForeground(null);
		}
	}

	public JPanel getPanel() {
		return panel;
	}
	public JPanel getTotalPanel()
	{
		return totalPanel;
	}
	public JButton getButtonForKey(String letter)
	{
		for (JButton button: buttons)
		{
			System.out.println(button.getActionCommand());
			if(button.getActionCommand().equals(letter))
					{
						return button;
					}
		}
		return null;
		
	}

}
