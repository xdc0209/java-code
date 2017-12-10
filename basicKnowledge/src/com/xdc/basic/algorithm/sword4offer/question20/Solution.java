package com.xdc.basic.algorithm.sword4offer.question20;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 本人对“ 《剑指offer(第2版)》 >>> 面试题20：表示数值的字符串”要求的科学技术法持保留意见，里面的部分格式存在争议，但OJ的测试用例是基于此判题的，所以在实现上是遵照书中的格式的。
 * 
 * 格式争议点：
 * （1）书中认为：
 * "-.123" 正确
 * "-123." 正确
 * "-.123e-5" 正确
 * "-123.e-5" 正确
 * "-.e-5" 错误
 * （2）我认为：
 * "-.123" 错误
 * "-123." 错误
 * "-.123e-5" 错误
 * "-123.e-5" 错误
 * "-.e-5" 错误
 * 
 * 正式因为这个争议，导致在State.dot对e或E的状态转换时，要先判断是否存在整数部分，导出状态机不十分纯粹。
 */
public class Solution
{
    enum State
    {
        start, // 开始
        sign_integer, // 整数部分的符号
        integer, // 整数部分
        dot, // 小数点
        decimal, // 小数部分
        e, // 指数符
        sign_exponent, // 指数部分的符号
        exponent, // 指数部分
        end; // 结束
    }

    enum Input
    {
        sign, // +-
        digital, // 0123456789
        dot, // .
        e; // eE

