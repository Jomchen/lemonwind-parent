package com.lemonwind.datastructure.suanfa;

import java.util.*;

/**
 * 前缀表达式（波兰表达式）
 * 中缀表达式
 * 后缀表达式（逆波兰表达式）
 *
 * 中转前：把与符号相关的表达式加括号，再把操作符放到这部分括号之前，再去掉括号
 *    原表达式：a+b*c-(d+e) 加括号=> ((a+(b*c))-(d+e)) 移动操作符=> -(+(a*(bc))+(de)) 去掉括号=> -+a*bc+de
 * 中转后：把与符号相关的表达式加括号，再把符号放到这部分括号之后
 *    原表达式：a+b*c-(d+e) 加括号=> ((a+(b*c))-(d+e)) 移动操作符=> ((a(bc)*)+(de)+)- 去掉括号=> abc*+de+-
 * 前转中：从后往前扫描，遇到数字压栈，遇到运算符将操作符被栈顶两个数字夹住并用括号包含成一个整体，这个整体入栈当做数字
 *    重复操作直到栈中只有一个数字（一个整体）
 * 后转中：从前往后扫描，.....
 *
 *    中转后例子：
 *       a+b => ab+
 *       a+(b-c) => abc-+
 *       a+(b-c)*d => abc-d*+
 *       a+d*(b-c) => adbc-*+
 *       a=1+3 => a13+=
 *
 * 表达式树
 *    如果表达式的操作数作为叶子节点，去处符作为父节点（假设只是四则运算）
 *    这些节点刚好可以组成一棵二叉树
 *    比如表达式：A/B + C*D - E
 *                      -
 *                    /   \
 *                  +      E
 *                /  \
 *              /     *
 *            /  \   /  \
 *           A   B  C   D
 *     前序遍历：- + / A B * C D E
 *        刚好就是前缀表达式（波兰表达式）
 *     中序遍历：A / B + C * D - E
 *        刚好就是中缀表达式
 *     后序遍历：A B / C D * + E -
 *        刚好就是后缀表达式（逆波兰表达式）
 *
 *
 */
public class PrefixAndSuffix {

    static final String LEFT_BRACKET = "(";
    static final String RIGHT_BRACKET = ")";
    static final String ADD_CHAR = "+";
    static final String SUB_CHAR = "-";
    static final String MUL_CHAR = "*";
    static final String DIV_CHAR = "/";
    static final String MOU_CHAR = "%";

    public static void main(String[] args) {
        // ((2+5)+3
//        String expression = "(2*5-3)";
        // (((2-3)*4)+(8/4-2))
        String expression = "(((2-3)*4)+(8/4-2))";
       // (2*5-3)
//        String suffixExpression =  "((2+5)+3";
        // (3+4)*5-6 = 29
//        String expression = "3 4 + 5 * 6 -";

        // 4*5-8+60+8/2 = 76
//        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        // (1+((2+3)*4))-5) => (1+((23)+*4))-5) => 123+4*+5-
        // 后缀为 123+4*+5-
//        String expression = "1+((2+3)*4)-5";
        List<String> list = strToList(expression);
        System.out.println("原式子");
        System.out.println(list);
        System.out.println("中缀表达式");
        list.forEach(System.out::print);
        System.out.println();
        System.out.println("后缀表达式");
        List<String> suffixExprTemp = parseSuffixExpression(list);
        StringBuilder stringBuilder = new StringBuilder();
        suffixExprTemp.forEach(stringBuilder::append);
        System.out.println(stringBuilder);
        System.out.println("逆波兰计算器结果");
        System.out.println(suffixExpression(suffixExprTemp));

        /*String data = suffixExpression(suffixExpression2, " ");
        System.out.println("计算的结果为：" + data);*/
    }

    /**
     * 逆波兰计算器
     * 输入一个后缀表达式，使用栈计算结果
     * 支持小括号和整数
     * @param expression 表达式
     */
    public static String suffixExpression(List<String> expression) {
        // 123+4*+5-
        Stack<String> stack = new Stack<>();
        for (String s : expression) {
            if (s.matches("^\\d+$")) {
                stack.add(s);
            } else {
                int a = Integer.parseInt(stack.pop());
                int b = Integer.parseInt(stack.pop());
                int data = comparator(s, a, b);
                stack.push(String.valueOf(data));
            }
            System.out.println("栈空间为：");
            stack.stream().forEach(i -> System.out.print("->"+i));
            System.out.println();
        }

        return stack.pop();
    }

