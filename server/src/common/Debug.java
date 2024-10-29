package common;

public final class Debug {
	// Boolean variables defining the debugging modes
	public static enum Mode {
		VERBOSE
	}

	// Initial states of the debugging modes
	private static boolean VERBOSE_ENABLED = false;

	// States of the debugging modes
	public static boolean[] modeState = {
		Debug.VERBOSE_ENABLED
	};

	/**
	 * Set the state of a debugging mode
	 * 
	 * @param enabled True or false to enable or disable the mode
	 */
	public static void setModeState(Mode mode, boolean enabled) {
		modeState[mode.ordinal()] = enabled;
	}

	/**
	 * Get the state of a debugging mode
	 * 
	 * @param mode Mode for which to retrieve the state
	 */
	public static boolean getModeState(Mode mode) {
		return modeState[mode.ordinal()];
	}

	/**
	 * Get the debugging modes
	 */
	public static String getModeStates() {
		String modes = "Enabled debugging modes: \n";
		for (Mode mode : Mode.values()) {
			modes += mode.toString() + "=" + modeState[mode.ordinal()] + "\n";
		}
		return modes;
	}
}
