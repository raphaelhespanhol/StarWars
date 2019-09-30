package com.raphahes.starwars.model.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raphael Hespanhol
 * Common String utilities
 */
public class StringUtil {

	/** Regular Expression for getting only the numbers at the end of an Account ID. */
	public static final Pattern ACCOUNT_ID_PATTERN = Pattern.compile("^.*?([0-9]+)$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Verifies if a String value is null or empty
	 * 
	 * @param value => String value
	 * @return if the String value is null or empty
	 */
	public static boolean isEmpty(String value) {
		return (value == null || !(value.trim().length() > 0) || "null".equalsIgnoreCase(value.trim()));
	}
	
	/**
	 * Returns only the numbers at the end of the account ID by using a proper regular expression.
	 * @See CloudCoreFunctions.ACCOUNT_ID_PATTERN
	 * 
	 * Ex:
	 * 		- IBM123456 returns 123456
	 * 		- icd.123456 returns 123456
	 * 
	 * @param accountID -> Exs: IBM123456, icd.123456
	 * @return -> Only the ID numbers, example: 123456
	 */
	public static String getNumbersOnly(String accountID) {
		String numbers = "";
		if(accountID != null && accountID.trim().length() > 0) {
			Matcher matcher = ACCOUNT_ID_PATTERN.matcher(accountID.trim());
			if(matcher.matches()) {
				matcher.group();
				numbers = matcher.group(1);
			}
		}
		return numbers;
	}
}