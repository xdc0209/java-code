package com.xdc.basic.api.args.args4j.subcommand.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 分发抽象类
 */
public abstract class AbstractDispatchCommand implements Command
{
    protected Map<String, Class<? extends AbstractAtomCommand>> subcommands = new LinkedHashMap<String, Class<? extends AbstractAtomCommand>>();

    protected abstract void initSubcommmands();

    @Override
    public void parseAndExec(String[] args)
    {
        // 初始化子命令
        initSubcommmands();

        // 获取子命令
        Command command = getSubcommand(args);

        // 执行子命令
        executeSubcommand(command, args);
    }

    private void executeSubcommand(Command command, String[] args)
    {
        String[] subcommandArgs = ArrayUtils.subarray(args, 1, args.length);
        command.parseAndExec(subcommandArgs);
    }

    private Command getSubcommand(String[] args)
    {
        if (ArrayUtils.isEmpty(args))
        {
            help();
            System.exit(1);
        }

        String subcommandName = args[0];
        Class<?> subcommandClass = subcommands.get(subcommandName);
        if (subcommandClass == null)
        {
            System.err.println("\"" + subcommandName + "\" is not a valid  subcommand");
            System.err.println();

            help();
            System.exit(1);
        }

        Command command = null;
        try
        {
            command = (Command) subcommandClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println();

            help();
            System.exit(1);
        }
        return command;
    }

    private void help()
    {
        System.err.println(String.format("Usage: %s <subcommand> [options] [args]", this.getClass().getSimpleName()));
        System.err.println();

        System.err.println("Available subcommands:");
        for (String subcommand : subcommands.keySet())
        {
            System.err.println("  " + subcommand);
        }
        System.err.println();
    }
}
