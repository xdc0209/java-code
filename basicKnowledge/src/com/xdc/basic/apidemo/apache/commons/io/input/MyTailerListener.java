package com.xdc.basic.apidemo.apache.commons.io.input;

import org.apache.commons.io.input.TailerListenerAdapter;

public class MyTailerListener extends TailerListenerAdapter
{
	public void handle(String line)
	{
		System.out.println(line);
	}
}