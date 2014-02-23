package com.xdc.basic.skills.getmapfromlist2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List转换成Map
 * 
 * @author xdc
 * 
 */
public class GetMapFromList
{
    public static void main(String[] args)
    {
        List<Student> tallStudents = new ArrayList<Student>();
        tallStudents.add(new Student("20082889", "wangziwei"));
        tallStudents.add(new Student("20082890", "xudachao"));

        Map<String, Student> map = getMapFromList(tallStudents, new Func<Student, String>()
        {
            @Override
            public String apply(Student v)
            {
                return v.getNumber();
            }
        });

        System.out.println(map);
    }

    private static <K, V> Map<K, V> getMapFromList(List<V> list, Func<V, K> func)
    {
        Map<K, V> result = new HashMap<K, V>();

        for (V v : list)
        {
            result.put(func.apply(v), v);
        }
        return result;
    }

    private interface Func<V, K>
    {
        public K apply(V v);
    }
}
