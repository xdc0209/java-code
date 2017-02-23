package com.xdc.basic.tools.restframework.caller;

import com.xdc.basic.tools.restframework.core.MethodType;
import com.xdc.basic.tools.restframework.core.RestMethod;
import com.xdc.basic.tools.restframework.message.music.get.GetMusicResponse;
import com.xdc.basic.tools.restframework.message.music.post.AddMusicRequest;
import com.xdc.basic.tools.restframework.message.music.post.AddMusicResponse;
import com.xdc.basic.tools.restframework.message.music.put.ModifyMusicRequst;
import com.xdc.basic.tools.restframework.message.music.put.ModifyMusicResponse;

/*
 * 测试链接：http://v5.pc.duomi.com/search-ajaxsearch-searchall?kw=关键字&pi=页码&pz=每页音乐数
 */
public interface MusicCaller
{
    // Restful消息规范：1. POST创建 PUT修改 2. get方法不允許有body体
    // 但是在项目中，一般delete方法也没有body体，为了框架的易用性，delete方法不支持有body体

    @RestMethod(method = MethodType.GET, url = "/search-ajaxsearch-searchall?kw={关键字}&pi={页码}&pz={每页音乐数}")
    GetMusicResponse getMusic(String keyWord, int pageNum, int pageSize);

    @RestMethod(method = MethodType.POST, url = "/XXX?YYY")
    AddMusicResponse AddMusic(AddMusicRequest request, String... args);

    @RestMethod(method = MethodType.PUT, url = "/XXX?YYY")
    ModifyMusicResponse ModifyMusic(ModifyMusicRequst request, String... args);

    @RestMethod(method = MethodType.DELETE, url = "/XXX?YYY")
    ModifyMusicResponse DeleteMusic(String... args);
}
