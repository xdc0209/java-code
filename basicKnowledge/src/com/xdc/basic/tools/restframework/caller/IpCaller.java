package com.xdc.basic.tools.restframework.caller;

import com.xdc.basic.tools.restframework.core.MethodType;
import com.xdc.basic.tools.restframework.core.RestMethod;
import com.xdc.basic.tools.restframework.message.ip.GetIpResponse;

/*
 * 测试链接：http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255
 */
public interface IpCaller
{
    @RestMethod(method = MethodType.GET, url = "/iplookup/iplookup.php?format=json&ip={ip}")
    GetIpResponse getIp(String ip);

    // POST   参见MusicCaller

    // PUT    参见MusicCaller

    // DELETE 参见MusicCaller
}
