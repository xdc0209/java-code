package com.xdc.basic.tools.restclient.other;

import java.io.IOException;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.xdc.basic.tools.restclient.constants.Constants;
import com.xdc.basic.tools.restclient.constants.HttpMethod;
import com.xdc.basic.tools.restclient.message.Request;
import com.xdc.basic.tools.restclient.message.RestClientException;
import com.xdc.basic.tools.restclient.tools.JsonTool;

public class RequestHelper
{
    @Test
    public void saveReqest() throws IOException, RestClientException
    {
        Request req = new Request();
        req.setMethod(HttpMethod.GET.toString());
        req.setUrl("/resoure/vm");
        req.setBodyType(Constants.BodyType.json);
        req.setBody("");

        String reqStr = JsonTool.toJSONString(req);

        String reqFile = "demo.req";
        FileUtils.writeStringToFile(FileUtils.getFile(reqFile), reqStr, Charsets.UTF_8, false);
    }

    @Test
    public void readReqest() throws IOException, RestClientException
    {
        String reqFile = "demo.req";
        String reqStr = FileUtils.readFileToString(FileUtils.getFile(reqFile), Charsets.UTF_8);

        Request req = JsonTool.parse(reqStr, Request.class);
        System.out.println(req);
    }
}
