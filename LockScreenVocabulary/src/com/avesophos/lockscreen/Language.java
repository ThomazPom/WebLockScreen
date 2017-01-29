package com.avesophos.lockscreen;
import java.util.*;

public class Language
{

	// Language constants
	public static final int TOTAL_LANGUAGES = 3;
	
	public static final int BASELANG_DE = 2;
	public static final int BASELANG_EN = 1;
	public static final int BASELANG_ES = 4;
	public static final int BASELANG_FR = 3;

	public static final int LANG_EN = 0;
	public static final int LANG_FR = 1;
	public static final int LANG_RU = 2;
	
	public static final String DISPLAYNAME_RU = "Росский";
	public static final String DISPLAYNAME_FR = "Français";
	public static final String DISPLAYNAME_EN = "English";
	
	public static final Locale LOCALE_RU = new Locale("ru", "RU");
	public static final Locale LOCALE_FR = new Locale("fr", "FR");
	public static final Locale LOCALE_EN = new Locale("en", "EN");
  	
	public static final String WORDLIST_RU = "ru.tsv";
	public static final String WORDLIST_FR = "fr.tsv";
	public static final String WORDLIST_EN = "en.tsv";
	
	public static final int WORDCOUNT_RU = 3000;
	public static final int WORDCOUNT_FR = 4900;
	public static final int WORDCOUNT_EN = 9200;
	
	public static final int[] getDefaultScores() {
		int[] scores = {50, 50, 50};
		return scores;
	}
	
	public static final List getDisplayLanguages() {
		ArrayList<String> array = new ArrayList<String>(); 
		array.add(DISPLAYNAME_EN); 
		array.add(DISPLAYNAME_FR); 
		array.add(DISPLAYNAME_RU);
		return array;
	}
	
	public static final String getDisplayName(int language) {
		switch (language) {
			case LANG_RU:
				return DISPLAYNAME_RU;
			case LANG_EN:
				return DISPLAYNAME_EN;
			case LANG_FR:
				return DISPLAYNAME_FR;
		}
		return DISPLAYNAME_FR;
	}
	
	public static final Locale getLocale(int language) {
		switch (language) {
			case LANG_RU:
				return LOCALE_RU;
			case LANG_EN:
				return LOCALE_EN;
			case LANG_FR:
				return LOCALE_FR;
		}
		return LOCALE_FR;
	}
	
	public static final int getWordCount(int language) {
		switch (language) {
			case LANG_RU:
				return WORDCOUNT_RU;
			case LANG_EN:
				return WORDCOUNT_EN;
			case LANG_FR:
				return WORDCOUNT_FR;
		}
		return WORDCOUNT_FR;
	}

	public static final String getWordList(int language) {
		switch (language) {
			case LANG_RU:
				return WORDLIST_RU;
			case LANG_EN:
				return WORDLIST_EN;
			case LANG_FR:
				return WORDLIST_FR;
		}
		return WORDLIST_FR;
	}
}
