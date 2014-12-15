package com.xdc.basic.api.jmx.virgo.cli.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 子命令抽象类
 */
public abstract class AbstractAtomCommand implements Command
{
    // 使用LinkedHashMap，保存放入顺序
    private Map<String, String> parsedOptions   = new LinkedHashMap<String, String>();

    private List<String>        parsedArguments = new ArrayList<String>();

    // 使用LinkedHashMap，保存放入顺序
    private Map<String, Option> options         = new LinkedHashMap<String, Option>();

    @Override
    public void parseAndExec(String[] args)
    {
        // 初始化选项
        initOptions(options);

        // 进行参数解析，此时args4j会进行进本的参数校验
        parse(args);

        // 执行业务逻辑
        excute(parsedOptions, parsedArguments);
    }

    protected abstract void initOptions(Map<String, Option> options);

    /**
     * 执行业务逻辑，需在子类中实现
     */
    protected abstract void excute(Map<String, String> parsedOptions, List<String> parsedArguments);

    /**
     * 进行复杂的参数检验，默认空实现，如果需要，子类覆写此方法即可
     */
    protected void complexArgsCheck(Map<String, String> parsedOptions, List<String> parsedArguments)
    {
    }

    /**
     * 进行参数解析
     * 
     * @param args
     */
    private void parse(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            String curArg = args[i];

            // 以-开头的字符串为选项，否则为参数
            if (curArg.startsWith("-"))
            {
                Option option = options.get(curArg);
                if (option == null)
                {
                    handleError(String.format("Unknow option: '%s'", curArg));
                }

                // 选项是否带有参数
                if (option.isWithArgument())
                {
                    if (i + 1 >= args.length)
                    {
                        handleError(String.format("Option '%s' requires an argument", curArg));
                    }

                    String nextArg = args[i + 1];
                    parsedOptions.put(curArg, nextArg);
                }
                else
                {
                    parsedOptions.put(args[i], null);
                }
            }
            else
            {
                parsedArguments.add(curArg);
            }
        }

        // 检查必选的参数
        for (Option option : options.values())
        {
            if (option.isRequired() && !parsedOptions.containsKey(option.getOption()))
            {
                handleError(String.format("Option '%s' is required", option.getOption()));
            }
        }

        // 进行复杂的参数检验
        complexArgsCheck(parsedOptions, parsedArguments);
    }

    /**
     * 异常处理
     */
    private void handleError(String error)
    {
        System.err.println(error);
        help();
        System.exit(1);
    }

    /**
     * 输出帮助信息
     */
    private void help()
    {
        System.err.println("Usage:");
        for (Option option : options.values())
        {
            System.err.println(String.format(" %5s : %s", option.getOption(), option.getUsage()));
        }
        System.err.println();
    }
}
