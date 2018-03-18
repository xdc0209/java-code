package com.xdc.basic.commons.exec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.exec.Executor;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xdc.basic.commons.BytesUtil;

public class ExecResult
{
    /**
     * 命令行调用框架异常，非命令行进程返回值。出现此返回值，代表ExecCommand中的值设置的不合理。
     */
    public static final int EXEC_FAILURE       = -1;

    /**
     * 命令行调用超时，非命令行进程返回值。出现此返回值，需要排查脚本和可执行命令中是否有死循环等错误。
     */
    public static final int EXEC_TIMEOUT       = -2;

    /**
     * 期待的返回值列表，只有返回值包含在此列表中，才认为此次调用成功。根据惯例，值0表示正常终止。此处提供定制能力，以适应一些设计古怪的可执行命令。
     */
    private int[]           expectedExitValues = new int[] { 0 };

    /**
     * 命令行调用的返回值。
     */
    private int             exitValue;

    /**
     * 命令行调用的标准输出流。
     */
    private String          stdOut;

    /**
     * 命令行调用的标准错误流。
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

        // 统一框架异常错误值，将Apache的框架异常错误值改为框架异常错误值。
        if (exitValue == Executor.INVALID_EXITVALUE && throwable == null)
        {
            this.exitValue = EXEC_FAILURE;
        }
        else
        {
            this.exitValue = exitValue;
        }

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

    public List<String> getStdOutLines()
    {
        byte[] stdOutBytes = BytesUtil.getBytes(stdOut, Charsets.UTF_8);
        ByteArrayInputStream stdOutBAIS = new ByteArrayInputStream(stdOutBytes);

        try
        {
            return IOUtils.readLines(stdOutBAIS);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            IOUtils.closeQuietly(stdOutBAIS);
        }
    }

    public void setStdOut(String stdOut)
    {
        this.stdOut = stdOut;
    }

    public String getStdErr()
    {
        return stdErr;
    }

    public List<String> getStdErrLines()
    {
        byte[] stdErrBytes = BytesUtil.getBytes(stdErr, Charsets.UTF_8);
        ByteArrayInputStream stdErrBAIS = new ByteArrayInputStream(stdErrBytes);

        try
        {
            return IOUtils.readLines(stdErrBAIS);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            IOUtils.closeQuietly(stdErrBAIS);
        }
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

    public boolean isFailure()
    {
        return !isSuccess();
    }
}
