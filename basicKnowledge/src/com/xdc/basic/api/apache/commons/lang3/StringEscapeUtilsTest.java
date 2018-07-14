package com.xdc.basic.api.apache.commons.lang3;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringEscapeUtilsTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        String java = "He didn't say, \"Stop!\"";
        String javaScript = "He didn't say, \"Stop!\"";
        String xml = "\"bread\" & \"butter\"";
        String html4 = "\"bread\" & \"butter\"";

        String escapeJava = StringEscapeUtils.escapeJava(java);
        String escapeEcmaScript = StringEscapeUtils.escapeEcmaScript(javaScript); // EcmaScript，即JavaScript
        String escapeXml = StringEscapeUtils.escapeXml(xml);
        String escapeHtml4 = StringEscapeUtils.escapeHtml4(html4);

        String unescapeJava = StringEscapeUtils.unescapeJava(escapeJava);
        String unescapeEcmaScript = StringEscapeUtils.unescapeEcmaScript(escapeEcmaScript);
        String unescapeXml = StringEscapeUtils.unescapeXml(escapeXml);
        String unescapeHtml4 = StringEscapeUtils.unescapeHtml4(escapeHtml4);

        // 其实上面的方法内部调用下面的方法进行实现的
        // StringEscapeUtils.ESCAPE_CSV.translate(input);
        // StringEscapeUtils.ESCAPE_ECMASCRIPT.translate(input);
        // StringEscapeUtils.ESCAPE_HTML3.translate(input);
        // StringEscapeUtils.ESCAPE_HTML4.translate(input);
        // StringEscapeUtils.ESCAPE_JAVA.translate(input);
        // StringEscapeUtils.ESCAPE_XML.translate(input);
        //
        // StringEscapeUtils.UNESCAPE_CSV.translate(input);
        // StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate(input);
        // StringEscapeUtils.UNESCAPE_HTML3.translate(input);
        // StringEscapeUtils.UNESCAPE_HTML4.translate(input);
        // StringEscapeUtils.UNESCAPE_JAVA.translate(input);
        // StringEscapeUtils.UNESCAPE_XML.translate(input);

        System.out.println();
    }
}
