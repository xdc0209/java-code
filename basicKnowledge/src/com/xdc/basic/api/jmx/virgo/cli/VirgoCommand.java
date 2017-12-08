package com.xdc.basic.api.jmx.virgo.cli;

import java.util.Map;

import com.xdc.basic.api.jmx.virgo.cli.core.AbstractAtomCommand;
import com.xdc.basic.api.jmx.virgo.cli.core.AbstractDispatchCommand;

public class VirgoCommand extends AbstractDispatchCommand
{
    @Override
    protected void initSubcommmands(Map<String, AbstractAtomCommand> subcommands)
    {
        subcommands.put("plan", new PlanCommand());
        subcommands.put("bundle", new BundleCommand());
        subcommands.put("shutdown", new ShutdownCommand());
    }
}
