package com.xdc.basic.api.args.args4j.randwom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

public class RandomCommand implements Command
{
    @Option(name = "-c", aliases = { "--count" }, usage = "the length of random string to create", required = true, metaVar = "<count>")
    private int          count;

    @Option(name = "-m", aliases = { "--mode" }, usage = "the character mode of random", required = true)
    private RandomMode   mode;

    @Option(name = "-s", aliases = { "--string" }, usage = "the string containing the set of characters to use when mode is Custom", metaVar = "<string>")
    private String       string;

    @Option(name = "-o", aliases = { "--output" }, usage = "the file to output", metaVar = "<file>")
    private File         output;

    // using 'handler=...' allows you to specify a custom OptionHandler
    // implementation class. This allows you to bind a standard Java type
    // with a non-standard option syntax
    @Option(name = "-M", aliases = { "--multi-line" }, usage = "if output with multi line or not", handler = BooleanOptionHandler.class)
    private boolean      multiLine = false;

    // receives other command line parameters than options
    @Argument
    // no useful in this example, just to show usage
    private List<String> arguments = new ArrayList<String>();

    @Override
    public void excute()
    {
        String randomStr = null;
        if (mode == RandomMode.Ascii)
        {
            randomStr = RandomStringUtils.randomAscii(count);
        }
        else if (mode == RandomMode.Alphabetic)
        {
            randomStr = RandomStringUtils.randomAlphabetic(count);
        }
        else if (mode == RandomMode.AlphaNumeric)
        {
            randomStr = RandomStringUtils.randomAlphanumeric(count);
        }
        else if (mode == RandomMode.Numeric)
        {
            randomStr = RandomStringUtils.randomNumeric(count);
        }
        else if (mode == RandomMode.Custom)
        {
            randomStr = RandomStringUtils.random(6, string);
        }

        if (multiLine)
        {
            StringBuilder sb = new StringBuilder();
            for (char c : randomStr.toCharArray())
            {
                sb.append(c).append(SystemUtils.LINE_SEPARATOR);
            }
            randomStr = sb.toString();
        }

        if (output == null)
        {
            System.out.println(randomStr);
        }
        else
        {
            try
            {
                FileUtils.writeStringToFile(output, randomStr, Charsets.UTF_8, false);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public RandomMode getMode()
    {
        return mode;
    }

    public void setMode(RandomMode mode)
    {
        this.mode = mode;
    }

    public String getString()
    {
        return string;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public File getOutput()
    {
        return output;
    }

    public void setOutput(File output)
    {
        this.output = output;
    }

    public boolean isMultiLine()
    {
        return multiLine;
    }

    public void setMultiLine(boolean multiLine)
    {
        this.multiLine = multiLine;
    }

    public List<String> getArguments()
    {
        return arguments;
    }

    public void setArguments(List<String> arguments)
    {
        this.arguments = arguments;
    }
}
