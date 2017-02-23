package com.xdc.basic.tools.restframework;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xdc.basic.tools.restframework.caller.CallerFactory;
import com.xdc.basic.tools.restframework.caller.IpCaller;
import com.xdc.basic.tools.restframework.caller.MusicCaller;
import com.xdc.basic.tools.restframework.core.CallerFrameWork;
import com.xdc.basic.tools.restframework.message.ip.GetIpResponse;
import com.xdc.basic.tools.restframework.message.music.get.GetMusicResponse;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            CallerFrameWork.init("v5.pc.duomi.com");
            MusicCaller musicCaller = CallerFactory.getCaller(MusicCaller.class);
            GetMusicResponse music = musicCaller.getMusic("羽泉", 2, 5);
            System.out.println(ReflectionToStringBuilder.toString(music, ToStringStyle.MULTI_LINE_STYLE));
            CallerFrameWork.destroy();

            CallerFrameWork.init("int.dpool.sina.com.cn");
            IpCaller ipCaller = CallerFactory.getCaller(IpCaller.class);
            GetIpResponse ip = ipCaller.getIp("218.4.255.255");
            System.out.println(ReflectionToStringBuilder.toString(ip, ToStringStyle.MULTI_LINE_STYLE));
            CallerFrameWork.destroy();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
