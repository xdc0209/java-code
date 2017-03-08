package com.xdc.basic.api.apache.commons.exec.framwaork;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.log.slf4j.LogTest;
import com.xdc.basic.commons.codec.BytesUtil;

public class ExecTool
{
    private static Logger  log                      = LoggerFactory.getLogger(LogTest.class);

    // 获取命令行的编码(以便后续处理数据流时不会乱码)：
    // (1)windows下获取cmd.exe的编码，打开命令行后在属性里可以查看当前编码。
    // (2)linux下获取当前shell(如bash)的编码，执行locale命令可以查看当前编码。
    private static String  CONSOLE_ENCODING_NAME    = System.getProperty("sun.jnu.encoding");
    private static Charset CONSOLE_ENCODING_CHARSET = null;

    static
    {
        try
        {
            CONSOLE_ENCODING_CHARSET = Charset.forName(CONSOLE_ENCODING_NAME);
        }
        catch (Throwable e)
        {
            log.error(String.format("Java not support console encoding [%s].", CONSOLE_ENCODING_NAME), e);
        }
    }

    /**
     * 使用字符串作为参数，使用ExecCommand的默认配置。
     */
    public static ExecResult exec(String command)
    {
        ExecCommand execCommand = new ExecCommand(command);
        return exec(execCommand);
    }

    /**
     * 使用ExecCommand作为参数，可以自由定制ExecCommand。
     */
    public static ExecResult exec(ExecCommand execCommand)
    {
        if (CONSOLE_ENCODING_CHARSET == null)
        {
            // Java不支持命令行的编码，直接返回失败。
            return new ExecResult(execCommand.getExpectedExitValues(), ExecResult.EXEC_FAILURE, null, null,
                    new UnsupportedCharsetException(CONSOLE_ENCODING_NAME));
        }

        // 创建执行命令行。
        CommandLine commandLine = null;
        if (SystemUtils.IS_OS_UNIX)
        {
            commandLine = CommandLine.parse("sh");

            // 注意此处的handleQuoting要为false。因为最终传给Java Api的命令数数组在造Process时，Java自动会加上双引号。如果此处为true，会被加两遍双引号，导致执行出错。
            commandLine.addArguments(new String[] { "-c", execCommand.getCommand() }, false);
        }
        else if (SystemUtils.IS_OS_WINDOWS)
        {
            commandLine = CommandLine.parse("cmd");

            // 注意此处的handleQuoting要为false。因为最终传给Java Api的命令数数组在造Process时，Java自动会加上双引号。如果此处为true，会被加两遍双引号，导致执行出错。
            commandLine.addArguments(new String[] { "/c", execCommand.getCommand() }, false);
        }
        else
        {
            throw new RuntimeException("Unsupport OS type. Suppurt OS type:[UNIX-like, Windows].");
        }

        // 创建执执行器。
        DefaultExecutor executor = new DefaultExecutor();

        // 设置标准输出流，标准错误留，标准输入流。值得注意的是ByteArrayInputStream和ByteArrayOutputStream是不用关闭的，因为不是它们不是真正的IO流，未与设备交互，不过是Java内部缓冲用的。其实查看他的的close方法，是空实现的。
        ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
        ByteArrayOutputStream stdErr = new ByteArrayOutputStream();
        ByteArrayInputStream stdIn = null;
        if (StringUtils.isNotEmpty(execCommand.getStdIn()))
        {
            stdIn = new ByteArrayInputStream(BytesUtil.getBytes(execCommand.getStdIn(), CONSOLE_ENCODING_CHARSET));
        }
        executor.setStreamHandler(new PumpStreamHandler(stdOut, stdErr, stdIn));

        // 设置执行目录。
        executor.setWorkingDirectory(execCommand.getWorkingDirectory());

        // 设置期望返回值，不符合抛异常ExecuteException。这里置为空，禁用此功能，由业务自己处理返回值，无需业务捕获异常。
        executor.setExitValues(null);

        if (execCommand.isDestroyCmdProcessOnJvmExit())
        {
            // 设置关闭钩子，当jvm退出时，杀死正在执行的命令行进程。
            executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());
        }

        try
        {
            if (execCommand.getTimeout() != null)
            {
                // 设置看门狗，超时后，再等待10秒钟，杀死命令行进程，否则会残留，浪费资源。等10秒的原因是为了区分正常返回和执行超时。
                // 因为杀死进程后，进程也是有返回值的，看起来与执行完成没有区别。虽然执行成功（惯例是0）、失败、超时被杀死的返回码不同，也是不能根据返回值判断是否超时的，那样代码就跟操作系统的耦合了。
                ExecuteWatchdog watchdog = new ExecuteWatchdog(execCommand.getTimeout() + 10 * 1000L);
                executor.setWatchdog(watchdog);

                // 创建执行结果处理器
                DefaultExecuteResultHandler defaultExecuteResultHandler = new DefaultExecuteResultHandler();

                // 执行命令。
                executor.execute(commandLine, execCommand.getEnvironment(), defaultExecuteResultHandler);

                // 同步等待命令行进程完成。
                defaultExecuteResultHandler.waitFor(execCommand.getTimeout());

                if (defaultExecuteResultHandler.hasResult())
                {
                    // 执行完成，获取返回值。
                    return new ExecResult(execCommand.getExpectedExitValues(),
                            defaultExecuteResultHandler.getExitValue(),
                            BytesUtil.newString(stdOut.toByteArray(), CONSOLE_ENCODING_CHARSET),
                            BytesUtil.newString(stdErr.toByteArray(), CONSOLE_ENCODING_CHARSET),
                            defaultExecuteResultHandler.getException());
                }
                else
                {
                    // 执行超时，返回值设置为空。
                    return new ExecResult(execCommand.getExpectedExitValues(), ExecResult.EXEC_TIMEOUT,
                            BytesUtil.newString(stdOut.toByteArray(), CONSOLE_ENCODING_CHARSET),
                            BytesUtil.newString(stdErr.toByteArray(), CONSOLE_ENCODING_CHARSET));
                }
            }
            else
            {
                int exitValue = executor.execute(commandLine, execCommand.getEnvironment());

                // 执行完成，获取返回值。
                return new ExecResult(execCommand.getExpectedExitValues(), exitValue,
                        BytesUtil.newString(stdOut.toByteArray(), CONSOLE_ENCODING_CHARSET),
                        BytesUtil.newString(stdErr.toByteArray(), CONSOLE_ENCODING_CHARSET));
            }
        }
        catch (Throwable e)
        {
            // 执行失败，获取异常。
            return new ExecResult(execCommand.getExpectedExitValues(), ExecResult.EXEC_FAILURE,
                    BytesUtil.newString(stdOut.toByteArray(), CONSOLE_ENCODING_CHARSET),
                    BytesUtil.newString(stdErr.toByteArray(), CONSOLE_ENCODING_CHARSET), e);
        }
    }
}
