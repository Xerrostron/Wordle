package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;

public class ColorResponse {
	/**
	 * 2 instances of Color class: one used for backgroundColor, another used
	 * for foregroundColor
	 */
	private final Color backgroundColor, foregroundColor;
	/**
	 * Constructor for ColorResponse
	 * @param backgroundColor - takes in an object of color for the backgroundColor
	 * @param foregroundColor - takes in an object of color for the foregroundColor
	 */
	public ColorResponse(Color backgroundColor, Color foregroundColor) {
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
	}
	/**
	 * gets the backgroundColor
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * gets the foregroundColor
	 * @return the foregroundColor
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

}
