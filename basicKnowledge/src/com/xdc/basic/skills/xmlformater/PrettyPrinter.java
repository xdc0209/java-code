package com.xdc.basic.skills.xmlformater;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class PrettyPrinter
{
	public static void main(String[] args)
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		try
		{
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			Document doc = builder.build(new File(curPath + "users.xml"));
			// Output the document, use standard formatter
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(doc, System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}