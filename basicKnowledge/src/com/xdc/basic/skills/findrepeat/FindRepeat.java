package com.xdc.basic.skills.findrepeat;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.skills.GetPath;

public class FindRepeat
{
    public static void main(String[] args) throws IOException
    {
        String curPath = GetPath.getRelativePath();

        FileReader input = new FileReader(curPath + "site.txt");
        List<String> lines = IOUtils.readLines(input);
        input.close();

        Map<String, List<Integer>> cacheMap = new LinkedHashMap<String, List<Integer>>();

        Integer lineNumber = 1;
        for (String line : lines)
        {
            List<Integer> list = cacheMap.get(line);
            if (list == null)
            {
                list = new ArrayList<Integer>();
                cacheMap.put(line, list);
            }

            list.add(lineNumber);
            lineNumber++;
        }

        for (Entry<String, List<Integer>> entry : cacheMap.entrySet())
        {
            String line = entry.getKey();
            List<Integer> list = entry.getValue();

            if (list.size() > 1)
            {
                System.out.println("Content: [" + line + "]");
                System.out.println("Line: " + list);
                System.out.println();
            }

        }
    }
}