        public static Input valueOf(char c)
        {
            if (c == '+' || c == '-')
            {
                return Input.sign;
            }
            else if (c >= '0' && c <= '9')
            {
                return Input.digital;
            }
            else if (c == '.')
            {
                return Input.dot;
            }
            else if (c == 'e' || c == 'E')
            {
                return Input.e;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * 使用if语句实现状态机(与switch本质上一样)，其实最优方案可以根据《Head First设计模式》中的状态模式章节，改成状态模式的实现，代码更易扩展。
     */
    public static boolean isNumeric1(char[] str)
    {
        if (str == null)
        {
            return false;
        }

        State state = State.start; // 当前状态设置为开始。
        boolean hasInteger = false; // 是否存在整数部分，用于在State.dot对e或E的状态转换。

        for (int i = 0; i < str.length; i++)
        {
            char c = str[i];

            if (c == '+' || c == '-')
            {
                if (state == State.start)
                {
                    state = State.sign_integer;
                }
                else if (state == State.e)
                {
                    state = State.sign_exponent;
                }
                else
                {
                    return false;
                }
            }
            else if (c >= '0' && c <= '9')
            {
                if (state == State.start)
                {
                    state = State.integer;
                    hasInteger = true;
                }
                else if (state == State.sign_integer)
                {
                    state = State.integer;
                    hasInteger = true;
                }
                else if (state == State.integer)
                {
                    state = State.integer;
                    hasInteger = true;
                }
                else if (state == State.dot)
                {
                    state = State.decimal;
                }
                else if (state == State.decimal)
                {
                    state = State.decimal;
                }
                else if (state == State.e)
                {
                    state = State.exponent;
                }
                else if (state == State.sign_exponent)
                {
                    state = State.exponent;
                }
                else if (state == State.exponent)
                {
                    state = State.exponent;
                }
                else
                {
                    return false;
                }
            }
            else if (c == '.')
            {
                if (state == State.start)
                {
                    state = State.dot;
                }
                else if (state == State.sign_integer)
                {
                    state = State.dot;
                }
                else if (state == State.integer)
                {
                    state = State.dot;
                }
                else
                {
                    return false;
                }
            }
            else if (c == 'e' || c == 'E')
            {
                if (state == State.integer)
                {
                    state = State.e;
                }
                else if (state == State.dot)
                {
                    if (hasInteger)
                    {
                        state = State.e;
                    }
                    else
                    {
                        return false;
                    }
                }
                else if (state == State.decimal)
                {
                    state = State.e;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        if (state == State.integer || state == State.dot || state == State.decimal || state == State.exponent)
        {
            state = State.end;
        }

        return state == State.end;
    }

    /**
     * 使用if语句实现状态机(与switch本质上一样)，其实最优方案可以根据《Head First设计模式》中的状态模式章节，改成状态模式的实现，代码更易扩展。
     */
    public static boolean isNumeric2(char[] str)
    {
        if (str == null)
        {
            return false;
        }

        Stack<State> stack = new Stack<State>(); // 使用栈保留每个字符的处理策略，方便后期复杂一点的状态转换，如在State.dot对e或E的状态转换。
        stack.push(State.start);

        for (int i = 0; i < str.length; i++)
        {
            char c = str[i];

            if (c == '+' || c == '-')
            {
                if (stack.peek() == State.start)
                {
                    stack.push(State.sign_integer);
                }
                else if (stack.peek() == State.e)
                {
                    stack.push(State.sign_exponent);
                }
                else
                {
                    return false;
                }
            }
            else if (c >= '0' && c <= '9')
            {
                if (stack.peek() == State.start)
                {
                    stack.push(State.integer);
                }
                else if (stack.peek() == State.sign_integer)
                {
                    stack.push(State.integer);
                }
                else if (stack.peek() == State.integer)
                {
                    stack.push(State.integer);
                }
                else if (stack.peek() == State.dot)
                {
                    stack.push(State.decimal);
                }
                else if (stack.peek() == State.decimal)
                {
                    stack.push(State.decimal);
                }
                else if (stack.peek() == State.e)
                {
                    stack.push(State.exponent);
                }
                else if (stack.peek() == State.sign_exponent)
                {
                    stack.push(State.exponent);
                }
                else if (stack.peek() == State.exponent)
                {
                    stack.push(State.exponent);
                }
                else
                {
                    return false;
                }
            }
            else if (c == '.')
            {
                if (stack.peek() == State.start)
                {
                    stack.push(State.dot);
                }
                else if (stack.peek() == State.sign_integer)
                {
                    stack.push(State.dot);
                }
                else if (stack.peek() == State.integer)
                {
                    stack.push(State.dot);
                }
                else
                {
                    return false;
                }
            }
            else if (c == 'e' || c == 'E')
            {
                if (stack.peek() == State.integer)
                {
                    stack.push(State.e);
                }
                else if (stack.peek() == State.dot)
                {
                    if (stack.contains(State.integer))
                    {
                        stack.push(State.e);
                    }
                    else
                    {
                        return false;
                    }
                }
                else if (stack.peek() == State.decimal)
                {
                    stack.push(State.e);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        if (stack.peek() == State.integer || stack.peek() == State.dot || stack.peek() == State.decimal
                || stack.peek() == State.exponent)
        {
            stack.push(State.end);
        }

        return stack.peek() == State.end;
    }

    /**
     * 使用状态矩阵实现状态机。
     */
    public static boolean isNumeric3(char[] str)
    {
        if (str == null)
        {
            return false;
        }

        final int[][] states = {
                // +- d . eE
                { 1, 2, 3, 0 }, // 0 start 开始
                { 0, 2, 3, 0 }, // 1 sign_integer 整数部分的符号
                { 0, 2, 3, 5 }, // 2 integer 整数部分
                { 0, 4, 0, 5 }, // 3 dot 小数点
                { 0, 4, 0, 5 }, // 4 decimal 小数部分
                { 6, 7, 0, 0 }, // 5 e 指数符
                { 0, 7, 0, 0 }, // 6 sign_exponent 指数部分的符号
                { 0, 7, 0, 0 }, // 7 exponent 指数部分
                { 0, 0, 0, 0 }, // 8 end 结束
        };

        int state = 0; // 当前状态设置为开始。
        boolean hasInteger = false; // 是否存在整数部分，用于在State.dot对e或E的状态转换。

        for (int i = 0; i < str.length; i++)
        {
            char c = str[i];

            int input = -1;
            if (c == '+' || c == '-')
            {
                input = 0;
            }
            else if (c >= '0' && c <= '9')
            {
                input = 1;
            }
            else if (c == '.')
            {
                input = 2;
            }
            else if (c == 'e' || c == 'E')
            {
                input = 3;
            }
            else
            {
                return false;
            }

            if (states[state][input] > 0)
            {
                if (states[state][input] == 2) // 是否正在处理在整数部分。
                {
                    hasInteger = true;
                }
                else if (state == 3 && input == 3) // 对State.dot对e或E的状态转换进行特殊判断。
                {
                    if (!hasInteger)
                    {
                        return false;
                    }
                }

                state = states[state][input];
            }
            else
            {
                return false;
            }
        }

        if (state == 2 || state == 3 || state == 4 || state == 7)
        {
            state = 8;
        }

        return state == 8;
    }

    /**
     * 使用状态矩阵实现状态机。
     */
    public static boolean isNumeric(char[] str)
    {
        HashMap<State, Map<Input, State>> map = new HashMap<State, Map<Input, State>>();

        map.put(State.start, new HashMap<Input, State>());
        map.get(State.start).put(Input.sign, State.sign_integer);
        map.get(State.start).put(Input.digital, State.integer);
        map.get(State.start).put(Input.dot, State.dot);

        map.put(State.sign_integer, new HashMap<Input, State>());
        map.get(State.sign_integer).put(Input.digital, State.integer);
        map.get(State.sign_integer).put(Input.dot, State.dot);

        map.put(State.integer, new HashMap<Input, State>());
        map.get(State.integer).put(Input.digital, State.integer);
        map.get(State.integer).put(Input.dot, State.dot);
        map.get(State.integer).put(Input.e, State.e);

        map.put(State.dot, new HashMap<Input, State>());
        map.get(State.dot).put(Input.digital, State.decimal);
        map.get(State.dot).put(Input.e, State.e);

        map.put(State.decimal, new HashMap<Input, State>());
        map.get(State.decimal).put(Input.digital, State.decimal);
        map.get(State.decimal).put(Input.e, State.e);

        map.put(State.e, new HashMap<Input, State>());
        map.get(State.e).put(Input.sign, State.sign_exponent);
        map.get(State.e).put(Input.digital, State.exponent);

        map.put(State.sign_exponent, new HashMap<Input, State>());
        map.get(State.sign_exponent).put(Input.digital, State.exponent);

        map.put(State.exponent, new HashMap<Input, State>());
        map.get(State.exponent).put(Input.digital, State.exponent);

        map.put(State.end, new HashMap<Input, State>());

        State state = State.start; // 当前状态设置为开始。
        boolean hasInteger = false; // 是否存在整数部分，用于在State.dot对e或E的状态转换。

        for (int i = 0; i < str.length; i++)
        {
            char c = str[i];

            if (map.get(state).get(Input.valueOf(c)) == State.integer) // 是否正在处理在整数部分。
            {
                hasInteger = true;
            }
            else if (state == State.dot && Input.valueOf(c) == Input.e) // 对State.dot对e或E的状态转换进行特殊判断。
            {
                if (!hasInteger)
                {
                    return false;
                }
            }

            state = map.get(state).get(Input.valueOf(c));
            if (state == null)
            {
                return false;
            }
        }

        if (state == State.integer || state == State.dot || state == State.decimal || state == State.exponent)
        {
            state = State.end;
        }

        return state == State.end;
    }

    public static void main(String[] args)
    {
        System.out.println(isNumeric("+100".toCharArray()));
        System.out.println(isNumeric("5e2".toCharArray()));
        System.out.println(isNumeric("-123".toCharArray()));
        System.out.println(isNumeric("3.1416".toCharArray()));
        System.out.println(isNumeric("-1E-16".toCharArray()));
        System.out.println(isNumeric("-.123".toCharArray()));
        System.out.println(isNumeric("-123.".toCharArray()));
        System.out.println(isNumeric("-.123e-5".toCharArray()));
        System.out.println(isNumeric("-123.e-5".toCharArray()));
        System.out.println();

        System.out.println(isNumeric("12e".toCharArray()));
        System.out.println(isNumeric("1a3.14".toCharArray()));
        System.out.println(isNumeric("1.2.3".toCharArray()));
        System.out.println(isNumeric("+-5".toCharArray()));
        System.out.println(isNumeric("12e+4.3".toCharArray()));
        System.out.println(isNumeric("-.e-5".toCharArray()));
        System.out.println();
    }
}
