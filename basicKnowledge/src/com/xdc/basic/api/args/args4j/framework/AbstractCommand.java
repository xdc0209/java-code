package com.xdc.basic.api.args.args4j.framework;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;

public abstract class AbstractCommand implements Command
{
    @Override
    public void parseAndExec(String[] args)
    {
        // 创建解析器
        CmdLineParser parser = new CmdLineParser(this);

        // 进行参数解析，此时args4j会进行进本的参数校验
        parse(parser, args);

        // 执行业务逻辑
        excute();
    }

    /**
     * 进行参数解析
     * 
     * @param parser
     * @param args
     */
    private void parse(CmdLineParser parser, String[] args)
    {
        // if you have a wider console, you could increase the value; default 80.
        parser.setUsageWidth(150);

        try
        {
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // 进行复杂的参数检验
            complexArgsCheck(parser);

            // after parsing arguments, you should check if enough arguments are given.
            // if (CollectionUtils.isEmpty(command.getArguments()))
            // {
            // throw new CmdLineException(parser, "No argument is given");
            // }
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
