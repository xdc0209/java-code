package com.xdc.basic.api.args.args4j.randwom;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;

public class RandomMain
{
    public static void main(String[] args)
    {
        args = new String[] { "arg1", "-c", "10", "-m", "custom", "arg2", "-s", "xdc0209", "arg3" };

        CmdLineParser parser = null;
        try
        {
            RandomCommand command = new RandomCommand();
            parser = new CmdLineParser(command);

            // if you have a wider console, you could increase the value; default 80.
            parser.setUsageWidth(150);

            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // 联合校验框架不支持，要自己写
            if (command.getMode() == RandomMode.Custom && StringUtils.isEmpty(command.getString()))
            {
                throw new CmdLineException(parser, "Option \"-s (--string)\" is required, when mode is Custom");
            }

            // after parsing arguments, you should check if enough arguments are given.
            // if (CollectionUtils.isEmpty(command.getArguments()))
            // {
            //     throw new CmdLineException(parser, "No argument is given");
            // }

            command.excute();

            // access non-option arguments
            if (CollectionUtils.isNotEmpty(command.getArguments()))
            {
                System.out.println("other arguments are: " + command.getArguments());
            }
        }
        catch (CmdLineException e)
        {
            // If 'usage' value is empty, the option will not be displayed in the usage screen.
            // 注意没有指定usage属性，当使用parser.printExample(ExampleMode.ALL), 该选项不会被打印出来。

            // wrong argument enteres, so print the usage message as supplied by args4j
            System.err.println(e.getMessage());
            System.err.println();

            if (parser != null)
            {
                System.err.println("Usage:");
                parser.printUsage(System.err);
                System.err.println();

                System.err.println("Example:");
                // print option sample. This is useful some time
                System.err.println(parser.printExample(ExampleMode.ALL));
                System.err.println(parser.printExample(ExampleMode.REQUIRED));
                System.err.println();
            }
            System.exit(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
