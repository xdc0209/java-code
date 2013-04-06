package com.xdc.basic.skills;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找指定元素，并移到最后
 * 
 * @author xdc
 * 
 */
public class FineAndMove
{
	public static void main(String[] args)
	{
		List<String> names = new ArrayList<String>();
		names.add("xdc");
		names.add("gx");
		names.add("cc");
		String me = null;

		System.out.println(names);
		for (String name : names)
		{
			if (name.equals("xdc"))
			{
				me = name;
				break;
			}
		}

		if (me != null)
		{
			names.remove(me);
			names.add(me);
		}

		System.out.println(names);
	}
}
