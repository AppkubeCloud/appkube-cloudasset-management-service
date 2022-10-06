package com.synectiks.asset.util;

import java.util.Random;

public class RandomUtil {

	public static Integer getRandom() {
		Random r = new Random();
		int low = 0; // it is inclusive
		int high = 101; //it is exclusive
		Integer result = r.nextInt(high-low) + low;
		return result;
	}
	
	public static Integer getRandom(int low, int high) {
		Random r = new Random();
		high = high + 1; //it is exclusive
		Integer result = r.nextInt(high-low) + low;
		return result;
	}
	
	public static String randomAlphabeticString() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 5;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}
}
