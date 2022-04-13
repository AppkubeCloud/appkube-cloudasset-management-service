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
}
