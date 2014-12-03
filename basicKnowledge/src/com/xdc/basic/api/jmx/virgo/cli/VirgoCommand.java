package com.xdc.basic.api.jmx.virgo.cli;

import com.xdc.basic.api.jmx.virgo.cli.core.AbstractDispatchCommand;

public class VirgoCommand extends AbstractDispatchCommand
{
    @Override
    protected void initSubcommmands()
    {
        subcommands.put("plan", PlanCommand.class);
        subcommands.put("bundle", BundleCommand.class);
    }
}
