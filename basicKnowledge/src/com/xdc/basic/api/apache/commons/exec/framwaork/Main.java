package com.xdc.basic.api.apache.commons.exec.framwaork;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ExecCommand execCommand = new ExecCommand("ping -n 20 127.0.0.1");
            System.out.println(execCommand);

            ExecResult execResult = ExecTool.exec(execCommand);
            System.out.println(execResult);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        // 测试简单命令：ping -c 10 127.0.0.1 ok
        // 测试管道：ps -ef | grep java ok
        // 测试双引号：ps -ef | grep "java" ok
        // 测试单引号：ps -ef | grep 'java' ok
        // 测试当前目录：pwd ok
        // 测试输入流：touch xdc.log && rm -i xdc.log execCommand.setStdIn("y\n"); ok
        // 测试环境变量：echo $PATH ok
        // 测试超时： ok
        // 测试期待的返回值： ok
        // windows测试简单命令： ok
    }
}
