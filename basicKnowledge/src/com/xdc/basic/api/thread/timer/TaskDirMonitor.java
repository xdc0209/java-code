package com.xdc.basic.api.thread.timer;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDirMonitor extends FileAlterationListenerAdaptor
{
    private static Logger log         = LoggerFactory.getLogger(TaskDirMonitor.class);

    private static int    checkConter = 0;

    @Override
    public void onStart(FileAlterationObserver observer)
    {
        log.debug("目录改变第[{}]次检查中...", checkConter);
    }

    @Override
    public void onStop(FileAlterationObserver observer)
    {
        log.debug("目录改变第[{}]次检查完成", checkConter);
    }

    @Override
    public void onDirectoryCreate(File directory)
    {
        log.debug("Fold [{}] is created.", directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory)
    {
        log.debug("Fold [{}] is changed.", directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory)
    {
        log.debug("Fold [{}] is deleted.", directory.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file)
    {
        log.info("File [{}] is created.", file.getAbsolutePath());
        try
        {
            TaskManager.addTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Add task failed. file=[%s].", file), e);
        }
    }

    @Override
    public void onFileChange(File file)
    {
        log.info("File [{}] is changed.", file.getAbsolutePath());
        try
        {
            TaskManager.updateTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Update task failed. file=[%s].", file), e);
        }
    }

    @Override
    public void onFileDelete(File file)
    {
        log.info("File [{}] is deleted.", file.getAbsolutePath());
        try
        {
            TaskManager.removeTask(file);
        }
        catch (Throwable e)
        {
            log.error(String.format("Remove task failed. file=[%s].", file), e);
        }
    }
}
