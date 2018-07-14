package com.xdc.basic.tools.sendjars;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.xdc.basic.commons.PathUtil;

public class SendJars
{
    public static void main(String[] args)
    {
        // TODO 时间有限，后期优化吧
        // 连通性校验没做，异常处理没做，架构优化没做

        String ip = "192.168.1.100";
        String user = "xdc0209";
        String password = "12345678";
        String searchPath = "/home/xdc0209";

        Authentication authentication = new Authentication(ip, user, password);

        Plink plink = new Plink(authentication);
        Pscp pscp = new Pscp(authentication);

        Collection<File> filesToSend = getFilesToSend();

        // 获取服务器时间，用于生成备份目录名
        plink.excute("date +%Y%m%d%H%M%S");
        String date = plink.getLastOutLines().get(0);
        String backupDir = searchPath + "/jarBackup/" + date;

        plink.excute("mkdir -p " + backupDir);

        for (File file : filesToSend)
        {
            plink.excute("find " + searchPath + " -name " + file.getName() + " -type f");
            List<String> findFiles = plink.getLastOutLines();

            for (String filePath : findFiles)
            {
                plink.excute("cp " + filePath + " " + backupDir);
                pscp.sendFile(file.getAbsolutePath(), filePath);
            }
        }
    }

    private static Collection<File> getFilesToSend()
    {
        String curPath = PathUtil.getRelativePath();
        Collection<File> filesToSend = FileUtils.listFiles(new File(curPath), new String[] { "jar" }, false);
        return filesToSend;
    }
}
