package com.xdc.basic.algorithm.basic.search.哈希表;

import java.util.HashMap;
import java.util.Map;

public class HashTable
{
    private Map<String, String> map = new HashMap<String, String>();

    public String get(String key)
    {
        return map.get(key);
    }

    public void put(String key, String value)
    {
        map.put(key, value);
    }

    public static void main(String[] args)
    {
        HashTable hashTable = new HashTable();
        hashTable.put("5", "5");
        hashTable.put("1", "1");
        hashTable.put("4", "4");
        hashTable.put("7", "7");
        hashTable.put("3", "3");
        hashTable.put("9", "9");

        System.out.println("4：" + hashTable.get("4"));
        System.out.println("8：" + hashTable.get("8"));
    }
}
