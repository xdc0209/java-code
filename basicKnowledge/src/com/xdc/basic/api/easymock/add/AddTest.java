package com.xdc.basic.api.easymock.add;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

public class AddTest
{
    @Test
    public void testAdd()
    {
        // 1、创建mock对象，以接口形式创建
        Add add = EasyMock.createMock(Add.class);

        // 2、设定参预期和返回，查询预期值得到所设定的预期结果
        EasyMock.expect(add.add(1, 1)).andReturn(2);
        EasyMock.expect(add.add(1, 2)).andReturn(3);

        // 3、结束录制
        EasyMock.replay(add);

        assertEquals(2, add.add(1, 1));
        assertEquals(3, add.add(1, 2));

        // 4、回放录制
        EasyMock.verify(add);
    }
}