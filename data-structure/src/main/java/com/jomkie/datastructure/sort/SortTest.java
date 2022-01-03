package com.jomkie.datastructure.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 算法法
 * 原地算法（In-place Algorithm）
 * 不依赖额外的资源或者依赖少数的额外资源，仅依靠输出来覆盖输入
 * 空间复杂度为O(1)都可以认为蝇原地算法
 * 非原地算法也称为 Not-in-place 或者 Out-of-place
 * 冒泡算法属于 In-place
 * @author jomkie
 *
 * 进度 P2
 */
public class SortTest {
	
	public static void main(String[] args) {
		// 0,1,2,3,4,5,6,7,8,9
		//int[] source = new int[] { 3, 2, 7, 5, 8, 9, 0, 1, 4, 6 };
		// 0,1,2,2,3,3,4,4,5,5
		//int[] source = new int[] { 3, 2, 3, 5, 2, 4, 0, 1, 4, 5 };
		// 6,9,11,20,35,332,440,520,1120
		int[] source = new int[] {1120, 332, 9, 440, 6, 35, 20, 11, 520};
		printer(source, "排序前");
		radixSort(source);
		printer(source, "排序后");
		
//		printer(source2);
//		heapSort(source2);
//		printer(source2);
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
//					 int tmp = source[begin];
//					 source[begin] = source[begin - 1];
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
			// 正常逻辑应该是默认赋值为 end，但如果后面的数据已经是完全有序的，
			// 则不会进入 if 判断去变更 sortedIndex；意味着不会变更 sortedIndex
			// 因此就没有排序的必要性，所以初始化值为 0
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
	 * 属于不稳定排序（此原因见案例）
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

		// 7 5 10 10 2 4 2
		//7 5 10 2 2 4 10 由此可知与10交换的2和另外一个2位置和以前的前后位置有异
		// 所以选择排序不稳定

		// 优化方法，选择最大值的过程用堆的方法选择，最终变幻为堆排序
	}
	
