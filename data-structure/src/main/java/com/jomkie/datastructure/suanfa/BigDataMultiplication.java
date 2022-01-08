package com.jomkie.datastructure.suanfa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

public class BigDataMultiplication {

	public static void main(String[] args) {
//		int dataA = 310;
//		int dataB = 220;
		
//		int dataA = 333;
//		int dataB = 29877;
		
		long dataA = 282892;
		long dataB = 29238;
		System.out.println(dataA * dataB);
		System.out.println(multiplicationOfBigData(String.valueOf(dataA), String.valueOf(dataB)));
	}
	
	/**
	 * 大数据乘法
	 * TODO 目前尚不支付负数，小数的计算，仅支付正整数乘法
	 * @author jomkie
	 * @since 2022-01-08 20:45:01
	 * @param data1 大数字1
	 * @param data2 大数字2
	 * @return
	 */
	public static String multiplicationOfBigData(String data1, String data2) {
		check(data1, data2);
		if (data2.length() > data1.length()) {
			String tem = data1;
			data1 = data2;
			data2 = tem;
		}

		int len1 = data1.length() - 1;
		int len2 = data2.length() - 1;
		Queue<String> queue = new LinkedList<>();
		
		for (int i1 = len1; i1 >= 0; i1--) {
			for (int i2 = len2; i2 >= 0; i2--) {
				int base1 = Integer.parseInt(String.valueOf(data1.charAt(i1)));
				int base2 = Integer.parseInt(String.valueOf(data2.charAt(i2)));

				int completZero1 = len1 - i1;
				int completZero2 = len2 - i2;
				int zeroCount = completZero1 + completZero2;
				int base = base2 * base1;

				StringBuilder dataStrBuilder = new StringBuilder();
				dataStrBuilder.append(base);
				while (zeroCount > 0) {
					dataStrBuilder.append("0");
					zeroCount--;
				}
				queue.offer(dataStrBuilder.toString());
			}
		}

		// 对分治的每一行进行累加
		String totalSum = "0";
		while (!queue.isEmpty()) {
			String dataStr = queue.poll();
			totalSum = sumOfBigData(dataStr, totalSum);
		}
		
		return totalSum;
	}
	
	
	/**
	 * 大数加法
	 * TODO 目前尚不支付负数，小数
	 * @author jomkie
	 * @since 2022-01-08 22:40:04
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static String sumOfBigData(String s1, String s2) {
		check(s1, s2);
		int len1 = s1.length();
		int len2 = s2.length();

		int i = 1;
		int before = 0;
		int maxLen = Math.max(len1, len2);
		StringBuilder totalSumBuilder = new StringBuilder();
		while (i <= maxLen) {
			int base1 = (len1 - i) < 0 ? 0 : Integer.parseInt(String.valueOf(s1.charAt(len1 - i)));
			int base2 = (len2 - i) < 0 ? 0 : Integer.parseInt(String.valueOf(s2.charAt(len2 - i)));
			int multipation = base1 + base2 + before;
			
			if (multipation <= 9) {
				before = 0;
				totalSumBuilder.append(multipation);
			} else {
				int[] sangYu = sangAndYu(multipation);	
				totalSumBuilder.append(sangYu[1]);
				before = sangYu[0];
			}
			
			i++;
		}
		
		if (before != 0) { totalSumBuilder.append(before); }
		String result =  totalSumBuilder.reverse().toString();
		return result;
	}
	
	/** 求商数和余数共同组成的数组 */
	private static int[] sangAndYu(int data) {
		int sange = data / 10;
		int yu = data - sange * 10;
		return new int[] {sange, yu};
	}

	/**
	 * 检查合法性
	 * @author jomkie
	 * @since 2022-01-08 22:21:37
	 * @param data1
	 * @param data2
	 */
	private static void check(String data1, String data2) {
		if (null == data1 || data1.length() == 0 || null == data2 || data2.length() == 0) {
			throw new IllegalArgumentException("parameters are invalid");
		}
		
		String pattern = "^\\d+$";
		boolean matche1 = data1.matches(pattern);
		boolean matche2 = data2.matches(pattern);
		if (!matche1 || !matche2) {
			throw new IllegalArgumentException("the expression of parameters are invalid");	
		}
	}
	
}
