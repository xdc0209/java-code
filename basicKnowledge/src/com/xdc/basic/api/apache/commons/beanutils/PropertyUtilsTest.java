package com.xdc.basic.api.apache.commons.beanutils;

public class PropertyUtilsTest
{
	// 除BeanUtils外还有一个名为PropertyUtils的工具类，它也提供copyProperties()方法，
	// 作用与BeanUtils的同名方法十分相似，主要的区别在于后者提供类型转换功能，
	// 即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换，
	// 而前者不支持这个功能，但是速度会更快一些。BeanUtils支持的转换类型如下：
	//
	// java.lang.BigDecimal
	// java.lang.BigInteger
	// boolean and java.lang.Boolean
	// byte and java.lang.Byte
	// char and java.lang.Character
	// java.lang.Class
	// double and java.lang.Double
	// float and java.lang.Float
	// int and java.lang.Integer
	// long and java.lang.Long
	// short and java.lang.Short
	// java.lang.String
	// java.sql.Date
	// java.sql.Time
	// java.sql.Timestamp
	//
	// 这里要注意一点，java.util.Date是不被支持的，而它的子类java.sql.Date是被支持的。
	// 因此如果对象包含时间类型的属性，且希望被转换的时候，一定要使用java.sql.Date类型。
	// 否则在转换时会提示argument mistype异常。

	public static void main(String[] args)
	{
		// 大部分与BeanUtil类似，用时再研究
	}
}
