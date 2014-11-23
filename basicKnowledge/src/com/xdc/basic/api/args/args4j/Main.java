package com.xdc.basic.api.args.args4j;

import static org.kohsuke.args4j.ExampleMode.ALL;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main
{
    public static void main(String[] args)
    {
        // args = new String[] { "-o", "dd" };

        BusinessCommand businessCommand = new BusinessCommand();

        CmdLineParser parser = new CmdLineParser(businessCommand);

        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);

        try
        {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
            //     if (arguments.isEmpty())
            //         throw new CmdLineException(parser, "No argument is given");

        }
        catch (CmdLineException e)
        {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java SampleMain" + parser.printExample(ALL));

            return;
        }

        // this will redirect the output to the specified output
        System.out.println(businessCommand.getOut());

        if (businessCommand.isRecursive())
            System.out.println("-r flag is set");

        if (businessCommand.isData())
            System.out.println("-custom flag is set");

        System.out.println("-str was " + businessCommand.getStr());

        if (businessCommand.getNum() >= 0)
            System.out.println("-n was " + businessCommand.getNum());

        // access non-option arguments
        System.out.println("other arguments are:");
        for (String s : businessCommand.getArguments())
            System.out.println(s);

    }
}