	/**
	 * 插入排序
	 * 最坏，平均时间复杂度 O(n^2)
	 * 最好时间复杂度 O(n)
	 * 空间复杂度 O(1)
	 * 属于稳定排序
	 * @param source
	 */
	public static void insertionSort(int[] source) {
//		 for (int begin = 1; begin < source.length; begin ++) { 
//			 int index = begin;
//			 // 如果这里的 com 大于等于0 就不稳定了
//			 while (index > 0 && cmp(source, index - 1, index) > 0) {
//				 swap(source, index - 1, index);
//				 index --;
//			 }
//		 }
		
		// 优化1
//		for (int begin = 1; begin < source.length; begin ++) {
//			int cur = begin;
//			int v = source[cur];
//			while (cur > 0 && (v - source[cur - 1]) < 0) {
//				source[cur] = source[cur - 1];
//				cur --;
//			}
//			source[cur] = v;
//		}
		
		// 优化2
		for (int begin = 1; begin < source.length; begin ++) {
			int insertIndex = search(source, begin);
			int tmp = source[begin];
			for (int i = begin; i > insertIndex; i --) {
				source[i] = source[i - 1];
			}
			source[insertIndex] = tmp;
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
	 * 归并排序
	 * 稳定排序
	 * @param source
	 */
	public static void mergeSort(int[] source) {
		int[] tmp = new int[source.length];
		mergeSortTool(source, 0, source.length, tmp);
	}
	private static void mergeSortTool(int[] source, int begin, int end, int[] tmp) {
		if ((end - begin) < 2) { return; }
		int mid = (end + begin) >> 1;
		mergeSortTool(source, begin, mid, tmp);
		mergeSortTool(source, mid, end, tmp);
		merge(source, begin, mid, end, tmp);
	}
	private static  void merge(int[] source, int begin, int mid, int end, int[] tmp) {
		// 其实 tmp 可以用源数组的一半长度作为代替
		// 假设 A和B两段数据，只需将A复制一份为C
		// 由 B 和 C 进行头部比较，依次放入 A和B形成的数组位置
		// 如果C先处理完则此合并结束，如果B先结束则把C依次放到剩余位置
		
		int firstIndex = begin;
		int secondIndex = mid;
		int tmpIndex = begin;
		
		// 对比两块数据排序
		while (true) {
			if (firstIndex < mid && secondIndex < end) {
				if (cmp(source, firstIndex, secondIndex) <= 0) {
					tmp[tmpIndex] = source[firstIndex];
					firstIndex ++;
					tmpIndex ++;
					continue;
				} else {
					tmp[tmpIndex] = source[secondIndex];
					secondIndex ++;
					tmpIndex ++;
					continue;
				}
			}
			
			while (firstIndex < mid) {
				tmp[tmpIndex] = source[firstIndex];
				firstIndex ++;
				tmpIndex ++;
			}
			while (secondIndex < end) {
				tmp[tmpIndex] = source[secondIndex];
				secondIndex ++;
				tmpIndex ++;
			}

			break;
		}
		
		// 将排序结果赋给源数组
		for (int i = begin; i < end; i ++) {
			source[i] = tmp[i];
		}
	}
	
	
	/**
	 * 希尔排序（升级版的插入排序）
	 * 最坏时间复杂度是O(n^2)
	 * 非稳定排序
	 * 
	 * 希尔排序把序列看作是一个矩阵，分成m列，逐列进行排序
	 * m 从某个整数逐渐减为1（是以 m/2^k，逐渐减小）
	 * 当 m 为1时，整个序列将完全有序
	 * 
	 * 比如，如果步长序列为{1,5,19,41,109,...}，就代表依次分为...109,41,19,5,1列
	 * 不同的步长序列，执行效率也不同
	 * 
	 * 希尔本人给出的步长序列为n/2^k，比如n为16时，步长序列为 {1,2,4,8}
	 * @param source
	 */
	public static void shellSort(int[] source) {
		List<Integer> stepList = shellStepSequence(source.length);
		for (Integer step : stepList) {
			shellSortTool(source, step);
		}
	}
	public static void shellSortTool(int[] source, int step) {
		for (int col = 0; col < step; col++) {
			for (int begin = col + step; begin < source.length; begin += step) {
				int cur = begin;
				while (cur > col && cmp(source, cur, cur - step) < 0) {
					swap(source, cur, cur - step);
					cur -= step;
				}
			}
		}
	}
	private static List<Integer> shellStepSequence(int length) {
		List<Integer> result = new ArrayList<>();
		int step = length;
		while ((step >>= 1) > 0) {
			result.add(step);
		}
		
		return result;
	}
	
	/**
	 * 快排序
	 * 不稳定排序
	 * O(nlogn)
	 * @param source
	 */
	public static void quickSort(int[] source) {
		quickSortTool(source, 0, source.length);
	}
	private static void quickSortTool(int[] source, int begin, int end) {
		if ((end - begin) < 2) { return; }
		int index = quickSortTool2(source, begin, end);
		quickSortTool(source, begin, index);
		quickSortTool(source, index + 1, end);
	}
	private static int quickSortTool2(int[] source, int begin, int end) {
		/* ---
		这一段代码为优化，需要优化则放开 ，因为如果每次都以 begin 的值作为轴点
		如果是升序排序，如果 begin 之后的都是比 begin 小的，那么需要很多次处理才能把 begin 的值放到最后
		所以需要随机选择一个值作为轴点值，把轴点值赋给 begin，再以 begin 的当前值作为轴点元素
		int random = (int)Math.random() * (end - begin);
		swap(source, begin, begin + random);
		--- */
		
		int tmp = source[begin];
		end--;
		
		while (begin < end) {
			// 以下的两个和零的判断，之所以没有用等于 0作减减或加加
			// 是因为这样在遇到相同值的时候可以均匀的分为两部分，可以尽量避免最坏时间复杂度
			
			while (begin < end) {
				if (cmp(tmp, source[end]) < 0) {
					end --;
				} else {
					source[begin++] = source[end];
					break;
				}
			}

			while (begin < end) {
				if (cmp(tmp, source[begin]) > 0) {
					begin++;
				} else {
					source[end--] = source[begin];
					break;
				}
			}
		}
		
		source[begin] = tmp;
		return begin;
	}
	
	/**
	 * 计数排序
	 * 不稳定排序
	 * 空间换时间，在某些情况下，平均时间复杂度可以比 O(nlogn)更低
	 */
	public static void countingSort(int [] source) {
		// 获取数组中最大的值
		int max = source[0];
		for (int i = 1; i < source.length; i++) {
			if (source[i] > max) {
				max = source[i];
			}
		}
		
		// 通过最大的值建立那个值长度的数组
		// 再遍历数组，将源数组中元素的值作为新数组索引，并记录源数组中元素出现的次数
		int[] counts = new int[max + 1];
		for (int i = 0; i < source.length; i++) {
			counts[source[i]]++;
		}
		
		// 根据整数出现的次数进行排序
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 0) { continue; }
			
			while (counts[i]-- > 0) {
				source[index++] = i;
			}
		}
	}
	/**
	 * 计数排序2
	 * 第一种算法不能计算负数，而且极其浪费空间
	 * @param source
	 */
	public static void countingSort2(int[] source) {
		int min = source[0];
		int max = source[0];
		for (int i = 1; i < source.length; i++) {
			// 之所以这里没有用 else if 是因为可能最大和最小都是同一个值
			if (source[i] < min) {
				min = source[i];
			}
			if (source[i] > max) {
				max = source[i];
			}
		}

		// 先统计元素出现的次数，再累加次数
		int[] counts = new int[max - min + 1];
		for (int i = 0; i < source.length; i++) {
			counts[source[i] - min]++;
		}
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		
		// 从后面遍历是为了排序的稳定性
		int[] temporary = Arrays.copyOf(source, source.length);
		for (int i = temporary.length - 1; i >=0; i--) {
			source[--counts[temporary[i] - min]] = temporary[i];
		}
	}



