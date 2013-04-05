package com.xdc.basic.skills.finderrorcode;

public class ErrorCodeElement
{
	private String	errorString;

	private int	   errorValue;

	public String getErrorString()
	{
		return errorString;
	}

	public void setErrorString(String errorString)
	{
		this.errorString = errorString;
	}

	public int getErrorValue()
	{
		return errorValue;
	}

	public void setErrorValue(int errorValue)
	{
		this.errorValue = errorValue;
	}

	public ErrorCodeElement(String errorString, int errorValue)
	{
		super();
		this.errorString = errorString;
		this.errorValue = errorValue;
	}

	@Override
	public String toString()
	{
		return errorValue + "    " + errorString;
	}
}
