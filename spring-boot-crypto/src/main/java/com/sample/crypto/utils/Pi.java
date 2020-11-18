package com.sample.crypto.utils;

import java.math.BigDecimal;

/**
 * 计算PI的值
 */
public class Pi {
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * 计算PI的值
	 * 
	 * @param digits 小数位
	 * @return PI
	 */
	public static BigDecimal compute(int digits) {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
		return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	private static BigDecimal arctan(int inverseX, int scale) {
		BigDecimal result, number, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
		number = BigDecimal.ONE.divide(invX, scale, ROUNDING_MODE);
		result = number;
		int i = 1;
		do {
			number = number.divide(invX2, scale, ROUNDING_MODE);
			int denom = 2 * i + 1;
			term = number.divide(BigDecimal.valueOf(denom), scale, ROUNDING_MODE);
			if ((i % 2) != 0) {
				result = result.subtract(term);
			} else {
				result = result.add(term);
			}
			i++;
		} while (term.compareTo(BigDecimal.ZERO) != 0);
		return result;
	}
}
