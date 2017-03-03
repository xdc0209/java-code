package com.xdc.basic.api.apache.commons.exec.framwaork;

public class Main
{
    // 测试简单命令：ping -c 10 127.0.0.1 ok
    // 测试管道：ps -ef | grep java ok
    // 测试双引号：ps -ef | grep "java" ok
    // 测试单引号：ps -ef | grep 'java' ok
    // 测试当前目录：pwd ok
    // 测试输入流：execCommand.setStdIn("y\n"); touch xdc.log && rm -i xdc.log ok
    // 测试环境变量：echo $PATH ok
    // 测试超时：execCommand.setTimeout(10000); ok
    // 测试期待的返回值： execCommand.setExpectedExitValues(new int[] { 0, 1, 2 }); ok
    // windows测试管道： ping -n 5 127.0.0.1 | findstr TTL ok
    public static void main(String[] args)
    {
        try
        {
            ExecCommand execCommand = new ExecCommand("ping -n 5 127.0.0.1 | findstr TTL");
            System.out.println(execCommand);

            execCommand.setTimeout(10000);
            execCommand.setExpectedExitValues(new int[] { 0, 1, 2 });
            ExecResult execResult = ExecTool.exec(execCommand);
            System.out.println(execResult);

            if (execResult.isSuccess())
            {
                System.out.println("命令执行成功。");
            }
            else
            {
                System.out.println("命令执行失败。");
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
