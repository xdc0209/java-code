package com.xdc.basic.api.args.args4j.subcommand.sqlcommand;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import com.xdc.basic.api.args.args4j.subcommand.core.AbstractAtomCommand;

public class OracleCommand extends AbstractAtomCommand
{
    @Option(name = "-f", aliases = { "--file" }, usage = "input file", metaVar = "<file>")
    private File    file;

    @Option(name = "-s", aliases = { "--sql" }, usage = "sql string", metaVar = "<sql>")
    private String  sql;

    @Option(name = "-H", aliases = { "--Host" }, usage = "database host or ip", metaVar = "<Host>", required = true)
    private String  host;

    @Option(name = "-P", aliases = { "--Port" }, usage = "database port", metaVar = "<Port>", required = true)
    private String  port;

    @Option(name = "-S", aliases = { "--Sid" }, usage = "database sid", metaVar = "<Sid>", required = true)
    private String  sid;

    @Option(name = "-u", aliases = { "--user" }, usage = "database user", metaVar = "<user>", required = true)
    private String  user;

    @Option(name = "-p", aliases = {
            "--password" }, usage = "database password", metaVar = "<password>", required = true)
    private String  password;

    @Option(name = "-d", aliases = { "--dba" }, usage = "use dba role", handler = BooleanOptionHandler.class)
    private boolean dba = false;

    @Override
    protected void complexArgsCheck(CmdLineParser parser) throws CmdLineException
    {
        super.complexArgsCheck(parser);

        if (file == null && sql == null)
        {
            throw new CmdLineException(parser, "Must contains one of : -f -s");
        }
        if (file != null && sql != null)
        {
            throw new CmdLineException(parser, "Must contains one of : -f -s");
        }
    }

    @Override
    protected void excute()
    {
        // 不实现具体逻辑，仅用来展示子命令解析
        System.out.println(toString());
    }

    @Override
    public String toString()
    {
        return String.format(
                "OracleCommand [file=%s, sql=%s, host=%s, port=%s, sid=%s, user=%s, password=%s, multiLine=%s]", file,
                sql, host, port, sid, user, password, dba);
    }
}
