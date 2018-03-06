package com.yyd.semantic.common;

import java.util.Random;

public class CommonUtils {

	public static int randomInt(int bound) {
		Random rd = new Random();
		return rd.nextInt(bound);
	}

	public static Number strToNumber(String numberStr) {
		return new Number(numberStr);
	}
}