    /**
     * 中缀转后缀表达式
     *
     * 1. 初始化两个栈：运算符栈s1和储存中间结果的栈s2
     * 2. 从左至右扫描中缀表达式
     * 3. 遇到操作数时，压入s2
     * 4. 遇到运算符时，比较其与s1栈顶运算符的优先级
     *    1. 如果 s1 为空，或栈顶运算符为左括号"("，则直接将此运算符入栈 s1
     *    2. 否则，若优先级比栈顶运算符的高，也将运算符压入s1
     *       注意：运算符和括号不能比较，所以入s1
     *    3. 否则，将 s1 栈顶的运算符弹出并压入s2中，再次转到 4.1. 与s1中新的栈顶运算符比较
     * 5. 遇到括号时
     *       1. 如果是左括号"("，则直接压入s1
     *       2. 如果是右括号")"，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将 这一对括号丢弃
     * 6. 重复步骤 2至5，直到表达式的最右边
     * 7. 将s1中剩余的运算符依次弹出并压入s2
     * 8. 依次弹出s2中的元素并输出，输出结果的逆序 即为 中缀表达式对应的后缀表达式
     *
     * @param ls 中缀表达式的内容集合
     */
    public static List<String> parseSuffixExpression(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<>();  // 符号栈

        //说明：因为 s2这个栈，在整个转换过程中，没有pop操作，后面还需要逆序输出
        //所以，把 s2 这个栈换成 List<String> 即可
        //Stack<String> s2 = new Stack<>();  // 存储中间结果的栈
        List<String> s2 = new ArrayList<>();  //存储中间结果的list

        //遍历 ls
        for(String item : ls) {
            //如果是一个栈，就加入到s2
            if(item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals(LEFT_BRACKET)) {
                s1.push(item);
            } else if (item.equals(RIGHT_BRACKET)) {
                // 如果是右括号，则依次弹出s1栈顶的运算符，并压入 s2,知道遇到左括号为止，此时将这一对括号丢弃
                while(!s1.peek().equals(LEFT_BRACKET)) {
                    s2.add(s1.pop());
                }
                s1.pop();  // 将左括号 弹出，消除小括号
            } else {
                // 当 item 的优先级小于或等于栈顶运算符，将s1栈顶的运算符弹出并压入s2中，再次转到4.1与s1中新的栈顶运算符相比较
                //问题：缺少比较优先级高低的方法
                while(s1.size() != 0 && opetatorPrim(s1.peek()) >= opetatorPrim(item)) {
                    s2.add(s1.pop());
                }
                //还需要将 item 压入 栈中
                s1.push(item);
            }
        }

        //将s1中剩余的运算符依次弹出加入s2
        while(s1.size()!=0) {
            s2.add(s1.pop());
        }

        return s2;  //因为存放到list中，因此，按顺序输出就是对应的后缀表达式对应的 list
    }

    /**
     * 获取操作符的优先级
     * @param operator 操作符
     * @return 优先级
     */
    public static int opetatorPrim(String operator) {
        switch (operator) {
            case ADD_CHAR:
                return 1;
            case SUB_CHAR:
                return 1;
            case MUL_CHAR:
                return 2;
            case DIV_CHAR:
                return 2;
            case MOU_CHAR:
                return 2;
            case LEFT_BRACKET:
                return -1;
            default:
                throw new RuntimeException(
                        "操作数不合法->" +
                                operator +
                                "<-"
                );
        }
    }

    /**
     * 将中缀转成 List
     * @param expression 中缀表达式
     */
    public static List<String> strToList(String expression) {
        // 1+((2+3)*4)-5 => 123+4*+5-
        List<String> ls = new LinkedList<>();
        int i = 0;
        String str;
        char c;
        do {
            if ((c = expression.charAt(i)) < 48 || (expression.charAt(i) > 57)) {
                /* 如果 c 是个非数字，需要加入到 ls 中
                 * 0-9 的 ascill 码在 48-57 之间
                 */
                ls.add(String.valueOf(c));
                i ++;
            } else {
                str = "";
                while (i < expression.length()
                        && (expression.charAt(i) >= 48)
                        && (expression.charAt(i) <= 57)) {
                    str += c;
                    i ++;
                }
                ls.add(str);
            }
        } while (i < expression.length());

        return ls;
    }

    /**
     * 计算
     * 注意由于是从栈取出，所以 a 和 b 的运算注意顺序是相反的
     * @param operator 操作符
     * @param a 计算值a
     * @param b 计算值b
     * @return 运算结果
     */
    public static int comparator(String operator, int a, int b) {
        switch (operator) {
            case ADD_CHAR:
                return b+a;
            case SUB_CHAR:
                // 注意 b 和 a 的顺序
                return b-a;
            case MUL_CHAR:
                return b*a;
            case DIV_CHAR:
                // 注意 b 和 a 的顺序
                return b/a;
            case MOU_CHAR:
                // 注意 b 和 a 的顺序
                return b%a;
            default:
                throw new RuntimeException("操作符异常");
        }
    }


    public static void printLog(Collection c) {
        if (c != null && !c.isEmpty()) {
            c.stream().forEach(i -> {
                System.out.print(i + " ");
            });
            System.out.println();
        }
    }
}
