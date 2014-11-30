package com.xdc.basic.api.args.args4j.randombasedonargs4j2021.subcommand;

import com.xdc.basic.api.args.args4j.randombasedonargs4j2021.subcommand.core.AbstractDispatchCommand;
import com.xdc.basic.api.args.args4j.randombasedonargs4j2021.subcommand.sqlcommand.MySqlCommand;
import com.xdc.basic.api.args.args4j.randombasedonargs4j2021.subcommand.sqlcommand.OracleCommand;

public class SqlCommand extends AbstractDispatchCommand
{
    protected void initSubcommmands()
    {
        subcommands.put("mysql", MySqlCommand.class);
        subcommands.put("oracle", OracleCommand.class);
    }
}
