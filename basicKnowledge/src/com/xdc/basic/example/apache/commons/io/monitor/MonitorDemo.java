package com.xdc.basic.example.apache.commons.io.monitor;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class MonitorDemo
{
    public static void main(String[] args) throws Exception
    {
        // 构造过滤器，只监视可见目录和*.java文件
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".java"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        //构造观察类主要提供要观察的文件或目录
        String directory = "src";
        FileAlterationObserver observer = new FileAlterationObserver(directory, filter);

        //构造收听类 没啥好说的  
        FileListener listener = new FileListener();

        //为观察对象添加收听对象  
        observer.addListener(listener);

        //配置Monitor，第一个参数单位是毫秒，是监听的间隔；第二个参数就是绑定我们之前的观察对象。  
        FileAlterationMonitor fileMonitor = new FileAlterationMonitor(5000, observer);

        //启动开始监听  
        fileMonitor.start();
    }
}