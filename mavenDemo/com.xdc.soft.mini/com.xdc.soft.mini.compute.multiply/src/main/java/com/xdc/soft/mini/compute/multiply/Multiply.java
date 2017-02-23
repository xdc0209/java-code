package com.xdc.soft.mini.compute.multiply;

import com.xdc.soft.mini.compute.Compute;

public class Multiply implements Compute
{
    @Override
    public String computeNmus(int x, int y)
    {
        return String.valueOf(x * y);
    }
}
