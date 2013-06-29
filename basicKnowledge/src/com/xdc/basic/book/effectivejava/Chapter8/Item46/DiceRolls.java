package com.xdc.basic.book.effectivejava.Chapter8.Item46;

// Same bug as NestIteration.java (but different symptom)!! - Page 213

import java.util.*;

enum Face { ONE, TWO, THREE, FOUR, FIVE, SIX }

public class DiceRolls {
    public static void main(String[] args) {
        Collection<Face> faces = Arrays.asList(Face.values());

        for (Iterator<Face> i = faces.iterator(); i.hasNext(); )
            for (Iterator<Face> j = faces.iterator(); j.hasNext(); )
                System.out.println(i.next() + " " + j.next());

        // Preferred idiom for nested iteration on collections and arrays
//      for (Face face1 : faces)
//          for (Face face2 : faces)
//              System.out.println(face1 + " " + face2);
    }
}
