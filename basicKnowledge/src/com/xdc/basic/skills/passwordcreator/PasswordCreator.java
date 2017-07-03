package com.xdc.basic.skills.passwordcreator;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.skills.GetPath;

public class PasswordCreator
{
    public static void main(String[] args) throws IOException
    {
        String curPath = GetPath.getRelativePath();

        FileReader input = new FileReader(curPath + "PasswordCreator.txt");
        List<String> lines = IOUtils.readLines(input);
        IOUtils.closeQuietly(input);

        // 每次换密码时，更改盐值，产生新的密码
        String salt = "春暖花开";

        for (String line : lines)
        {
            if (StringUtils.isBlank(line))
            {
                System.out.println();
            }
            else
            {
                String createPassword = createPassword(line, salt);
                System.out.printf("%s %s\n", createPassword, line);
            }
        }
    }

    private static String createPassword(String text, String salt) throws IOException
    {
        if (StringUtils.isBlank(text))
        {
            return null;
        }

        String textTrimed = StringUtils.trim(text);
        String textWithSalt = StringUtils.join(salt, textTrimed);

        String sha512Hex = DigestUtils.sha512Hex(textWithSalt);

        String alpha = extractAlpha(sha512Hex);
        String numeric = extractNumeric(sha512Hex);

        String alpha4 = leftAndRightPad(alpha, 4, 'x');
        String numeric6 = leftAndRightPad(numeric, 8, '8');

        String password = StringUtils.capitalize(alpha4 + numeric6);

        return password;
    }

    /**
     * 抽取字符串中的字母
     */
    private static String extractAlpha(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray())
        {
            if (CharUtils.isAsciiAlpha(c))
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 抽取字符串中的数字
     */
    private static String extractNumeric(String s)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray())
        {
            if (CharUtils.isAsciiNumeric(c))
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 修正字符串长度。如果长度超出则截取指定长度；如果长度不足则补全指定字符。
     * 
     * @param s
     *            原始字符串
     * @param len
     *            期望的长度
     * @param c
     *            补全字符
     * @return
     *         修正后的字符串
     */
    private static String leftAndRightPad(String s, int len, char c)
    {
        // 截取指定长度
        String left = StringUtils.left(s, len);

        // 如果长度不足，补全指定字符
        String rightPad = StringUtils.rightPad(left, len, c);

        return rightPad;
    }
}
