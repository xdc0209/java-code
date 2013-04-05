package com.xdc.basic.skills.findprime;

import java.util.ArrayList;

public class Element
{
	ArrayList<Integer>	primeArrayList;
	boolean	           isAlive;
	int	               beginNum;
	int	               endNum;

	public Element(int beginNum, int endNum)
	{
		this.beginNum = beginNum;
		this.endNum = endNum;
		this.primeArrayList = new ArrayList<Integer>();
		this.isAlive = true;
	}
}
