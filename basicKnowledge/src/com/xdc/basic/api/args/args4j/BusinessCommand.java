package com.xdc.basic.api.args.args4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

public class BusinessCommand
{
    @Option(name = "-r", usage = "recursively run something")
    private boolean      recursive;

    @Option(name = "-o", usage = "output to this file", metaVar = "OUTPUT", required = true)
    private File         out       = new File(".");

    @Option(name = "-str")
    // no usage
    private String       str       = "(default value)";

    @Option(name = "-n", usage = "repeat <n> times\nusage can have new lines in it and also it can be verrrrrrrrrrrrrrrrrry long")
    private int          num       = -1;

    // using 'handler=...' allows you to specify a custom OptionHandler
    // implementation class. This allows you to bind a standard Java type
    // with a non-standard option syntax
    @Option(name = "-custom", handler = BooleanOptionHandler.class, usage = "boolean value for checking the custom handler")
    private boolean      data;

    // receives other command line parameters than options
    @Argument
    private List<String> arguments = new ArrayList<String>();

    public boolean isRecursive()
    {
        return recursive;
    }

    public void setRecursive(boolean recursive)
    {
        this.recursive = recursive;
    }

    public File getOut()
    {
        return out;
    }

    public void setOut(File out)
    {
        this.out = out;
    }

    public String getStr()
    {
        return str;
    }

    public void setStr(String str)
    {
        this.str = str;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public boolean isData()
    {
        return data;
    }

    public void setData(boolean data)
    {
        this.data = data;
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
