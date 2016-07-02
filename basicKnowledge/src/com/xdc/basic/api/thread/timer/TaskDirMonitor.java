package com.xdc.basic.api.thread.timer;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDirMonitor extends FileAlterationListenerAdaptor
{
    private static Logger log = LoggerFactory.getLogger(TaskDirMonitor.class);

    // private static int checkConter = 0;

    @Override
    public void onStart(FileAlterationObserver observer)
    {
        // System.out.println(MessageFormat.format("目录改变第{0}次检查中...", ++checkConter));
    }

    @Override
    public void onStop(FileAlterationObserver observer)
    {
        // System.out.println(MessageFormat.format("目录改变第{0}次检查完成.", checkConter));
    }

    @Override
    public void onDirectoryCreate(File directory)
    {
        // System.out.println("fold: " + directory.getAbsolutePath() + " is created.");
    }

    @Override
    public void onDirectoryChange(File directory)
    {
        // System.out.println("fold: " + directory.getAbsolutePath() + " is changed.");
    }

    @Override
    public void onDirectoryDelete(File directory)
    {
        // System.out.println("fold: " + directory.getAbsolutePath() + " is deleted.");
    }

    @Override
    public void onFileCreate(File file)
    {
        log.info("File: [{}] is created.", file.getAbsolutePath());
        try
        {
            TaskManager.addTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Add task failed. taskFile=[%s].", file), e);
        }
    }

    @Override
    public void onFileChange(File file)
    {
        log.info("File: [{}] is changed.", file.getAbsolutePath());
        try
        {
            TaskManager.updateTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Update task failed. taskFile=[%s].", file), e);
        }
    }

    @Override
    public void onFileDelete(File file)
    {
        log.info("File: [{}] is deleted.", file.getAbsolutePath());
        try
        {
            TaskManager.removeTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Remove task failed. taskFile=[%s].", file), e);
        }
    }
}