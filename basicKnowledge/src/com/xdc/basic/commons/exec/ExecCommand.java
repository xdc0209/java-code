package com.xdc.basic.commons.exec;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExecCommand
{
    /**
     * 执行的命令，支持管道和重定向。
     */
    private String              command;

    /**
     * 命令行调用的标准输入流。比如删除文件要确认，测试命令：touch xdc.log && rm -i xdc.log
     * 注意：在linux上，如果命令行进程本身不需要输入(即默认的标准输入流未打开)，但是又在本处指定输入，当命令行进程执行完成后，java会flush输入到命令进程，导致抛出异常。windows无此问题。
     */
    private String              stdIn                      = null;

    /**
     * 设置执行目录。一般是默认当前目录就可以了，特殊场景下需要切换到其他目录执行。
     */
    private File                workingDirectory           = new File(".");

    /**
     * 可以定制环境变量。留空的话，默认继承java程序的环境变量。
     */
    private Map<Object, Object> environment                = null;

    /**
     * 超时时间，单位毫秒。超时后，再等待10秒钟，杀死命令行进程，否则会残留，浪费资源。等10秒的原因是为了区分正常返回和执行超时。
     * 因为杀死进程后，进程也是有返回值的，看起来与执行完成没有区别。虽然执行成功（惯例是0）、失败、超时被杀死的返回码不同，也是不能根据返回值判断是否超时的，那样代码就跟操作系统的耦合了。
     * 设置为null的话，禁用超时功能。
     */
    private Long                timeout                    = 60 * 1000L;

    /**
     * 当jvm退出时，是否杀死正在执行的命令行进程。
     */
    private boolean             destroyCmdProcessOnJvmExit = true;

    /**
     * 期待的返回值列表，只有返回值包含在此列表中，才认为此次调用成功。根据惯例，值0表示正常终止。此处提供定制能力，以适应一些设计古怪的可执行命令。
     */
    private int[]               expectedExitValues         = new int[] { 0 };

    public ExecCommand(String command)
    {
        super();
        this.command = command;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public String getStdIn()
    {
        return stdIn;
    }

    public void setStdIn(String stdIn)
    {
        this.stdIn = stdIn;
    }

    public File getWorkingDirectory()
    {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory)
    {
        this.workingDirectory = workingDirectory;
    }

    public Map<Object, Object> getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(Map<Object, Object> environment)
    {
        this.environment = environment;
    }

    public Long getTimeout()
    {
        return timeout;
    }

    public void setTimeout(Long timeout)
    {
        if (timeout != null && timeout < 10 * 1000)
        {
            throw new IllegalArgumentException(String.format(
                    "Timeout[%s] is not vaild. Timeout must be greater than 10000 or equal to 10000 or null.",
                    timeout));
        }

        this.timeout = timeout;
    }

    public boolean isDestroyCmdProcessOnJvmExit()
    {
        return destroyCmdProcessOnJvmExit;
    }

    public void setDestroyCmdProcessOnJvmExit(boolean destroyCmdProcessOnJvmExit)
    {
        this.destroyCmdProcessOnJvmExit = destroyCmdProcessOnJvmExit;
    }

    public int[] getExpectedExitValues()
    {
        return expectedExitValues;
    }

    public void setExpectedExitValues(int[] expectedExitValues)
    {
        this.expectedExitValues = (expectedExitValues == null ? null : (int[]) expectedExitValues.clone());
    }

    public void setExpectedExitValue(int expectedExitValue)
    {
        this.setExpectedExitValues(new int[] { expectedExitValue });
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
