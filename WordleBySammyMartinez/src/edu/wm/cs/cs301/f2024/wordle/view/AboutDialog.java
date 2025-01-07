package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import edu.wm.cs.cs301.f2024.wordle.view.AppStrings;

public class AboutDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;

	public AboutDialog(WordleFrame view) {
		super(view.getFrame(), AppStrings.ABOUT_TITLE, true);
		this.cancelAction = new CancelAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		Font titleFont = AppFonts.getTitleFont();
		Font textFont = AppFonts.getTextFont();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 5, 30);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = new JLabel(AppStrings.ABOUT_LABEL_TEXT);
		label.setFont(titleFont);
		label.setHorizontalAlignment(JLabel.CENTER);
		Color backgroundColor = label.getBackground();
		panel.add(label, gbc);
		
		gbc.gridy++;
	//	String text = "Wordle was created by software engineer "
			//	+ "and former Reddit employee, Josh Wardle, and "
			//	+ "was launched in October 2021.";
		String text = AppStrings.ABOUT_DESCRIPTION;
		JTextArea textArea = new JTextArea(4, 25);
		textArea.setBackground(backgroundColor);
		textArea.setEditable(false);
		textArea.setFont(textFont);
		textArea.setLineWrap(true);
		textArea.setText(text);
		textArea.setWrapStyleWord(true);
		panel.add(textArea, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		label = new JLabel(AppStrings.AUTHOR_LABEL);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(AppStrings.AUTHOR_NAME);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel(AppStrings.DATE_CREATED_LABEL);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(AppStrings.DATE_CREATED);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel(AppStrings.VERSION_LABEL);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel(AppStrings.VERSION);
		label.setFont(textFont);
		panel.add(label, gbc);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), AppStrings.CANCEL_ACTION_LABEL);
		ActionMap actionMap = panel.getActionMap();
		actionMap.put(AppStrings.CANCEL_ACTION_LABEL, cancelAction);
		
		JButton button = new JButton(AppStrings.CANCEL_BUTTON_TEXT);
		button.addActionListener(cancelAction);
		panel.add(button);
		
		return panel;
	}
	
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
		
	}

}