	/**
	 * 桶排序
	 * 空间换时间，在某些情况下，平均时间复杂度可以比 O(nlogn)更低
	 * @param source
	 */
	public static void bucketSort(int[] source) {
		// 通过链表或数组的元素充当桶（桶号越小表示这个桶里的数据一定小于桶号大的）
		// 对源数据的每个元素通过 自定义算法 算出应该放在哪个桶
		// 对每个桶的元素在桶内排序
		// 依次遍历每个桶，取出桶的元素放到源数组中，完成排序
	}

	/**
	 * 基数排序
	 * 非常适合整数据排序，尤其是非负整数
	 * 依次对个数，十位，百位，....进行排序（从低位到高位），当某位如果不存在时，则以0替代
	 * 最终相当于：针对个位数执行一次计数排序，针对十位数执行一次计数排序，针对百位...
	 * 空间换时间，在某些情况下，平均时间复杂度可以比 O(nlogn)更低
	 * @param source
	 */
	public static void radixSort(int[] source) {
		int max = source[0];
		for (int i = 1; i < source.length; i++) {
			if (cmp(source[i], max) > 0) {
				max = source[i];
			}
		}

		// 相当于最大值有几位，就执行多少次计数排序

		// 个位数：5932 / 1 % 10 = 2
		// 十位数：5932 / 10 % 10 = 3
		// 百位数：5932 / 100 % 10 = 9
		// 千位数：5932 / 1000 % 10 = 5
		for (int divider = 1; divider <= max; divider *= 10) {
			radixSortTool(source, divider);
		}
	}
	private static void radixSortTool(int[] source, int divider) {
		// TODO 这里还可以作优化，因为基数最小值也许不是0，这样浪费了时间和空间
		int[] counting = new int[10];
		for (int i = source.length - 1; i >= 0; i--) {
			counting[source[i] / divider % 10]++;
		}
		for (int i = 1; i < counting.length; i++) {
			counting[i] += counting[i - 1];
		}

		int[] newArray = new int[source.length];
		for (int i = source.length - 1; i >= 0; i--) {
			int index = source[i] / divider % 10;
			newArray[--counting[index]] = source[i];
		}
		for (int i = 0; i < newArray.length; i++) {
			source[i] = newArray[i];
		}
	}

	/**
	 * 基数排序，另一种思路
	 * @param source
	 */
	public static void radixSort2(int[] source) {
		int max = source[0];
		for (int i = 0; i < source.length; i++) {
			if (cmp(source[i], max) > 0) {
				max = source[i];
			}
		}

		int[][] buckets = new int[10][source.length];
		int[] bucketSizes = new int[10];

		for (int divider = 1; divider <= max; divider *= 10) {
			for (int i = 0; i < source.length; i++) {
				int base = source[i] / divider % 10;
				buckets[base][bucketSizes[base]++] = source[i];
			}
			int index = 0;
			for (int i = 0; i < buckets.length; i++) {
				for (int j = 0; j < bucketSizes[i]; j++) {
					source[index++] = buckets[i][j];
				}
				bucketSizes[i] = 0;
			}
		}
	}

	/**
	 * 打印器
	 * @param source
	 */
	public static void printer(int[] source, String msg) {
		System.out.println(msg + "**华丽的分割线--------------------------------------------------------------");
		if (null == source || source.length <= 0) { return; }
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < source.length; i ++) {
			sb.append(source[i]).append("_");
		}
		System.out.print(sb);
		System.out.println();
	}
	
	/**
	 * 比较两个索引位置元素比较结果
	 *   等于0表示source[i1] == source[i2]
	 *   小于0表示source[i1] < source[i2]
	 *   大于0表示source[i1] > source[i2]
	 * @param source 源数组
	 * @param i1 索引1
	 * @param i2 索引2
	 * @return 
	 */
	public static int cmp(int[] source, int i1, int i2) {
		return source[i1] - source[i2];
	}
	
	/**
	 * 比较两个值的大小
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static int cmp(int i1, int i2) {
		return i1 - i2;
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
	
	/**
	 * 二分查找法
	 * 以后编程建议使用左闭右开的原则
	 * 二分搜索的目标如果在数组中有重复值，则返回的哪个是不确定的
	 * @param source
	 * @param v
	 * @return
	 */
	public static int binarySearch(int[] source, int v) {
		if (null == source || source.length == 1) { return -1; }
		int begin = 0;
		int end = source.length;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (v < source[mid]) {
				end = mid;
			} else if (v > source[mid]) {
				begin = mid + 1;
			} else {
				return mid;
			}
		}
			
		return -1;
	}
	
	/**
	 * 在一个升序数组中，从左到右查找第一个大于目标值的索引
	 * @param source 源数组
	 * @param index 目标值的索引
	 * @return
	 */
	public static int search(int[] source, int index) {
		if (null == source || source.length == 0) { return -1; }
		
		int begin = 0;
		int end = index;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (source[index] < source[mid]) {
				end = mid;
			} else {
				begin = mid + 1;
			}
		}
		return begin;
	}
	
}
