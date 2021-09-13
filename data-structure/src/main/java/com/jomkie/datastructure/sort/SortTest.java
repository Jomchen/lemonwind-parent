package com.jomkie.datastructure.sort;

public class SortTest {
	
	public static void main(String[] args) {
		int[] source = new int[] { 3, 2, 7, 5, 8, 9, 0, 1, 4, 6 };
		printer(source);
		maopao(source);
		printer(source);
	}

	/**
	 * 冒泡排序
	 * @param source
	 */
	public static void maopao(int[] source) {
//		for (int end = source.length - 1; end > 0; end --) { 
//			 for (int begin = 1; begin <= end; begin ++) { 
//				 if (source[begin] < source[begin - 1]) { 
//					 int tmp = source[begin]; source[begin] = source[begin - 1]; 
//					 source[begin - 1] = tmp; 
//				 }
//			 } 
//		}
		 
		
		/* 上一个的代码如果数组已经是有序的，则仍然会进行两次for循环浪费性能，所以只要判断一次遍历是有序的则没有必要再遍历下去 */
//		for (int end = source.length - 1; end > 0; end --) {
//			boolean sorted = true;
//			for (int begin = 1; begin <= end; begin ++) {
//				if (source[begin] < source[begin - 1]) {
//					int tmp = source[begin];
//					source[begin] = source[begin - 1];
//					source[begin - 1] = tmp;
//					sorted = false;
//				}
//			}
//			
//			if (sorted) { break; }
//		}
		
		/* 上一个的代码如果在数据量很多的情况，有序的可能性很小。所以运行效率不一定比没有优化的好 */
		/* 再经过优化，如果序列尾部已经局部有序，可以记录最后一次交换的位置，减少比较次数 */
		for (int end = source.length - 1; end > 0; end --) {
			int sortedIndex = 0;
			for (int begin = 1; begin <= end; begin ++) {
				if (source[begin] < source[begin - 1]) {
					int tmp = source[begin];
					source[begin] = source[begin - 1];
					source[begin - 1] = tmp;
					
					sortedIndex = begin;
				}
			}
			end = sortedIndex;
		}
	}

	/**
	 * 选择排序
	 * @param source
	 */
	public static void xuanze(int[] source) {
		for (int i = source.length - 1; i > 0; i --) {
			for (int j = i - 1; j >= 0; j --) {
				if (source[i] < source[j]) {
					int temporary = source[i];
					source[i] = source[j];
					source[j] = temporary;
				}
			}
		}
	}
	
	/**
	 * 插入排序
	 * @param source
	 */
	public static void charu(int[] source) {
		 for (int i = 1; i < source.length; i ++) { 
			 int lastData = source[i]; 
			 int current = i; 
			 while (current > 0 && source[current] <= source[current -1]) { 
				 current --;
			 }
		  
			 for (int j = i; j >= current; j --) { 
				 source[j + 1] = source[j]; 
			 }
			 
			 source[current] = lastData; 
		 }
	}
	
	/**
	 * 堆排序
	 * @param source
	 */
	public static void dui(int[] source) {
		
	}
	
	/**
	 * 分治排序
	 * @param source
	 */
	public static void guibing(int[] source) {
		
	}
	
	/**
	 * 希尔排序
	 * @param source
	 */
	public static void xier(int[] source) {
		
	}
	
	/**
	 * 快排序
	 * @param source
	 */
	public static void kuai(int[] source) {
		
	}
	
	/**
	 * 桶排序
	 * @param source
	 */
	public static void tong(int[] source) {
		
	}
	
	/**
	 * 基数排序
	 * @param source
	 */
	public static void jishu(int[] source) {
		
	}
	
	/**
	 * 打印器
	 * @param source
	 */
	public static void printer(int[] source) {
		if (null == source || source.length <= 0) { return; }
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < source.length; i ++) {
			sb.append(source[i]).append("_");
		}
		System.out.print(sb);
		System.out.println("华丽的分割线--------------------------------------------------------------");
	}
}
