package com.coreclasses.core;

/**
 *
 */
public class OSChecker {
	/**
	 *
	 */
	private static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * @return
	 */
	public static boolean isMac() {
		return OS.contains("mac");
	}

	/**
	 * @return
	 */
	public static boolean isUnix() {
		return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
	}

	/**
	 * @return
	 */
	public static boolean isWindows() {
		return OS.contains("win");
	}
}
