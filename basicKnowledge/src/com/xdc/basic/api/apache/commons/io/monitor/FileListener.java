package com.xdc.basic.api.apache.commons.io.monitor;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileListener extends FileAlterationListenerAdaptor
{
    private static int checkConter = 0;

    @Override
    public void onStart(FileAlterationObserver observer)
    {
        System.out.println(MessageFormat.format("目录改变第{0}次检查中...", ++checkConter));
    }

    @Override
    public void onStop(FileAlterationObserver observer)
    {
        System.out.println(MessageFormat.format("目录改变第{0}次检查完成.", checkConter));
    }

    @Override
    public void onDirectoryCreate(File directory)
    {
        System.out.println("fold: " + directory.getAbsolutePath() + " is created.");
    }

    @Override
    public void onDirectoryChange(File directory)
    {
        System.out.println("fold: " + directory.getAbsolutePath() + " is changed.");
    }

    @Override
    public void onDirectoryDelete(File directory)
    {
        System.out.println("fold: " + directory.getAbsolutePath() + " is deleted.");
    }

    @Override
    public void onFileCreate(File file)
    {
        System.out.println("file: " + file.getAbsolutePath() + " is created.");
    }

    @Override
    public void onFileChange(File file)
    {
        System.out.println("file: " + file.getAbsolutePath() + " is changed.");
    }

    @Override
    public void onFileDelete(File file)
    {
        System.out.println("file: " + file.getAbsolutePath() + " is deleted");
    }
}
