package com.github.cg.example.jsf.util;


import java.util.TreeMap;

public class PrimefacesThemes {
	
	private static TreeMap<String, String> THEMES;
	
	static {
		THEMES = new TreeMap<String, String>();
        THEMES.put("Afterdark", "afterdark");
        THEMES.put("Afternoon", "afternoon");
        THEMES.put("Afterwork", "afterwork");
        THEMES.put("Aristo", "aristo");
        THEMES.put("Black-Tie", "black-tie");
        THEMES.put("Blitzer", "blitzer");
        THEMES.put("Bluesky", "bluesky");
        THEMES.put("Bootstrap", "bootstrap");
        THEMES.put("Casablanca", "casablanca");
        THEMES.put("Cupertino", "cupertino");
        THEMES.put("Cruze", "cruze");
        THEMES.put("Dark-Hive", "dark-hive");
        THEMES.put("Delta", "delta");
        THEMES.put("Dot-Luv", "dot-luv");
        THEMES.put("Eggplant", "eggplant");
        THEMES.put("Excite-Bike", "excite-bike");
        THEMES.put("Flick", "flick");
        THEMES.put("Glass-X", "glass-x");
        THEMES.put("Home", "home");
        THEMES.put("Hot-Sneaks", "hot-sneaks");
        THEMES.put("Humanity", "humanity");
        THEMES.put("Le-Frog", "le-frog");
        THEMES.put("Midnight", "midnight");
        THEMES.put("Mint-Choc", "mint-choc");
        THEMES.put("Overcast", "overcast");
        THEMES.put("Pepper-Grinder", "pepper-grinder");
        THEMES.put("Redmond", "redmond");
        THEMES.put("Rocket", "rocket");
        THEMES.put("Sam", "sam");
        THEMES.put("Smoothness", "smoothness");
        THEMES.put("South-Street", "south-street");
        THEMES.put("Start", "start");
        THEMES.put("Sunny", "sunny");
        THEMES.put("Swanky-Purse", "swanky-purse");
        THEMES.put("Trontastic", "trontastic");
        THEMES.put("UI-Darkness", "ui-darkness");
        THEMES.put("UI-Lightness", "ui-lightness");
        THEMES.put("Vader", "vader");
	}

	public static TreeMap<String, String> getThemes() {
		return THEMES;
	}	
}
