package com.xdc.basic.api.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class RegexTest
{
    @Test
    public void checkInput()
    {
        // 一般网页中名称的检验：中文、字母、数字、点、中划线、下划线
        String regex = "^[\\u4E00-\\u9FA5a-zA-Z0-9\\.\\-_]+$";
        String input = "我是个符合的字符串abcABC.-_";

        regexSearch(regex, input);
    }

    @Test
    public void searchChinese()
    {
        // 匹配中文（注意：String中的正则表达式相关的方法匹配中文无效，原因不清楚）
        String regex = "[\\u4E00-\\u9FA5]+";
        String input = "I love 中华 --徐大超";

        regexSearch(regex, input);
    }

    @Test
    public void searchWord()
    {
        String word = "there";

        String regex = "\\b" + word + "\\b";
        String input = "Where there is a will there is a way.";

        regexSearch(regex, input);
    }

    @Test
    public void searchWordPrefix()
    {
        String wordPrefix = "the";

        String regex = "\\b" + wordPrefix + "[a-zA-Z]+\\b";
        String input = "Where there is a will there is a way.";

        regexSearch(regex, input);
    }

    @Test
    public void getAllWord()
    {
        String regex = "\\b[a-zA-Z]+\\b";
        String input = "Where there is a will there is a way.";

        regexSearch(regex, input);
    }

    @Test
    public void expressionParse()
    {
        // 表达式解析： 整数1 加或乘 整数2
        // \s：代表空白，-？：代表0个或一个减号，\d:代表数字
        String regex = "^\\s*(-?\\d+)\\s*([+*])\\s*(-?\\d+)\\s*$";
        String input = " 11 * 12 ";

        regexSearch(regex, input);
    }

    @Test
    public void searchWordCaseInsensitive1()
    {
        String word = "thERE";

        String regex = "(?i)\\b" + word + "\\b";
        String input = "Where there is a will there is a way.";

        regexSearch(regex, input);
    }

    @Test
    public void searchWordCaseInsensitive2()
    {
        String word = "thERE";

        String regex = "\\b" + word + "\\b";
        String input = "Where there is a will there is a way.";

        int flags = Pattern.CASE_INSENSITIVE;
        regexSearch(regex, input, flags);
    }

    @Test
    public void patternFlags()
    {
        String word = "thERE";

        String regex = "\\b" + word + "\\b";
        String input = "Where there is a will there is a way.";

        // 常用的标志
        // 常量......................等价的内嵌标志表达式..意义
        // Pattern.CASE_INSENSITIVE..(?i)................启用不区分大小写匹配。默认情况下，仅匹配 US-ASCII 字符集中的字符。
        // Pattern.COMMENTS..........(?x)................模式中允许存在空白和注释。在这种模式下，空白和以#开始的直到行尾的内嵌注释会被忽略。
        // Pattern.MULTILINE.........(?m)................启用多行（multiline）模式。在多行模式下，表达式^和$分别匹配输入序列行结束符前面和行结束符的前面。默认情况下，表达式仅匹配整个输入序列的开始和结尾。
        // Pattern.DOTALL............(?s)................启用 dotall 模式。在 dotall 模式下，表达式.匹配包括行结束符在内的任意字符。
        // Pattern.UNICODE_CASE......(?u)................配合CASE_INSENSITIVE标志来启用，使 Unicode字符不区分大小写匹配

        int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
        regexSearch(regex, input, flags);
    }

    @Test
    public void patternSplit()
    {
        String regex = "\\d";
        String input = "one9two4three7four1five";

        Pattern pattern = Pattern.compile(regex);
        String[] items = pattern.split(input);
        for (String item : items)
        {
            System.out.println(item);
        }
    }

    /**
     * 比matcher.replaceAll(replacement)灵活，可以在每个匹配出替换为不同的值。 用哪个是需求而定。
     */
    @Test
    public void matcherReplacement()
    {
        String regex = "\\d";
        String input = "one9two4three7four1five";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input); // 获得匹配器对象

        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            String replace = RandomStringUtils.random(1, "!@#$");
            // 处理特殊字符\和$,在前面添加转义符\
            replace = Matcher.quoteReplacement(replace);
            System.out.println("Random char: " + replace);
            matcher.appendReplacement(sb, replace);
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }

    /**
     * find和matches比较
     * 
     * matcher.find()字符串内查找
     * 
     * matcher.matches()整个字符串匹配 (ps: string.matches()调用的是matcher.matches())
     */
    @Test
    public void matcherFindAndMatches()
    {
        String regex = "[a-zA-Z0-9_]";
        String input = "User_001";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input); // 获得匹配器对象

        System.out.println(matcher.find());
        System.out.println(matcher.matches());

        regex = "[a-zA-Z0-9_]*";
        input = "User_001";

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(input); // 获得匹配器对象

        System.out.println(matcher.find());
        System.out.println(matcher.matches());
    }

    private void regexSearch(String regex, String input)
    {
        regexSearch(regex, input, 0);
    }

    private void regexSearch(String regex, String input, int flags)
    {
        Pattern pattern = null;
        Matcher matcher = null;
        try
        {
            pattern = Pattern.compile(regex, flags);
            matcher = pattern.matcher(input);
        }
        catch (PatternSyntaxException pse)
        {
            System.out.println("There is a problem with the regular expression!");
            System.out.println("The pattern in question is: " + pse.getPattern());
            System.out.println("The description is: " + pse.getDescription());
            System.out.println("The message is: " + pse.getMessage());
            System.out.println("The index is: " + pse.getIndex());
            System.exit(0);
        }

        int count = 0;
        // 一直不停的找，直到结尾
        while (matcher.find())
        {
            System.out.println("======================================");
            count++;
            System.out.println("Match number      : " + count);
            System.out.println("Match text        : " + matcher.group());
            System.out.println("Match start index : " + matcher.start());
            System.out.println("Match end index   : " + matcher.end());

            // matcher.groupCount()方法返回int类型值，表示当前 Matcher模式中捕获组的数量。
            // 有一个特别的组——组 0，它表示整个表达式。这个组不包括在groupCount的报告范围内。因此注意下面需使用<=号。
            // 因此matcher.group(0)与matcher.group()是等效的。
            for (int i = 0; i <= matcher.groupCount(); i++)
            {
                System.out.println("  Group number      : " + i);
                System.out.println("  Group text        : " + matcher.group(i));
                System.out.println("  Group start index : " + matcher.start(i));
                System.out.println("  Group end index   : " + matcher.end(i));
                System.out.println();
            }

            System.out.println();
        }
    }
}
