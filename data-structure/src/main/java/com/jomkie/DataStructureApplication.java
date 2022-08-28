package com.jomkie;

import java.util.*;

public class DataStructureApplication {

    final static String LEFT = "(";
    final static String RIGHT = ")";

    public static void main(String[] args) {
        // ((2+5)+3
//        List<String> original = strToList("((2+5)+3");
        // (((2-3)*4)+(8/4-2))
//        List<String> original = strToList("(((2-3)*4)+(8/4-2))");
//         (2*5-3)
        List<String> original = strToList("(2*5-3)");

        List<String> result = parseSuffixExpressionList(original);
        System.out.println(result);
    }

    public static boolean checkOperator(String o) {
       return "+".equals(o) || "-".equals(o) || "*".equals(o) || "/".equals(o);
    }

    public static int comparator(String a, String b) {
        return getValue(a) - getValue(b);
    }

    public static int getValue(String c) {
        switch (c) {
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 2;
            case "(":
                return -1;
            default:
                throw new RuntimeException("异常的操作符");
        }
    }

    public static List<String> parseSuffixExpressionList(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<String>();  // 符号栈

        //说明：因为 s2这个栈，在整个转换过程中，没有pop操作，后面还需要逆序输出
        //所以，把 s2 这个栈换成 List<String> 即可
        //Stack<String> s2 = new Stack<String>();  // 存储中间结果的栈
        List<String> s2 = new ArrayList<String>();  //存储中间结果的list

        //遍历 ls
        for(String item : ls) {
            //如果是一个栈，就加入到s2
            if(item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                // 如果是右括号，则依次弹出s1栈顶的运算符，并压入 s2,知道遇到左括号为止，此时将这一对括号丢弃

                while(!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();  // 将左括号 弹出，消除小括号
            } else {
                // 当 item 的优先级小于或等于栈顶运算符，将s1栈顶的运算符弹出并压入s2中，再次转到4.1与s1中新的栈顶运算符相比较
                //问题：缺少比较优先级高低的方法
                while(s1.size() != 0 && getValue(s1.peek()) >= getValue(item)) {
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

    public static List<String> strToList(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(String.valueOf(s.charAt(i)));
        }
        return result;
    }
}
