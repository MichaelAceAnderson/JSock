package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleInteraction {
	// ANSI COLOR CODES
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_BLACK = "\u001B[30m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_YELLOW = "\u001B[33m";
	public static final String TEXT_BLUE = "\u001B[34m";
	public static final String TEXT_PURPLE = "\u001B[35m";
	public static final String TEXT_CYAN = "\u001B[36m";
	public static final String TEXT_WHITE = "\u001B[37m";

	public static final String TEXT_BLACK_BACKGROUND = "\u001B[40m";
	public static final String TEXT_RED_BACKGROUND = "\u001B[41m";
	public static final String TEXT_GREEN_BACKGROUND = "\u001B[42m";
	public static final String TEXT_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String TEXT_BLUE_BACKGROUND = "\u001B[44m";
	public static final String TEXT_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String TEXT_CYAN_BACKGROUND = "\u001B[46m";
	public static final String TEXT_WHITE_BACKGROUND = "\u001B[47m";

	/**
	 * Reads the first character of user input
	 *
	 * @return the integer value of the read character
	 */
	public static int readCharInput() {
		int keyCode = -1;
		try {
			// Create an input reader on the console (System.in)
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			// Read the first entered character
			keyCode = reader.read();
		} catch (Exception e) {
			ConsoleInteraction.printC("Please enter a valid input!", ConsoleInteraction.TEXT_RED);
		}
		return keyCode;
	}

	/**
	 * Reads user input
	 *
	 * @return the user input
	 */
	public static String readStringInput() {
		String input = "";
		try {
			// Create a console reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			// Read the user input
			input = reader.readLine();
		} catch (Exception e) {
			ConsoleInteraction.printC("Please enter a valid input!", ConsoleInteraction.TEXT_RED);
		}
		return input;
	}

	/**
	 * Reads an integer entered by the user
	 *
	 * @return the integer entered by the user
	 */
	public static int readIntInput() {
		int input = -1;
		try {
			input = Integer.parseInt(ConsoleInteraction.readStringInput());
		} catch (NumberFormatException e) {
			ConsoleInteraction.printC("Please enter an integer!", ConsoleInteraction.TEXT_RED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * Proposes a numeric choice to the user between 2 values (inclusive)
	 * 
	 * @param min The minimum value (inclusive)
	 * @param max The maximum value (inclusive)
	 */
	public static int readIntChoice(int min, int max) {
		int choice = ConsoleInteraction.readIntInput();
		// While the user has not chosen a valid option
		while (choice < min || choice > max) {
			// Inform the user that their choice is invalid
			ConsoleInteraction.printC("Please enter a valid choice!", ConsoleInteraction.TEXT_RED);
			// Get the user's choice
			choice = ConsoleInteraction.readIntInput();
		}
		// Return the user's choice
		return choice;
	}

	/**
	 * Displays colored text in the console
	 * 
	 * @param text  The text to display
	 * @param color The color of the text
	 * 
	 * @see ConsoleInteraction#TEXT_RESET
	 */
	public static void printC(String text, String color) {
		System.out.println(color + text + ConsoleInteraction.TEXT_RESET);
	}

	/**
	 * Displays text in the console
	 */
	public static void printC(String text) {
		ConsoleInteraction.printC(text, ConsoleInteraction.TEXT_RESET);
	}

	/**
	 * Displays informational text surrounded by dashes
	 * 
	 * @param text The text to display
	 * 
	 * @see ConsoleInteraction#TEXT_RESET
	 */
	public static void printInfo(String text) {
		ConsoleInteraction.printC("========================================",
				ConsoleInteraction.TEXT_WHITE_BACKGROUND + ConsoleInteraction.TEXT_BLACK);
		ConsoleInteraction.printC(text, ConsoleInteraction.TEXT_PURPLE);
		ConsoleInteraction.printC("========================================",
				ConsoleInteraction.TEXT_WHITE_BACKGROUND + ConsoleInteraction.TEXT_BLACK);
	}

}
