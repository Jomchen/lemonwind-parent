package com.datastructure.common.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 符号合法性
 * @author Jomkie
 * @since 2021-07-08 23:13:57
 * TODO 还应该看 括号的分数，前缀表达式，中缀表达式，逆波兰表达式（后缀表达式），基本计算器
 */
public class StackApplication {

    public static void main(String[] args) {
        test();
    }

    /**
     * 判断字符串中的版本号是否有效
     * @author Jomkie
     * @since 2021-07-08 22:20:54
     */
    public static void test() {
        /* ---------------
            判断有效的括号
            (): true
            ()[]{}: true
            (]: false
            ([)]: false
            {[]}: true
            遇到左括号直接入栈，遇到右括号则从栈中取元素进行比较是否匹配
         --------------- */
        String s1 = "()";
        String s2 = "()[]{}";
        String s3 = "(]";
        String s4 = "([)]";
        String s5 = "{[]}";

        System.out.println(test02(s1));
        System.out.println(test02(s2));
        System.out.println(test02(s3));
        System.out.println(test02(s4));
        System.out.println(test02(s5));
    }

    /**
     * 检验字符串符号合法性
     * @author Jomkie
     * @since 2021-07-08 22:26:0
     * @param str 字符串
     * @return boolean
     */
    public boolean test01(String str) {
        while (str.contains("{}")
                || str.contains("[]")
                || str.contains("()")) {
            str = str.replace("{}", "");
            str = str.replace("[]]", "");
            str = str.replace("()", "");
        }

        return str.isEmpty();
    }

    public static boolean test02(String str) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        char[] charArray = str.toCharArray();
        Set<Character> keySet = map.keySet();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < charArray.length; i ++) {
            Character ch = charArray[i];
            if (keySet.contains(ch)) {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) { return false; }
                Character left = stack.pop();
                if (map.get(left) != ch) { return false; }
            }
        }

        return stack.isEmpty();
    }

}
