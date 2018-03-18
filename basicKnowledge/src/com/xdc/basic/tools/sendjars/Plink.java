package com.xdc.basic.tools.sendjars;

import java.util.List;

import org.apache.commons.exec.CommandLine;

import com.xdc.basic.commons.PathUtil;

public class Plink
{
    private String         exePath = "plink.exe";
    private Authentication authentication;
    private ExecutorTool   executorTool;

    public Plink(Authentication authentication)
    {
        super();
        this.exePath = PathUtil.getRelativePath() + "plink.exe";
        this.authentication = authentication;
        this.executorTool = new ExecutorTool();
    }

    public int excute(String cmd)
    {
        String allcmd = String.format("%s -pw %s %s@%s \"%s\"", exePath, authentication.getPassword(),
                authentication.getUser(), authentication.getIp(), cmd);

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
