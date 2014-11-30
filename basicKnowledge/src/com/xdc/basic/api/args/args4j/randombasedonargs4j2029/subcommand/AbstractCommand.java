package com.xdc.basic.api.args.args4j.randombasedonargs4j2029.subcommand;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

public abstract class AbstractCommand implements Command
{
    @Override
    public void parseAndExec(String[] args)
    {
        try
        {
            // 创建解析器
            CmdLineParser parser = new CmdLineParser(this);

            // 进行参数解析，此时args4j会进行进本的参数校验
            parse(parser, args);

            // 执行业务逻辑
            excute(parser);
        }
        catch (CmdLineException e)
        {
            // wrong argument enteres, so print the usage message as supplied by args4j
            System.err.println(e.getMessage());
            System.err.println();

            // 输出帮助
            help(e.getParser());

            System.exit(1);
        }
    }

    /**
     * 进行参数解析
     * 
     * @param parser
     * @param args
     * @throws CmdLineException
     */
    private void parse(CmdLineParser parser, String[] args) throws CmdLineException
    {
        // if you have a wider console, you could increase the value; default 80.
        // parser.getProperties().withUsageWidth(150);
        parser.getProperties().withUsageWidth(150);

        parser.parseArgument(args);

        // you can parse additional arguments if you want.
        // parser.parseArgument("more","args");

        // 进行复杂的参数检验
        complexArgsCheck(parser);

        // after parsing arguments, you should check if enough arguments are given.
        // if (CollectionUtils.isEmpty(command.getArguments()))
        // {
        //     throw new CmdLineException(parser, "No argument is given");
        // }
    }

    /**
     * 进行复杂的参数检验，默认空实现，如果需要，子类覆写此方法即可
     * 
     * @param parser
     * @throws CmdLineException
     */
    protected void complexArgsCheck(CmdLineParser parser) throws CmdLineException
    {
    }

    /**
     * 执行业务逻辑，需在子类中实现
     * 
     * @param parser
     * @throws CmdLineException
     */
    protected abstract void excute(CmdLineParser parser) throws CmdLineException;

    /**
     * 输出帮助信息
     * 
     * @param parser
     */
    protected void help(CmdLineParser parser)
    {
        // If 'usage' value is empty, the option will not be displayed in the usage screen.
        // 注意没有指定usage属性，当使用parser.printExample(ExampleMode.ALL), 该选项不会被打印出来。

        ByteArrayOutputStream outStreamForUsage = new ByteArrayOutputStream();
        parser.printUsage(outStreamForUsage);
        String usage = outStreamForUsage.toString();
        if (StringUtils.isNotBlank(usage))
        {
            System.err.println("Usage:");
            System.err.println(usage);
            System.err.println();
        }

        StringBuilder sbForExample = new StringBuilder();
        sbForExample.append(parser.printExample(OptionHandlerFilter.ALL)).append(SystemUtils.LINE_SEPARATOR);
        sbForExample.append(parser.printExample(OptionHandlerFilter.REQUIRED)).append(SystemUtils.LINE_SEPARATOR);
        String example = sbForExample.toString();
        if (StringUtils.isNotBlank(example))
        {
            System.err.println("Example:");
            System.err.println(example);
            System.err.println();
        }
    }
}
