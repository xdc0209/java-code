package com.xdc.basic.api.timer.jdk;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.commons.PathUtil;

public class Main
{
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception
    {
        initTask();
        monitorTaskDir();
    }

    private static void initTask() throws Exception
    {
        String taskDir = PathUtil.connect(PathUtil.getAbsolutePath(), "tasks");

        // 列出任务目录的所有文件, 注意java的api设计的比较怪异，扩展名明前不能加点。
        Collection<File> files = FileUtils.listFiles(new File(taskDir), new String[] { "txt" }, true);

        for (File file : files)
        {
            try
            {
                TaskManager.addTask(file);
            }
            catch (Throwable e)
            {
                log.error(String.format("Add task failed. taskFile=[%s].", file), e);
            }
        }
    }

    private static void monitorTaskDir() throws Exception
    {
        TaskDirMonitor taskDirMonitor = new TaskDirMonitor();

        String taskDir = PathUtil.connect(PathUtil.getAbsolutePath(), "tasks");
        FileAlterationObserver taskDirObserver = new FileAlterationObserver(taskDir,
                FileFilterUtils.suffixFileFilter(".txt"));
        taskDirObserver.addListener(taskDirMonitor);

        // 配置Monitor，第一个参数单位是毫秒，是监听的间隔；第二个参数就是绑定我们之前的观察对象。
        FileAlterationMonitor fileMonitor = new FileAlterationMonitor(5000, taskDirObserver);

        // 启动开始监听
        fileMonitor.start();
    }
}
