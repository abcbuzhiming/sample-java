package com.youming.algorithm.sort;

/**
 * 世界上最漂亮的排序算法 https://mp.weixin.qq.com/s/bUNjl3glwaitO9RkErzfXw
 * 《算法导论》习题中的“完美排序”，由Howard、Fine等几个教授提出，之所以称为“完美排序”，是因为其代码实现，优雅、工整、漂亮
 * 除了代码好看，完美排序毛用没有，因为它是一个挺慢的算法。 由代码很容易看出来： (1)当只有1个元素时，完美排序的时间也是1；
 * (2)当有n个元素时，完美排序由一个常数计算，加上三次递归，每次递归数据量为(2/3)*n； 即，其时间复杂度递归式为： T(1) = 1; T(n) =
 * 3T(2/3n) + 1;
 *  使用《搞定所有时间复杂度计算》 https://mp.weixin.qq.com/s/yfzrFYn0Dogy0HkN5XAS0Q 
 *  中的递归式计算方法，最终得到，完美排序的时间复杂度是O(n^2.7)，比O(n^2)的排序都要慢
 */
public class WorldMostBeautifulSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {5,9,8,7,6,3};
		stooge_sort(arr,0,arr.length - 1);
		for(int i:arr) {
			System.out.print(i + ",");
		}
		
	}

	private static void stooge_sort(int arr[], int i, int j) {
		if (arr[i] > arr[j]) {		// 比较并置换
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		if (i + 1 >= j) // 是否结束
			return;

		int k = (j - i + 1) / 3; // 三等分
		stooge_sort(arr, i, j - k); // 前2/3半区
		stooge_sort(arr, i + k, j); // 后2/3半区
		stooge_sort(arr, i, j - k); // 前2/3半区
	}
	
}
