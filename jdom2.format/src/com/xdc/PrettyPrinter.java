package com.xdc;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class PrettyPrinter
{
	public static void main(String[] args)
	{
		String filename = "users.xml";
		try
		{
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			Document doc = builder.build(new File(filename));
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