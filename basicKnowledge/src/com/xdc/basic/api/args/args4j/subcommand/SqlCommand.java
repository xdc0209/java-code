package com.xdc.basic.api.args.args4j.subcommand;

import java.util.Map;

import com.xdc.basic.api.args.args4j.subcommand.core.AbstractAtomCommand;
import com.xdc.basic.api.args.args4j.subcommand.core.AbstractDispatchCommand;
import com.xdc.basic.api.args.args4j.subcommand.sqlcommand.MySqlCommand;
import com.xdc.basic.api.args.args4j.subcommand.sqlcommand.OracleCommand;

public class SqlCommand extends AbstractDispatchCommand
{
    protected void initSubcommmands(Map<String, Class<? extends AbstractAtomCommand>> subcommands)
    {
        subcommands.put("mysql", MySqlCommand.class);
        subcommands.put("oracle", OracleCommand.class);
    }
}
