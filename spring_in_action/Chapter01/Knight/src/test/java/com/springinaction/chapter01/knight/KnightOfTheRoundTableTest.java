package com.springinaction.chapter01.knight;
import com.springinaction.chapter01.knight.HolyGrail;
import com.springinaction.chapter01.knight.HolyGrailQuest;
import com.springinaction.chapter01.knight.KnightOfTheRoundTable;
import com.springinaction.chapter01.knight.QuestFailedException;

import junit.framework.TestCase;

public class KnightOfTheRoundTableTest extends TestCase {
  public void testEmbarkOnQuest() throws GrailNotFoundException {
    KnightOfTheRoundTable knight = 
        new KnightOfTheRoundTable("Bedivere");
    
    knight.setQuest(new HolyGrailQuest());
    
    try {
      HolyGrail grail = (HolyGrail) knight.embarkOnQuest();
      
      assertNotNull(grail);
      
      assertTrue(grail.isHoly());
    } catch (QuestFailedException e) {
      fail();
    }
  }
}
