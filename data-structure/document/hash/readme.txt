* java 中 hash(key) 的计算结果为 32 位的数值
* java 中 hashCode 相同的对象并不一定 equals 相等

* hash(key)中key的常见种类可能有
   1. 整数，浮点数，字符串，自定义对象
   2. 不同种类的 key，哈希值的生成方式不一样，但目标是一致的
      1. 尽量让每个 key 的哈希值是唯一的
      2. 尽量让 key 的所有信息参与运算

* 让 key 的哈希值跟数组的大小进行相关运算，生成一个索引值
   - hash_code(key) % table.length
   - 为了提高效率，可以使用 & 位运算取代 % 运算（前提：将数组的长度设计为 2 的幂）
      - hash_code(key) & (table.length - 1)
      ```
      假设数组长度为 n，且 n 的值为 2 的幂

      值 = 值二进制 = 科学计数法
      1   = 1         = 2^0
      2   = 10       = 2^1
      4   = 100     = 2^2
      8   = 1000   = 2^3
      16 = 10000 = 2^4

      值 = 值二进制 = 科学计数法
      0   = 0         = 2^0 - 1
      1   = 01       = 2^1 - 1
      3   = 011     = 2^2 - 1
      7   = 0111   = 2^3 - 1
      15 = 01111 = 2^4 - 1

      通过上面的形式可知，如果任意一个数 X 和 n-1 相与，结果必然为 [0 ~ (n-1)] 范围
      ```


* Integer计算hash
   - Integer.toBinaryString
   - 就是 int 值在二进制中的表现结果

* Float计算hash
   - Float 对象中 floatToIntBits(float的浮点数)
   - 就是 float 在二进制中的表现结果

* Long计算hash
   - value ^ (value >>> 32)
   - value 为 Long 类型的值
   - 即long的值为 value，将value无符号右移32的结果和原来的值进行异或

* Double计算hash
   - long bits = Double 中 doubleToLongBits(value)
   - value 为 Double 类型的值
   - 结果为：(int)(bits ^ (bits >>> 32))

* 字符串计算hash
   - "jack" 的哈希值可以表示为 j * n^3 + a * n^2 + c * n^1 + k * n^0
   - 等价于 [(j * n + a) * n + c] * n + k
   - 在，JDK 中，乘数 n 为 31，为什么为 31
      - 31 是一个奇素数，JVM 会将 31 * i 优化成 (i << 5) - i

* 关于 31 的探讨
   - 31 * i = (2^5 - 1) * i = i * 2^5 - i = (i << 5) - i