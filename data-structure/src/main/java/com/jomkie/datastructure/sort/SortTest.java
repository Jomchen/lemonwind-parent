package com.jomkie.datastructure.sort;

/**
 * 算法法
 * 原地算法（In-place Algorithm）
 * 不依赖额外的资源或者依赖少数的额外资源，仅依靠输出来覆盖输入
 * 空间复杂度为O(1)都可以认为蝇原地算法
 * 非原地算法也称为 Not-in-place 或者 Out-of-place
 * 冒泡算法属于 In-place
 * @author jomkie
 *
 */
public class SortTest {
	
	public static void main(String[] args) {
		int[] source = new int[] { 3, 2, 7, 5, 8, 9, 0, 1, 4, 6 };
		printer(source);
		heapSort(source);
		printer(source);
	}

	/**
	 * 冒泡排序
	 * 最坏时间复杂度 O(n^2)
	 * 是稳定排序
	 * @param source
	 */
	public static void bubbleSort(int[] source) {
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
				if (cmp(source, begin, begin - 1) < 0) {
					swap(source, begin, begin - 1);
					sortedIndex = begin;
				}
			}
			end = sortedIndex;
		}
	}

	/**
	 * 选择排序
	 * 属于稳定排序
	 * 平均性能优于冒泡排序
	 * 最好，最坏，平均时间复杂度都为O(n^2)
	 * 空间复杂度为 O(1)
	 * @param source
	 */
	public static void selectionSort(int[] source) {
//		for (int i = source.length - 1; i > 0; i --) {
//			for (int j = i - 1; j >= 0; j --) {
//				if (source[i] < source[j]) {
//					int temporary = source[i];
//					source[i] = source[j];
//					source[j] = temporary;
//				}
//			}
//		}
		
		for (int end = source.length - 1; end > 0; end --) {
			int maxIndex = 0;
			for (int begin = 1; begin <= end; begin ++) {
				// 注意如果需要此算法是稳定排序，这里需要是 <=
				if (cmp(source, maxIndex, begin) <= 0) {
					maxIndex = begin;
				}
			}
			swap(source, maxIndex, end);
		}
		
		// 优化方法，选择最大值的过程用堆的方法选择，最终变幻为堆排序
	}
	
	/**
	 * 插入排序
	 * @param source
	 */
	public static void charu(int[] source) {
		 for (int end = 1; end < source.length; end ++) { 
			 int index = end;
			 while (index > 0 && cmp(source, index - 1, index) > 0) {
				 index --;
			 }
			 
			 int tmp = source[end];
			 
			 // 移动数据未实现
			 for (int i = end - 1; i >= index; i --) {
				 source[i + 1] = source[i];
			 }
			 
			 source[index] = tmp;
		 }
	}
	
	/**
	 * 堆排序
	 * 不是稳定的排序
	 * 时间复杂度为O(nlogn)
	 * @param source
	 */
	public static void heapSort(int[] source) {
		/* --------------------------
		 对序列进行原地建堆（heapify）
		 重复执行以下操作，直到堆的元素数量为 1
		    交换堆顶元素与尾元素
		    堆的元素数量减1
		    对0位置进行1次 siftDown 操作
		    
		 因为一开始就是堆了，所以交换堆顶元素与尾元素后
		 剩余元素对0位置元素 shiftDown 一次后结果仍然是堆
		---------------------------*/
		int heapSize = source.length;
		for (int i = (heapSize >> 1) - 1; i >= 0; i --) {
			siftDown(source, i, heapSize);
		}
		
		while (heapSize > 1) {
			// 交换堆顶元素和尾部元素
			swap(source, 0, -- heapSize);
			
			// 对0位置进行siftDown（恢复堆的性质）
			siftDown(source, 0, heapSize);
		}
	}
	private static void siftDown(int[] source, int index, int heapSize) {
		// 逻辑待实现
		int element = source[index];
		int half = heapSize >> 1;
		while (index < half) { // index 必须是非叶子节点
			// 默认是左边跟父节点比
			int childIndex = (index << 1) + 1;
			int child = source[childIndex];
			
			int rightIndex = childIndex + 1;
			// 右子节点比左子节点大
			if (rightIndex < heapSize 
					&& source[rightIndex] - child > 0) {
				child = source[childIndex = rightIndex];
			}
			
			// 大于等于子节点
			if ((element - child) >= 0) {
				break;
			}
			
			source[index] = child;
			index = childIndex;
		}
		
		source[index] = element;
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
	
	/**
	 * 比较两个索引位置元素比较结果
	 * @param source 源数组
	 * @param i1 索引1
	 * @param i2 索引2
	 * @return 
	 *   等于0表示source[i1] == source[i2]
     *   小于0表示source[i1] < source[i2]
     *   大于0表示source[i1] > source[i2]
	 */
	public static int cmp(int[] source, int i1, int i2) {
		return source[i1] - source[i2];
	}
	
	/**
	 * 交换索引位置的元素
	 * @param source 源数组
	 * @param i1 索引1
	 * @param i2 索引2
	 */
	public static void swap(int[] source, int i1, int i2) {
		int tmp = source[i1];
		source[i1] = source[i2];
		source[i2] = tmp;
	}
	
}
