package com.xdc.basic.skills.findrepeatline;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.commons.PathUtil;

public class FindRepeatLine
{
    public static void main(String[] args) throws IOException
    {
        String curPath = PathUtil.getRelativePath();

        FileReader input = new FileReader(curPath + "data.txt");
        List<String> lines = IOUtils.readLines(input);
        input.close();

        Map<String, List<Integer>> lineNumberMap = new LinkedHashMap<String, List<Integer>>();

        Integer lineNumber = 1;
        for (String line : lines)
        {
            if (StringUtils.isEmpty(line))
            {
                continue;
            }

            List<Integer> lineNumberList = lineNumberMap.get(line);
            if (lineNumberList == null)
            {
                lineNumberList = new ArrayList<Integer>();
                lineNumberMap.put(line, lineNumberList);
            }

            lineNumberList.add(lineNumber);
            lineNumber++;
        }

        for (Entry<String, List<Integer>> entry : lineNumberMap.entrySet())
        {
            String line = entry.getKey();
            List<Integer> lineNumberList = entry.getValue();

            if (lineNumberList.size() > 1)
            {
                System.out.println("Content: [" + line + "]");
                System.out.println("Line: " + lineNumberList);
                System.out.println();
            }
        }
    }
}
