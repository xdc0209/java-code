package com.xdc.basic.api.args.args4j.randombasedonargs4j2029.subcommand;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import com.xdc.basic.api.args.args4j.randombasedonargs4j2029.subcommand.sqlcommand.MySqlCommand;
import com.xdc.basic.api.args.args4j.randombasedonargs4j2029.subcommand.sqlcommand.OracleCommand;

public class SqlCommand extends AbstractCommand
{
    @SubCommands({ @SubCommand(name = "mysql", impl = MySqlCommand.class),
            @SubCommand(name = "oracle", impl = OracleCommand.class) })
    @Argument(handler = SubCommandHandler.class, usage = "SqlCommand <subcommand> [options] [args]", metaVar = "<subcommand>")
    AbstractCommand command;

    @Override
    protected void excute(CmdLineParser parser) throws CmdLineException
    {
        command.complexArgsCheck(parser);
        command.excute(parser);
    }

    public AbstractCommand getCommand()
    {
        return command;
    }

    public void setCommand(AbstractCommand command)
    {
        this.command = command;
    }
}
