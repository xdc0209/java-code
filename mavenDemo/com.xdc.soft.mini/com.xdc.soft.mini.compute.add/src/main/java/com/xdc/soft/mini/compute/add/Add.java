package com.xdc.soft.mini.compute.add;

import com.xdc.soft.mini.compute.Compute;

public class Add implements Compute
{
    @Override
    public String computeNmus(int x, int y)
    {
        return String.valueOf(x + y);
    }
}
