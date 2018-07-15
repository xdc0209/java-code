package com.xdc.basic.tools.statemachine.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.xdc.basic.commons.PathUtil;
import com.xdc.basic.tools.statemachine.SMException;
import com.xdc.basic.tools.statemachine.StateMachine;
import com.xdc.basic.tools.statemachine.StateMachineFrameWork;
import com.xdc.basic.tools.statemachine.test.tsuit.E1Arg;
import com.xdc.basic.tools.statemachine.xml.Event;

public class StateMachineFrameWorkTest
{
    @Test
    public void testBasicCreateSM()
    {
        try
        {
            String curPath = PathUtil.getRelativePath();
            File baseFile = FileUtils.getFile(curPath + "base.xml");
            String baseString = FileUtils.readFileToString(baseFile, Charsets.UTF_8);

            StateMachine sm = StateMachineFrameWork.createStateMachine(baseString);
            Assert.assertNotNull(sm);

            List<String> extQueryNextState = Lists.newArrayList("S1", "S2");
            List<String> rstQueryNextState = sm.queryNextState("S0");
            StateMachineFrameWorkTestHelper.assertStringListEqual(extQueryNextState, rstQueryNextState);

            Event event = new Event();
            event.setName("e1");

            E1Arg e1Arg = new E1Arg();
            e1Arg.setArg("OK");
            event.setArg(e1Arg);

            String extNxtState = "S1";
            String rstNxtState = sm.dealEvent(event);
            Assert.assertEquals(extNxtState, rstNxtState);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Assert.fail("error");
        }
        catch (SMException e)
        {
            e.printStackTrace();
            Assert.fail("error");
        }
    }
}
