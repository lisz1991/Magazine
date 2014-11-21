package com.xiaofeng.utils;

import java.util.Random;

/**
 * @Use: 随机数获取工具类
 * @Date:2014-2-25
 * @Time:下午3:40:15
 */
public class RandomUtils {

	public static final String NUM_LET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String BLOCK_LET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWER_LET = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUM = "0123456789";

	private RandomUtils() {

	}

	public static int nextInt() {
		return nextInt(new Random());
	}

	public static int nextInt(int max) {
		return nextInt(0, max);
	}

	public static int nextInt(int min, int max) {
		if (min > max) {
			return 0;
		}
		if (min == max) {
			return min;
		}
		return min + new Random().nextInt(max - min);
	}

	public static int nextInt(Random random) {
		return random.nextInt();
	}

	public static long nextLong() {
		return nextLong(new Random());
	}

	public static long nextLong(Random random) {
		return random.nextLong();
	}

	public static boolean nextBoolean() {
		return nextBoolean(new Random());
	}

	public static boolean nextBoolean(Random random) {
		return random.nextBoolean();
	}

	public static float nextFloat() {
		return nextFloat(new Random());
	}

	public static float nextFloat(Random random) {
		return random.nextFloat();
	}

	public static double nextDouble() {
		return nextDouble(new Random());
	}

	public static double nextDouble(Random random) {
		return random.nextDouble();
	}
}
