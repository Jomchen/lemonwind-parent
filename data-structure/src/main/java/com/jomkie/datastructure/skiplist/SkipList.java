package com.jomkie.datastructure.skiplist;

/**
 * 跳表
 * 在有序链表的基础上增加了“跳跃跃”的功能
 * 设计的初衷是为了取代平衡树（比如红黑树）
 * Redis 中的 SortedSet，LevelDB 中的 MemTable 都用到了跳表
 * Redis, LevelDB 都是著名的 Key-Value 数据库
 *
 * 对比平衡树
 *     跳表的实现和维护会更加简单
 *     跳表的搜索，删除，添加的平均时间复杂度是 O(logn)
 */
public class SkipList {
}
