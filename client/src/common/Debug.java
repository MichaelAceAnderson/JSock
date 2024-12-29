package common;

public final class Debug {
	// Boolean variables defining the debug modes
	public static enum Mode {
		VERBOSE
	}

	// Initial states of the debug modes
	private static boolean VERBOSE_ENABLED = false;

	// States of the debug modes
	public static boolean[] modeState = {
		Debug.VERBOSE_ENABLED
	};

	/**
	 * Set the state of a debug mode
	 * 
	 * @param enabled True or false to enable or disable the mode
	 */
	public static void setModeState(Mode mode, boolean enabled) {
		modeState[mode.ordinal()] = enabled;
	}

	/**
	 * Get the state of a debug mode
	 * 
	 * @param mode Mode whose state we want to retrieve
	 */
	public static boolean getModeState(Mode mode) {
		return modeState[mode.ordinal()];
	}

	/**
	 * Get the debug modes
	 */
	public static String getModeStates() {
		String modes = "Enabled debug modes: \n";
		for (Mode mode : Mode.values()) {
			modes += mode.toString() + "=" + modeState[mode.ordinal()] + "\n";
		}
		return modes;
	}
}
