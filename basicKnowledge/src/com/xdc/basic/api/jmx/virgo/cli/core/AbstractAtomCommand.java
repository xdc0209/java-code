package com.xdc.basic.api.jmx.virgo.cli.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;

/**
 * 子命令抽象类
 */
public abstract class AbstractAtomCommand implements Command
{
    protected Map<String, String> parsedOptions = new LinkedHashMap<String, String>();

    protected Map<String, Option> options       = new LinkedHashMap<String, Option>();

    protected abstract void initOptions();

    @Override
    public void parseAndExec(String[] args)
    {
        // 初始化选项
        initOptions();

        // 进行参数解析，此时args4j会进行进本的参数校验
        parse(args);

        // 执行业务逻辑
        excute();
    }

    /**
     * 进行参数解析
     * 
     * @param args
     */
    private void parse(String[] args)
    {
        List<String> errors = new ArrayList<String>();
        for (int i = 0; i < args.length; i++)
        {
            Option option = options.get(args[i]);
            if (option != null)
            {
                if (option.isWithArgument())
                {
                    if (i + 1 < args.length)
                    {
                        parsedOptions.put(args[i], args[i + 1]);
                        i++;
                    }
                    else
                    {
                        errors.add("Option missing argument: " + args[i]);
                    }
                }
                else
                {
                    parsedOptions.put(args[i], null);
                }
            }
            else
            {
                errors.add("Unknow option: " + args[i]);
            }
        }

        if (!errors.isEmpty())
        {
            for (String error : errors)
            {
                System.err.println(error);
            }
            System.err.println();

            usage();
            System.exit(1);
        }

        // 进行复杂的参数检验
        complexArgsCheck();
    }

    /**
     * 输出帮助信息
     * 
     * @param parser
     */
    protected void usage()
    {
    }

    /**
     * 进行复杂的参数检验，默认空实现，如果需要，子类覆写此方法即可
     * 
     * @param parser
     * @throws CmdLineException
     */
    protected void complexArgsCheck()
    {
    }

    /**
     * 执行业务逻辑，需在子类中实现
     */
    protected abstract void excute();

    /**
     * 输出帮助信息
     * 
     * @param parser
     */
    protected void help(CmdLineParser parser)
    {
        // If 'usage' value is empty, the option will not be displayed in the usage screen.
        // 注意没有指定usage属性，当使用parser.printExample(ExampleMode.ALL), 该选项不会被打印出来。

        System.err.println("Usage:");
        parser.printUsage(System.err);
        System.err.println();

        System.err.println("Example:");
        // print option sample. This is useful some time
        System.err.println(parser.printExample(ExampleMode.ALL));
        System.err.println(parser.printExample(ExampleMode.REQUIRED));
        System.err.println();
    }
}
