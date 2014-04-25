package com.xdc.basic.tools.restclient;

import com.xdc.basic.tools.restclient.constants.HttpMethod;
import com.xdc.basic.tools.restclient.message.Request;
import com.xdc.basic.tools.restclient.message.Response;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Request req = new Request();
            req.setMethod(HttpMethod.GET.toString());
            req.setUrl("/data/sk/101010100.html");
            System.out.println("Send rest requst: " + req);

            RestClient restClient = RestClientFactory.createHttpRestClient("www.weather.com.cn", null, null, null);

            Response rsp = restClient.handldeRequset(req);
            System.out.println("Receive rest response: " + rsp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
