package com.youming.algorithm.question;

import java.util.IllegalFormatFlagsException;

import javax.xml.transform.Templates;

/**
 * 两个超大的整数如何相加的问题 出自 -
 * 漫画：如何实现大整数相加(https://mp.weixin.qq.com/s/GXclPfm_L2kIGAjgLSPX5g)
 */
public class BigIntegerAdd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String bigNumberA = "892354723456723186745812643866235812946120523894561237456";
		String bigNumberB = "23895467819356123894671234623895893645430281456126489123486";
		String result1 = bigNumberSum(bigNumberA, bigNumberB);
		String result2 = bigNumberSumPlus(bigNumberA, bigNumberB);
		System.out.println(result1);
		System.out.println(result2);
	}

	/**
	 * 方法，按位相加，小学加法的方式,时间复杂度O(n)
	 * 
	 * @param bigNumberA 第一个整数
	 * @param bigNumberB 第二个整数
	 * 
	 */
	private static String bigNumberSum(String bigNumberA, String bigNumberB) {
		// 1.把两个大整数用数组逆序存储，数组长度等于较大整数位数+1
		int maxLength = bigNumberA.length() > bigNumberB.length() ? bigNumberA.length() : bigNumberB.length();
		int[] arrayA = new int[maxLength + 1];
		for (int i = 0; i < bigNumberA.length(); i++) { // 反序数组
			arrayA[i] = bigNumberA.charAt(bigNumberA.length() - 1 - i) - '0'; // 减去'0'的意思就是转换为int
		}

		int[] arrayB = new int[maxLength + 1];
		for (int i = 0; i < bigNumberB.length(); i++) { // 反序数组
			arrayB[i] = bigNumberB.charAt(bigNumberB.length() - 1 - i) - '0';
		}
		// 2.构建result数组，数组长度等于较大整数位数+1
		int[] result = new int[maxLength + 1];
		// 3.遍历数组，按位相加
		for (int i = 0; i < result.length; i++) {
			int temp = result[i];
			temp += arrayA[i];
			temp += arrayB[i];
			// 判断是否进位
			if (temp >= 10) {
				temp = temp - 10;
				result[i + 1] = 1;
			}
			result[i] = temp;
		}

		// 4.把result数组再次逆序并转成String
		StringBuilder sb = new StringBuilder();
		// 是否找到大整数的最高有效位
		boolean findFirst = false;
		for (int i = result.length - 1; i >= 0; i--) {
			if (!findFirst) {
				if (result[i] == 0) {
					continue;
				}
				findFirst = true;
			}
			sb.append(result[i]);
		}
		return sb.toString();
	}

	/**
	 * 加强版方法，取9位一组(int最大10位，防止溢出)，按位相加
	 * 
	 * @param bigNumberA 第一个整数
	 * @param bigNumberB 第二个整数
	 */
	private static String bigNumberSumPlus(String bigNumberA, String bigNumberB) {
		int size = 9;
		int bit = 0;
		StringBuilder sb = new StringBuilder();
		// 1.把两个大整数用数组逆序存储，数组长度等于较大整数位数+1
		int maxLength = bigNumberA.length() > bigNumberB.length() ? bigNumberA.length() : bigNumberB.length();
		int[] arrayA = new int[maxLength / size + 1];
		for (int i = 0; i < bigNumberA.length(); i++) { // 反序数组
			// arrayA[i] = bigNumberA.charAt(bigNumberA.length() - 1 - i) - '0'; //
			// 减去'0'的意思就是转换为int
			sb.append(bigNumberA.charAt(bigNumberA.length() - 1 - i));
			bit += 1;
			if (bit >= size || i == bigNumberA.length() - 1) { // bit达到最大位数，或大数被读完，都需要重置
				arrayA[i / size] = Integer.parseInt(sb.reverse().toString());
				//System.out.println("arrayA:" + i + "|" + arrayA[i / size]);
				sb.delete(0, sb.length()); // 清空
				bit = 0;
			}
		}

		bit = 0; // 重置
		sb.delete(0, sb.length()); // 清空

		int[] arrayB = new int[maxLength / size + 1];
		for (int i = 0; i < bigNumberB.length(); i++) { // 反序数组
			// arrayB[i] = bigNumberB.charAt(bigNumberB.length() - 1 - i) - '0';
			sb.append(bigNumberB.charAt(bigNumberB.length() - 1 - i));
			bit += 1;
			if (bit >= size || i == bigNumberB.length() - 1) { // bit达到最大位数，或大数被读完，都需要重置
				arrayB[i / size] = Integer.parseInt(sb.reverse().toString());
				//System.out.println("arrayB:" + i + "|" + arrayB[i / size]);
				sb.delete(0, sb.length()); // 清空
				bit = 0;
			}
		}
		// 2.构建result数组，数组长度等于较大整数位数+1
		int[] result = new int[maxLength / 9 + 1];
		// 3.遍历数组，按位相加
		for (int i = 0; i < result.length; i++) {
			int temp = result[i];
			temp += arrayA[i];
			temp += arrayB[i];
			// 判断是否进位
			if (temp >= 1000000000) {
				temp = temp - 1000000000;
				result[i + 1] = 1;
			}
			result[i] = temp;
		}

		// 4.把result数组再次逆序并转成String
		sb.delete(0, sb.length()); // 清空
		// 是否找到大整数的最高有效位
		boolean findFirst = false;
		for (int i = result.length - 1; i >= 0; i--) {
			if (!findFirst) {
				if (result[i] == 0) {
					continue;
				}
				findFirst = true;
			}
			
			// 不足的位数除最高位需要补0
			if (i != result.length - 1) {
				int needAddZero = Math.abs(String.valueOf(result[i]).length() - size);
				for (int j = 0; j < needAddZero; j++) {
					sb.append("0");
				}
			}

			sb.append(result[i]);
		}
		return sb.toString();
	}

}
