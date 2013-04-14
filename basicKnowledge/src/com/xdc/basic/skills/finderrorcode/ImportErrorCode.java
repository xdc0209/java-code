package com.xdc.basic.skills.finderrorcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ImportErrorCode
{

	public static void main(String[] args)
	{
		ArrayList<ErrorCodeElement> errorCodeElementsArrayList = new ArrayList<ErrorCodeElement>();

		// 抽取每个枚举中的错误码
		for (ErrorEnums err : ErrorEnums.values())
		{
			String errorString = err.getName() + " ---- " + err.getClass();
			int errorValue = err.getCode();
			errorCodeElementsArrayList.add(new ErrorCodeElement(errorString, errorValue));
		}

		// 将结果排序
		Collections.sort(errorCodeElementsArrayList, new Comparator<ErrorCodeElement>()
		{
			public int compare(ErrorCodeElement arg0, ErrorCodeElement arg1)
			{
				return arg0.getErrorValue() - arg1.getErrorValue();
			}
		});

		// 显示结果
		for (int i = 0; i < errorCodeElementsArrayList.size(); i++)
		{
			System.out.println(errorCodeElementsArrayList.get(i));
		}
	}

}
