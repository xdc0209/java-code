package com.xdc.basic.apidemo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLTest
{
	public static void main(String[] args)
	{
		String url = "name = xdc";
		System.out.println(url);

		String en = encodeUrl(url);
		System.out.println(en);

		String de = decode(en);
		System.out.println(de);
	}

	public static String encodeUrl(String url)
	{
		try
		{
			return URLEncoder.encode(url, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return url;
		}
	}

	public static String decode(String url)
	{
		try
		{
			return URLDecoder.decode(url, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return url;
		}
	}
}
