package com.xdc.basic.tools.markdown.formatter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.MutableDataSet;

public class MarkdownFormatter
{
    private static final MutableDataSet OPTIONS = new MutableDataSet();

    static
    {
        OPTIONS.set(Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE, false); // 对紧凑与宽松的判断不是很准确，禁用此项，自己实现相关功能。

        OPTIONS.set(Formatter.SETEXT_HEADER_EQUALIZE_MARKER, false); // 对中文字符支持不好，禁用此项。

        OPTIONS.set(Formatter.MAX_TRAILING_BLANK_LINES, 0); // 文件尾空行处理，0表示文件尾不保留空行。

        OPTIONS.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create())); // 启用表格扩展。
        OPTIONS.set(TablesExtension.FORMAT_FILL_MISSING_COLUMNS, true); // 如果表格的某行缺少列，则添加列，保证所有行列数相同。
        OPTIONS.set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() // 字符宽度计算，1个英文字母长度为1，1个汉字长度为2。
        {
            @Override
            public int spaceWidth()
            {
                return 1;
            }

            @Override
            public int charWidth(CharSequence s)
            {
                return s == null ? 0 : s.toString().replaceAll("[^\\u0000-\\u00FF]", "AA").length();
            }

            @Override
            public int charWidth(char c)
            {
                return ('\u0000' <= c && c <= '\u00FF') ? 1 : 2;
            }
        });
    }

    private static final Parser    PARSER   = Parser.builder(OPTIONS).build();

    private static final Formatter RENDERER = Formatter.builder(OPTIONS).build();

    public static String format(String markdown) throws IOException
    {
        // 解析。
        Node document = PARSER.parse(markdown);

        // 格式化。
        String markdownFormated = RENDERER.render(document);

        return markdownFormated;
    }

    public static void formatFile(String markdownFile) throws IOException
    {
        if (!System.getProperties().containsKey("MarkdownFormatter.QuietMode"))
        {
            System.out.println("Handling " + markdownFile);
        }

        // 读取文件。
        String markdown = new String(Files.readAllBytes(Paths.get(markdownFile)), Charset.forName("UTF-8"));

        // 解析文件。
        Node document = PARSER.parse(markdown);

        // 格式化文件。
        String markdownFormated = RENDERER.render(document);

        // 保存文件。
        Files.write(Paths.get(markdownFile), markdownFormated.getBytes(Charset.forName("UTF-8")));
    }

    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.printf("Usage 1: java -cp <your_class_path> %s -\n", MarkdownFormatter.class.getName());
            System.err.printf("Usage 2: java -cp <your_class_path> %s file\n", MarkdownFormatter.class.getName());
            System.err.printf("Usage 3: java -cp <your_class_path> %s dir\n", MarkdownFormatter.class.getName());
            System.exit(1);
        }

        String markdownSource = args[0];

        if ("-".equals(markdownSource))
        {
            String markdown = inputStreamToString(System.in);
            String markdownFormated = format(markdown);
            stringToOutputStream(markdownFormated, System.out);
        }
        else if (Files.isRegularFile(Paths.get(markdownSource)))
        {
            formatFile(markdownSource);
        }
        else if (Files.isDirectory(Paths.get(markdownSource)))
        {
            List<File> markdownFiles = listRegularFiles(new File(markdownSource));
            for (File markdownFile : markdownFiles)
            {
                if (markdownFile.getPath().endsWith(".md"))
                {
                    formatFile(markdownFile.getPath());
                }
            }
        }
        else
        {
            System.err
                    .println("Markdown source [ " + markdownSource + " ] is not a stdin or regular file or directory.");
            System.exit(1);
        }
    }

    /**
     * 参考：org.apache.commons.io.FileUtils.listFiles(File, IOFileFilter, IOFileFilter)
     */
    private static List<File> listRegularFiles(File directory)
    {
        List<File> files = new LinkedList<File>();
        listRegularFiles(directory, files);
        return files;
    }

    /**
     * 参考：org.apache.commons.io.FileUtils.innerListFiles(Collection<File>, File, IOFileFilter, boolean)
     */
    private static void listRegularFiles(File directory, List<File> files)
    {
        File[] found = directory.listFiles();

        // directory文件不存在或者directory文件为普通文件时，结果为空。
        if (found == null)
        {
            return;
        }

        for (File file : found)
        {
            if (file.isDirectory())
            {
                listRegularFiles(file, files);
            }
            else
            {
                files.add(file);
            }
        }
    }

    private static String inputStreamToString(InputStream is) throws IOException
    {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bufferUsed = 0;
        while ((bufferUsed = bis.read(buffer, 0, buffer.length)) > 0)
        {
            baos.write(buffer, 0, bufferUsed);
        }

        return baos.toString("UTF-8");
    }

    private static void stringToOutputStream(String string, OutputStream os) throws IOException
    {
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        os.write(bytes);
        os.flush();
    }
}
