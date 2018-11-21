package com.xdc.basic.api.socket.tcp.demo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 8080);

        OutputStream outputStream = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 依次写入两次数据，我们期望对方接收到的也是两次(1234、5678)，如果对方只接受到一次数据，那么就发生了粘包。
        // 参见1：https://blog.csdn.net/gengbaolong/article/details/75450208
        // 参见2：http://blog.51cto.com/zhangfengzhe/1890577
        // 其实，粘包和拆包都是正常现象，TCP是个“流”协议，所谓流，就是没有界限的一串数据。TCP底层并不了解应用层数据的具体含义，TCP会根据TCP缓冲区的实际情况进行包的划分，所以在应用层认为，一个完整的包可能会被TCP拆分成多个包进行发送，也有可能把多个小的包封装成一个大的数据包发送。这就是TCP所谓的拆包和粘包的问题。
        outputStream.write("1234".getBytes());
        outputStream.write("5678".getBytes());

        outputStream.close();
        reader.close();

        socket.close();
    }
}
