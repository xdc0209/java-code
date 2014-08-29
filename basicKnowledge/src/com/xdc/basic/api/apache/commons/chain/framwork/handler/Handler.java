package com.xdc.basic.api.apache.commons.chain.framwork.handler;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Handler implements Command
{
    @Override
    public boolean execute(Context context) throws Exception
    {
        return Command.PROCESSING_COMPLETE;
    }
}
