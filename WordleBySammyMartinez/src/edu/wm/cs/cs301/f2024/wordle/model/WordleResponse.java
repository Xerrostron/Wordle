package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;


public class WordleResponse {
	/**
	 * An unchangeable char, that is used as a part of the WordleResponse
	 */
	private final char c;
	/**
	 * ColorResponse instance used in the WordleResponse to connect a char response to
	 * a foreground and background color
	 */
	private final ColorResponse colorResponse;
	/** NOTE: A 2D array of WordleResponse is used in WordleModel.java
	 * WordleResponse constructor
	 * @param c is the character used for a cell in the wordleGrid
	 * @param backgroundColor the background color of a cell in wordleGrid
	 * @param foregroundColor the foreground color of a cell in wordleGrid
	 */
	public WordleResponse(char c, Color backgroundColor, Color foregroundColor) {
		this.c = c;
		this.colorResponse = new ColorResponse(backgroundColor, foregroundColor);
	}
	/**
	 * Gets the char c field member of instance
	 * @return the char c
	 */
	public char getChar() {
		return c;
	}
	/**
	 * gets the backgroundcolor
	 * @return the backgroundcolor
	 */
	public Color getBackgroundColor() {
		return colorResponse.getBackgroundColor();
	}
	/**
	 * gets the foreground color
	 * @return the foreground color
	 */
	public Color getForegroundColor() {
		return colorResponse.getForegroundColor();
	}
	
}
