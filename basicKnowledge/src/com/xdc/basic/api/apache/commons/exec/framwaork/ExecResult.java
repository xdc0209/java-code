package com.xdc.basic.api.apache.commons.exec.framwaork;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExecResult
{
    /**
     * 命令行调用框架异常，出现此返回值，需要考虑修改框架缺陷。
     */
    public static final int EXEC_FAILURE       = -1;

    /**
     * 命令行调用超时，出现此返回值，需要排查脚本和可执行命令中是否有死循环等错误。
     */
    public static final int EXEC_TIMEOUT       = -2;

    /**
     * 期待的返回值列表，只有返回值包含在此列表中，才认为此次调用成功。根据惯例，值0表示正常终止。此处提供定制能力，以适应一些设计古怪的可执行命令。
     */
    private int[]           expectedExitValues = new int[] { 0 };

    /**
     * 命令行调用的返回值
     */
    private int             exitValue;

    /**
     * 命令行调用的标准输出流
     */
    private String          stdOut;

    /**
     * 命令行调用的标准错误流
     */
    private String          stdErr;

    /**
     * 命令行调用框架异常。
     */
    private Throwable       throwable;

    public ExecResult(int[] expectedExitValues, int exitValue, String stdOut, String stdErr)
    {
        this(expectedExitValues, exitValue, stdOut, stdErr, null);
    }

    public ExecResult(int[] expectedExitValues, int exitValue, String stdOut, String stdErr, Throwable throwable)
    {
        super();
        this.expectedExitValues = expectedExitValues;
        this.exitValue = exitValue;
        this.stdOut = stdOut;
        this.stdErr = stdErr;
        this.throwable = throwable;
    }

    public int[] getExpectedExitValues()
    {
        return expectedExitValues;
    }

    public void setExpectedExitValues(int[] expectedExitValues)
    {
        this.expectedExitValues = expectedExitValues;
    }

    public int getExitValue()
    {
        return exitValue;
    }

    public void setExitValue(int exitValue)
    {
        this.exitValue = exitValue;
    }

    public String getStdOut()
    {
        return stdOut;
    }

    public void setStdOut(String stdOut)
    {
        this.stdOut = stdOut;
    }

    public String getStdErr()
    {
        return stdErr;
    }

    public void setStdErr(String stdErr)
    {
        this.stdErr = stdErr;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean isSuccess()
    {
        if (exitValue == EXEC_FAILURE || exitValue == EXEC_TIMEOUT)
        {
            return false;
        }

        if (throwable != null)
        {
            return false;
        }

        if (expectedExitValues == null || expectedExitValues.length == 0)
        {
            return true;
        }

        for (int i = 0; i < expectedExitValues.length; i++)
        {
            if (expectedExitValues[i] == exitValue)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isFailure(final int exitValue)
    {
        return !isSuccess();
    }
}
