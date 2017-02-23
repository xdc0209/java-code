package com.xdc.basic.tools.sqlplus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws SQLException, IOException
    {
        Map<String, String> arguments = parseCommandLine(args);

        checkArguments(arguments);

        SQLPlus.excute(arguments);
    }

    private static void println(String message)
    {
        System.out.println(message);
    }

    private static void println()
    {
        System.out.println();
    }

    private static void usage()
    {
        println("Usage 1: java -jar sqlplus.jar -f <file> -H <host> -P <port> -S <sid> -u <user> -p <password> [--dba]");
        println("Usage 2: java -jar sqlplus.jar -s <sql> -H <host> -P <port> -S <sid> -u <user> -p <password> [--dba]");
        println();
        println("Tips: sql delimiter is ##.");
        println();
        println("Sql demo:");
        println("    SELECT * FROM dual##");
        println();
        println("File demo:");
        println("    DECLARE");
        println("    BEGIN");
        println("        DBMS_OUTPUT.PUT_LINE('hello');");
        println("    END;##");
        println("    SELECT * FROM dual##");
        println();
    }

    private static void checkArguments(Map<String, String> arguments)
    {
        List<String> errors = new ArrayList<String>();
        for (Option option : Option.values())
        {
            if (option.isRequired())
            {
                if (!arguments.containsKey(option.getOption()))
                {
                    errors.add("Option is required: " + option.getOption());
                }
            }
        }

        if (arguments.containsKey(Option.FILE.getOption()) && arguments.containsKey(Option.SQL.getOption()))
        {
            errors.add("Must contains one of : " + Option.FILE + "and " + Option.SQL);
        }
        if (!arguments.containsKey(Option.FILE.getOption()) && !arguments.containsKey(Option.SQL.getOption()))
        {
            errors.add("Must contains one of : " + Option.FILE + " and " + Option.SQL);
        }

        if (!errors.isEmpty())
        {
            for (String error : errors)
            {
                println(error);
            }
            println();

            usage();
            System.exit(-1);
        }
    }

    private static Map<String, String> parseCommandLine(String[] args)
    {
        List<String> errors = new ArrayList<String>();
        Map<String, String> arguments = new HashMap<String, String>();
        for (int i = 0; i < args.length; i++)
        {
            Option option = Option.fromString(args[i]);
            if (option != null)
            {
                if (option.isWithArgument())
                {
                    if (i + 1 < args.length)
                    {
                        arguments.put(args[i], args[i + 1]);
                        i++;
                    }
                    else
                    {
                        errors.add("Option missing argument: " + args[i]);
                    }
                }
                else
                {
                    arguments.put(args[i], null);
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
                println(error);
            }
            println();

            usage();
            System.exit(-1);
        }

        return arguments;
    }
}