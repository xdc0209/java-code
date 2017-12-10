package com.xdc.basic.api.jmx.virgo.cli.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 分发抽象类
 */
public abstract class AbstractDispatchCommand implements Command
{
    // 存放子命令，使用LinkedHashMap，保存放入顺序
    private Map<String, AbstractAtomCommand> subcommands = new LinkedHashMap<String, AbstractAtomCommand>();

    @Override
    public void parseAndExec(String[] args)
    {
        // 初始化子命令
        initSubcommmands(subcommands);

        // 获取子命令
        Command command = getSubcommand(args);

        // 执行子命令
        executeSubcommand(command, args);
    }

    /**
     * 初始化子命令，需在子类中实现
     */
    protected abstract void initSubcommmands(Map<String, AbstractAtomCommand> subcommands);

    private Command getSubcommand(String[] args)
    {
        if (ArrayUtils.isEmpty(args))
        {
            // 没有参数，则显示帮助
            handleError(null);
        }

        // The "sub-command" pattern refers to the design of the command line like git and svn,
        // where the first argument to the command designates a sub-command (say git checkout),
        // then everything that follows afterward are parsed by this sub-command (which is usually
        // different depending on which sub-command was selected.)
        String subcommandName = args[0];
        Command command = subcommands.get(subcommandName);
        if (command == null)
        {
            handleError(String.format("'%s' is not a valid subcommand", subcommandName));
        }

        return command;
    }

    private void executeSubcommand(Command command, String[] args)
    {
        String[] subcommandArgs = ArrayUtils.subarray(args, 1, args.length);
        command.parseAndExec(subcommandArgs);
    }

    /**
     * 异常处理
     */
    private void handleError(String error)
    {
        if (StringUtils.isNotBlank(error))
        {
            System.err.println(error);
            System.err.println();
        }

        help();
        System.exit(1);
    }

    /**
     * 输出帮助信息
     */
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
