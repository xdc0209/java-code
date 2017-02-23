package com.xdc.basic.api.args.apache.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.ArrayUtils;

public class Mkdir
{
    public static void main(String[] args)
    {
        Options opt = new Options();
        opt.addOption("p", false, "no error if existing, make parent directories as needed.");
        opt.addOption("v", "verbose", false, "explain what is being done.");

        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("search for buildfile towards the root of the filesystem and use it");
        opt.addOption(OptionBuilder.create("O"));

        OptionBuilder.withLongOpt("block-size");
        OptionBuilder.withDescription("use SIZE-byte blocks");
        OptionBuilder.withValueSeparator('=');
        OptionBuilder.hasArg();
        opt.addOption(OptionBuilder.create());

        opt.addOption("h", "help", false, "print help for the command.");

        String formatstr = "gmkdir [-p][-v/--verbose][--block-size][-h/--help] DirectoryName";

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();
        CommandLine cl = null;
        try
        {
            // 处理Options和参数
            cl = parser.parse(opt, args);
        }
        catch (ParseException e)
        {
            formatter.printHelp(formatstr, opt); // 如果发生异常，则打印出帮助信息
        }

        // 选项和参数都为空，输出帮助帮助信息
        if (ArrayUtils.isEmpty(cl.getOptions()) && ArrayUtils.isEmpty(cl.getArgs()))
        {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp(formatstr, "--header--", opt, "--footer--");
            return;
        }

        // 如果包含有-h或--help，则打印出帮助信息
        if (cl.hasOption("h"))
        {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp(formatstr, "--header--", opt, "--footer--");
            return;
        }

        // 判断是否有-p参数
        if (cl.hasOption("p"))
        {
            System.out.println("has p");
        }

        // 判断是否有-v或--verbose参数
        if (cl.hasOption("v"))
        {
            System.out.println("has v");
        }

        // 判断是否含有block-size参数
        if (cl.hasOption("block-size"))
        {
            // print the value of block-size
            System.out.println("block-size=" + cl.getOptionValue("block-size"));
        }

        // 获取参数值，这里主要是DirectoryName
        String[] str = cl.getArgs();
        int length = str.length;
        System.out.println("length=" + length);
        System.out.println("Str[0]=" + str[0]);
    }
}
