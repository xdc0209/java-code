package com.xdc.basic.tools.sendjars;

import java.util.List;

import org.apache.commons.exec.CommandLine;

import com.xdc.basic.commons.PathUtil;

public class Pscp
{
    private String         exePath = "pscp.exe";
    private Authentication authentication;
    private ExecutorTool   executorTool;

    public Pscp(Authentication authentication)
    {
        super();
        this.exePath = PathUtil.getRelativePath() + "pscp.exe";
        this.authentication = authentication;
        this.executorTool = new ExecutorTool();
    }

    public int sendFile(String source, String target)
    {
        String allcmd = String.format("%s -pw %s %s %s@%s:%s", exePath, authentication.getPassword(), source,
                authentication.getUser(), authentication.getIp(), target);

        CommandLine cmdLine = CommandLine.parse(allcmd);

        int excute = executorTool.execute(cmdLine);
        return excute;
    }

    public List<String> getLastOutLines()
    {
        return executorTool.getLastOutputLines();
    }

    public List<String> getLastErrLines()
    {
        return executorTool.getLastErrLines();
    }

}
